package PaooGame.data;

import PaooGame.entity.Entity;
import PaooGame.entity.Sheep;
import PaooGame.entity.Wolf;
import PaooGame.exceptions.DatabaseSyncException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:salvari_joc.db";

    private DatabaseManager() throws DatabaseSyncException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            initDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseSyncException("Initializare esuata", e);
        }
    }

    public static DatabaseManager getInstance() throws DatabaseSyncException {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    private void initDatabase() throws DatabaseSyncException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Salvari (id INTEGER PRIMARY KEY, nivel INTEGER, bogo_x INTEGER, bogo_y INTEGER, bogo_hp INTEGER, scor_curent INTEGER, oite_salvate INTEGER, lupi_ucisi INTEGER);");
            stmt.execute("CREATE TABLE IF NOT EXISTS StareEntitati (id INTEGER PRIMARY KEY AUTOINCREMENT, tip TEXT, x INTEGER, y INTEGER, hp INTEGER, salvata INTEGER);");
        } catch (SQLException e) {
            throw new DatabaseSyncException("Creare tabele esuata", e);
        }
    }

    public int[] incarcaSalvareJucator() throws DatabaseSyncException {
        String sql = "SELECT * FROM Salvari WHERE id = 1;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return new int[]{
                        rs.getInt("nivel"), rs.getInt("bogo_x"), rs.getInt("bogo_y"),
                        rs.getInt("bogo_hp"), rs.getInt("scor_curent"),
                        rs.getInt("oite_salvate"), rs.getInt("lupi_ucisi")
                };
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseSyncException("Citire salvare esuata", e);
        }
    }

    public ArrayList<Object[]> incarcaEntitatiSalvate() throws DatabaseSyncException {
        ArrayList<Object[]> entitati = new ArrayList<>();
        String sql = "SELECT * FROM StareEntitati;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entitati.add(new Object[]{
                        rs.getString("tip"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getInt("hp"),
                        rs.getInt("salvata")
                });
            }
            return entitati;
        } catch (SQLException e) {
            throw new DatabaseSyncException("Citire salvare entitati esuata", e);
        }
    }

    public void salveazaJoc(int nivel, int bogoX, int bogoY, int bogoHp, int scorCurent, int oiteSalvate, int lupiUcisi, ArrayList<Entity> entitati) throws DatabaseSyncException {
        try {
            connection.setAutoCommit(false);

            String sqlBogo = "INSERT OR REPLACE INTO Salvari(id, nivel, bogo_x, bogo_y, bogo_hp, scor_curent, oite_salvate, lupi_ucisi) VALUES(1, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlBogo)) {
                pstmt.setInt(1, nivel); pstmt.setInt(2, bogoX); pstmt.setInt(3, bogoY);
                pstmt.setInt(4, bogoHp); pstmt.setInt(5, scorCurent);
                pstmt.setInt(6, oiteSalvate); pstmt.setInt(7, lupiUcisi);
                pstmt.executeUpdate();
            }

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM StareEntitati;");
            }

            String sqlEnt = "INSERT INTO StareEntitati(tip, x, y, hp, salvata) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtEnt = connection.prepareStatement(sqlEnt)) {
                for (Entity e : entitati) {
                    if (e.isDead()) continue;
                    pstmtEnt.setString(1, (e instanceof Sheep) ? "sheep_" + ((Sheep) e).getTipOaie() : "lup");
                    pstmtEnt.setInt(2, e.getWorldX()); pstmtEnt.setInt(3, e.getWorldY());

                    // ÎNCAPSULARE: Folosim metoda getter publică stabilită
                    pstmtEnt.setInt(4, e.getHp());

                    pstmtEnt.setInt(5, (e instanceof Sheep && ((Sheep) e).isEsteSalvata()) ? 1 : 0);
                    pstmtEnt.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            try { connection.rollback(); } catch (SQLException ex) {}
            throw new DatabaseSyncException("Eroare in tranzactia de salvare", e);
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) {}
        }
    }
}