package PaooGame.map;

import PaooGame.exceptions.MapLoadingException;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;

public class Harta {
    private int[][] matriceDale;
    private int latimeInDale;
    private int inaltimeInDale;

    public Harta(String caleFisier, int latimeInDale, int inaltimeInDale, int offsetID) throws MapLoadingException {
        this.latimeInDale = latimeInDale;
        this.inaltimeInDale = inaltimeInDale;
        matriceDale = new int[inaltimeInDale][latimeInDale];

        incarcaHarta(caleFisier, offsetID);
    }

    private void incarcaHarta(String caleFisier, int offsetID) throws MapLoadingException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(caleFisier));
            String linie;
            int rand = 0;

            while ((linie = br.readLine()) != null && rand < inaltimeInDale) {
                String[] numere = linie.split(",");
                for (int col = 0; col < latimeInDale && col < numere.length; col++) {

                    int idCitit = Integer.parseInt(numere[col].trim());

                    if (idCitit != -1) {
                        matriceDale[rand][col] = idCitit + offsetID;
                    } else {
                        matriceDale[rand][col] = -1;
                    }
                }
                rand++;
            }
            br.close();
        } catch (Exception e) {
            throw new MapLoadingException(caleFisier, e);
        }
    }

    public int getDalaID(int col, int rand) {
        if (col >= 0 && col < latimeInDale && rand >= 0 && rand < inaltimeInDale) {
            return matriceDale[rand][col];
        }
        return -1;
    }

    public void Draw(Graphics g, int cameraX, int cameraY, int screenWidth, int screenHeight) {
        int marimeTileZoom = 64;

        for (int y = 0; y < inaltimeInDale; y++) {
            for (int x = 0; x < latimeInDale; x++) {

                int idDala = matriceDale[y][x];
                Tile t = getTileDinId(idDala);

                if (t != null) {
                    int worldX = x * marimeTileZoom;
                    int worldY = y * marimeTileZoom;

                    int screenX = worldX - cameraX;
                    int screenY = worldY - cameraY;

                    if (screenX + marimeTileZoom > 0 && screenX < screenWidth &&
                            screenY + marimeTileZoom > 0 && screenY < screenHeight) {
                        t.Draw(g, screenX, screenY);
                    }
                }
            }
        }
    }

    private Tile getTileDinId(int id) {
        if (id == -1) return null;
        if (id < 0 || id >= Tile.tiles.length) return null;
        return Tile.tiles[id];
    }
}