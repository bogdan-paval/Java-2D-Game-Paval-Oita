package PaooGame.main;

import PaooGame.Graphics.Camera;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Graphics.HUD;
import PaooGame.Graphics.VictoryOverlay;
import PaooGame.Graphics.LevelCompletedOverlay;
import PaooGame.entity.*;
import PaooGame.input.KeyHandler;
import PaooGame.input.MouseHandler;
import PaooGame.map.Harta;
import PaooGame.states.MeniuJoc;
import PaooGame.map.Tile;
import PaooGame.exceptions.*;
import PaooGame.data.DatabaseManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements Runnable, GameController {
    public static final int TILE_SIZE = 64;
    public static final int MAX_COLS = 60;
    public static final int MAX_ROWS = 34;
    public static final int MAP_MAX_X = MAX_COLS * TILE_SIZE;
    public static final int MAP_MAX_Y = MAX_ROWS * TILE_SIZE;

    private boolean gameOverAfisat = false;
    private Harta hartaFundal;
    private Harta hartaObiecte;
    private GameWindow wnd;
    private boolean runState;
    private Thread gameThread;
    private BufferStrategy bs;
    private Graphics g;
    private long timpStartCutScene;
    private java.awt.Image introGif;
    private Camera camera;
    private Player bogo;
    private ArrayList<Entity> listaEntitati = new ArrayList<>();
    private int nivelCurent = 1;
    private KeyHandler keyH = new KeyHandler();
    private MouseHandler mouseH = new MouseHandler();
    private HUD hud;
    private VictoryOverlay victoryOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;

    private int nrTotalOiteCalculat = 0;
    private int nrTotalLupiCalculat = 0;
    private int totalOiteSalvate = 0;
    private int totalLupiUcisi = 0;
    private int scorTotalJoc = 0;
    private int oiteSalvateNivelCurent = 0;

    public enum GameState {MENU, CUTSCENE, PLAYING, LEVEL_COMPLETED, GAME_OVER_FADE, GAME_OVER, VICTORY}
    private GameState stareCurenta = GameState.MENU;
    private boolean meniuAfisat = false;
    public CollisionChecker cChecker = new CollisionChecker();

    private int timerMoarte = 0;
    private final int TIMP_INNEGRIRE = 90;

    public Game(String title, int width, int height) {
        introGif = new javax.swing.ImageIcon(getClass().getResource("/VINElupu.gif")).getImage();
        runState = false;
        hud = new HUD();
        victoryOverlay = new VictoryOverlay();
        levelCompletedOverlay = new LevelCompletedOverlay();
    }

    public void adaugaOaieSalvata() { this.totalOiteSalvate++; }
    public void adaugaLupUcis() { this.totalLupiUcisi++; }

    public int getNrTotalOiteCalculat() { return nrTotalOiteCalculat; }
    public int getNrTotalLupiCalculat() { return nrTotalLupiCalculat; }
    public Player getBogo() { return bogo; }
    public ArrayList<Entity> getListaEntitati() { return listaEntitati; }
    public Harta getHartaObiecte() { return hartaObiecte; }

    public void setStareCurenta(GameState stare) { this.stareCurenta = stare; }
    public void setTimpStartCutScene(long timp) { this.timpStartCutScene = timp; }

    public void restartJoc() {
        this.gameOverAfisat = false;

        if (bogo != null) {
            bogo.invie();
            bogo.setWorldX(150);
            bogo.setWorldY(1000);
        }

        if (nivelCurent == 1) incarcaNivelul1();
        else if (nivelCurent == 2) incarcaNivelul2();
        else if (nivelCurent == 3) incarcaNivelul3();

        setStareCurenta(GameState.PLAYING);
    }

    public void afiseazaGameOver() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            PaooGame.states.FereastraGameOver gameOver = new PaooGame.states.FereastraGameOver(this);
            gameOver.setVisible(true);
        });
    }

    public void intoarcereLaMeniu() {
        this.gameOverAfisat = false;
        this.meniuAfisat = false;
        setStareCurenta(GameState.MENU);
    }

    public void arataMeniu() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MeniuJoc meniu = new MeniuJoc(this);
            meniu.setVisible(true);
        });
    }

    private void InitGame() {
        Dimension dimensiuneEcran = Toolkit.getDefaultToolkit().getScreenSize();
        int latimeEcran = (int) dimensiuneEcran.getWidth();
        int inaltimeEcran = (int) dimensiuneEcran.getHeight();

        wnd = new GameWindow("Cine mi-a furat oița?", latimeEcran, inaltimeEcran);
        wnd.BuildGameWindow();

        wnd.GetCanvas().addKeyListener(keyH);
        wnd.GetCanvas().addMouseListener(mouseH);
        wnd.GetCanvas().setFocusable(true);

        camera = new Camera(0, 0);

        try {
            Assets.Init();
        } catch (ResourceLoadException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void incarcaNivelul1() {
        try {
            hartaFundal = new Harta("res/baza.csv", 60, 34, 0);
            hartaObiecte = new Harta("res/obiect.csv", 60, 34, 0);

            cChecker.setHarta(hartaObiecte);

            bogo = Player.getInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), keyH, mouseH);
            bogo.setWorldX(150);
            bogo.setWorldY(1000);
            incarcaConfiguratieEntitati("res/config/nivel1.txt");
        } catch (MapLoadingException | LevelConfigurationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void incarcaNivelul2() {
        try {
            hartaFundal = new Harta("res/nivel2_baza.csv", 60, 34, 2000);
            hartaObiecte = new Harta("res/nivel2_obiecte.csv", 60, 34, 2000);

            cChecker.setHarta(hartaObiecte);

            bogo = Player.getInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), keyH, mouseH);
            bogo.setWorldX(150);
            bogo.setWorldY(1000);
            incarcaConfiguratieEntitati("res/config/nivel2.txt");
        } catch (MapLoadingException | LevelConfigurationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void incarcaNivelul3() {
        try {
            hartaFundal = new Harta("res/nivel3_baza.csv", 60, 34, 3000);
            hartaObiecte = new Harta("res/nivel3_obiecte.csv", 60, 34, 3000);

            cChecker.setHarta(hartaObiecte);

            bogo = Player.getInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), keyH, mouseH);
            bogo.setWorldX(150);
            bogo.setWorldY(1000);
            incarcaConfiguratieEntitati("res/config/nivel3.txt");
        } catch (MapLoadingException | LevelConfigurationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void incarcaStareSalvata(int nivel, int x, int y, int hp, int oite, int lupi) {
        this.nivelCurent = nivel;
        if (nivel == 1) incarcaNivelul1();
        else if (nivel == 2) incarcaNivelul2();
        else if (nivel == 3) incarcaNivelul3();

        if (bogo != null) {
            bogo.setWorldX(x);
            bogo.setWorldY(y);
            bogo.setHp(hp);
        }
        this.totalOiteSalvate = oite;
        this.totalLupiUcisi = lupi;

        try {
            ArrayList<Object[]> entitatiSalvate = DatabaseManager.getInstance().incarcaEntitatiSalvate();
            if (!entitatiSalvate.isEmpty()) {
                ArrayList<Entity> entitatiInViata = new ArrayList<>();

                for (Object[] dateEnt : entitatiSalvate) {
                    String tip = (String) dateEnt[0];
                    int entX = (int) dateEnt[1];
                    int entY = (int) dateEnt[2];
                    int entHp = (int) dateEnt[3];

                    Entity potrivire = null;
                    for (Entity e : listaEntitati) {
                        String tipE = (e instanceof Sheep) ? "sheep_" + ((Sheep) e).getTipOaie() : "lup";
                        if (tipE.equals(tip) && !entitatiInViata.contains(e)) {
                            potrivire = e;
                            break;
                        }
                    }

                    if (potrivire != null) {
                        potrivire.setWorldX(entX);
                        potrivire.setWorldY(entY);
                        potrivire.setHp(entHp);
                        entitatiInViata.add(potrivire);
                    } else {
                        try {
                            Entity nouaEntitate = EntityFactory.createEntity(tip, entX, entY, nivelCurent);
                            if (nouaEntitate != null) {
                                nouaEntitate.setHp(entHp);
                                entitatiInViata.add(nouaEntitate);
                            }
                        } catch (Exception ex) {
                            System.err.println("Eroare la generarea entitatii din DB: " + ex.getMessage());
                        }
                    }
                }

                listaEntitati.clear();
                listaEntitati.addAll(entitatiInViata);
            }
        } catch (DatabaseSyncException e) {
            System.err.println("Eroare la restaurarea entitatilor: " + e.getMessage());
        }
    }

    public void salveazaJoculCurent() {
        if (bogo != null) {
            try {
                DatabaseManager.getInstance().salveazaJoc(
                        this.nivelCurent,
                        bogo.getWorldX(),
                        bogo.getWorldY(),
                        bogo.getHp(),
                        this.scorTotalJoc,
                        this.totalOiteSalvate,
                        this.totalLupiUcisi,
                        this.listaEntitati
                );
            } catch (DatabaseSyncException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void nivelCompletat(int oiteSalvate) {
        this.oiteSalvateNivelCurent = oiteSalvate;
        this.scorTotalJoc += (oiteSalvate * 100);
        setStareCurenta(GameState.LEVEL_COMPLETED);
        salveazaJoculCurent();
    }

    private void executaTrecereaLaNivelulUrmator() {
        if (nivelCurent < 3) {
            nivelCurent++;
            salveazaJoculCurent();
            if (nivelCurent == 2) incarcaNivelul2();
            else if (nivelCurent == 3) incarcaNivelul3();
            camera.setX(0);
            camera.setY(0);
            setStareCurenta(GameState.PLAYING);
        } else {
            setStareCurenta(GameState.VICTORY);
        }
    }

    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond = 60;
        final double timeFrame = 1000000000 / framesPerSecond;

        while (runState) {
            curentTime = System.nanoTime();
            if ((curentTime - oldTime) > timeFrame) {
                Update();
                Draw();
                oldTime = curentTime;
            }
        }
    }

    public synchronized void StartGame() {
        if (!runState) {
            runState = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void StopGame() {
        if (runState) {
            runState = false;
            try {
                gameThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void incarcaConfiguratieEntitati(String caleFisier) throws LevelConfigurationException {
        listaEntitati.clear();
        java.util.ArrayList<int[]> listaPortiTemp = new java.util.ArrayList<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(caleFisier))) {
            String linie;
            boolean citimPorti = false;

            while ((linie = br.readLine()) != null) {
                linie = linie.trim();
                if (linie.isEmpty()) continue;

                if (linie.equals("ENTITATI:")) { citimPorti = false; continue; }
                if (linie.equals("PORTI:")) { citimPorti = true; continue; }

                if (!citimPorti) {
                    String[] date = linie.split(",");
                    Entity entitate = EntityFactory.createEntity(date[0].trim(), Integer.parseInt(date[1].trim()) * TILE_SIZE, Integer.parseInt(date[2].trim()) * TILE_SIZE, nivelCurent);
                    if (entitate instanceof Sheep) {
                        ((Sheep) entitate).seteazaDestinatieTarc(Integer.parseInt(date[3].trim()), Integer.parseInt(date[4].trim()));
                    }
                    listaEntitati.add(entitate);
                } else {
                    String[] date = linie.split(",");
                    listaPortiTemp.add(new int[]{Integer.parseInt(date[0].trim()), Integer.parseInt(date[1].trim()), Integer.parseInt(date[2].trim()), Integer.parseInt(date[3].trim())});
                }
            }

            for (Entity e : listaEntitati) {
                for (int[] p : listaPortiTemp) {
                    if (e instanceof InteractiaCuZone) {
                        ((InteractiaCuZone) e).primesteZonaSpeciala(p[0], p[1], p[2], p[3]);
                    }
                }
            }

            nrTotalOiteCalculat = 0;
            nrTotalLupiCalculat = 0;
            for (Entity e : listaEntitati) {
                if (e instanceof Sheep) nrTotalOiteCalculat++;
                if (e instanceof Wolf) nrTotalLupiCalculat++;
            }

            LevelManager levelManager = new LevelManager(this, 3, nrTotalOiteCalculat);
            if (bogo != null) bogo.addObserver(levelManager);
            for (Entity e : listaEntitati) e.addObserver(levelManager);

        } catch (Exception e) {
            throw new LevelConfigurationException(caleFisier, "Eroare parsare entitati", e);
        }
    }

    private void Update() {
        if (stareCurenta == GameState.MENU) {
            if (!meniuAfisat) {
                arataMeniu();
                meniuAfisat = true;
            }
        }
        else if (stareCurenta == GameState.CUTSCENE) {
            if (System.currentTimeMillis() - timpStartCutScene >= 8000) {
                nivelCurent = 1;
                incarcaNivelul1();
                stareCurenta = GameState.PLAYING;
            }
        }
        else if (stareCurenta == GameState.PLAYING) {
            bogo.update(cChecker, listaEntitati);
            for (Entity npc : listaEntitati) {
                npc.update(cChecker, listaEntitati);
            }
            listaEntitati.removeIf(Entity::isDead);
            camera.update(bogo, wnd.GetWndWidth(), wnd.GetWndHeight(), MAP_MAX_X, MAP_MAX_Y);
        }
        else if (stareCurenta == GameState.GAME_OVER_FADE) {
            timerMoarte++;
            if (timerMoarte >= TIMP_INNEGRIRE) {
                stareCurenta = GameState.GAME_OVER;
                salveazaJoculCurent();
                afiseazaGameOver();
                gameOverAfisat = true;
            }
        }
        else if (stareCurenta == GameState.LEVEL_COMPLETED) {
            if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {
                executaTrecereaLaNivelulUrmator();
                if (stareCurenta != GameState.VICTORY) setStareCurenta(GameState.PLAYING);
            }
        }
        else if (stareCurenta == GameState.VICTORY) {
            if (keyH.isEscPressed()) {
                intoarcereLaMeniu();
            }
        }
    }

    private void Draw() {
        bs = wnd.GetCanvas().getBufferStrategy();
        if (bs == null) {
            try { wnd.GetCanvas().createBufferStrategy(3); return; } catch (Exception e) { return; }
        }

        g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        if (stareCurenta == GameState.CUTSCENE) {
            if (introGif != null) g.drawImage(introGif, 0, 0, wnd.GetWndWidth(), wnd.GetWndHeight(), null);
        } else if (stareCurenta == GameState.PLAYING || stareCurenta == GameState.GAME_OVER_FADE) {
            hartaFundal.Draw(g, camera.getX(), camera.getY(), wnd.GetWndWidth(), wnd.GetWndHeight());
            hartaObiecte.Draw(g, camera.getX(), camera.getY(), wnd.GetWndWidth(), wnd.GetWndHeight());

            bogo.draw(g, camera.getX(), camera.getY());
            for (Entity npc : listaEntitati)
                npc.draw(g, camera.getX(), camera.getY());

            int oiteInTarc = 0;
            int lupiRamasi = 0;
            for (Entity e : listaEntitati) {
                if (e instanceof Sheep && ((Sheep) e).isEsteSalvata()) oiteInTarc++;
                if (e instanceof Wolf) lupiRamasi++;
            }
            int lupiUcisi = nrTotalLupiCalculat - lupiRamasi;

            hud.draw((Graphics2D) g, bogo.getHp(), bogo.getMaxHp(), oiteInTarc, nrTotalOiteCalculat, lupiUcisi, nrTotalLupiCalculat);

            if (nivelCurent == 3) {
                Graphics2D g2d = (Graphics2D) g;
                RadialGradientPaint ceata = new RadialGradientPaint(
                        bogo.getWorldX() - camera.getX() + 72f, bogo.getWorldY() - camera.getY() + 72f,
                        500f, new float[]{0.0f, 1.0f}, new Color[]{new Color(200, 200, 200, 0), new Color(200, 220, 255, 255)}
                );
                g2d.setPaint(ceata);
                g2d.fillRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
            }

            if (stareCurenta == GameState.GAME_OVER_FADE) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, Math.min((float) timerMoarte / TIMP_INNEGRIRE, 1.0f)));
                g2d.fillRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
            }
        }
        else if (stareCurenta == GameState.VICTORY) {
            victoryOverlay.draw(g, wnd.GetWndWidth(), wnd.GetWndHeight());
        }
        else if (stareCurenta == GameState.LEVEL_COMPLETED) {
            levelCompletedOverlay.draw(g, wnd.GetWndWidth(), wnd.GetWndHeight(), oiteSalvateNivelCurent);
        }

        bs.show();
        g.dispose();
    }
}