package PaooGame.states;

import PaooGame.main.Game;
import PaooGame.main.GameController;
import PaooGame.data.DatabaseManager;
import PaooGame.exceptions.DatabaseSyncException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MeniuJoc extends JFrame {

    private GameController referintaJoc;

    public MeniuJoc(GameController joc) {
        this.referintaJoc = joc;

        setTitle("Cine mi-a furat oița?");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        PanouFundal panouPrincipal = new PanouFundal("/meniu.png");
        panouPrincipal.setPreferredSize(new Dimension(1385, 1030));
        panouPrincipal.setLayout(null);

        JButton butonNewGame = creeazaButonInvizibil();
        butonNewGame.setBounds(510, 280, 370, 60);
        adaugaEfectHover(butonNewGame);

        butonNewGame.addActionListener(e -> {
            referintaJoc.setStareCurenta(Game.GameState.CUTSCENE);
            referintaJoc.setTimpStartCutScene(System.currentTimeMillis());
            MeniuJoc.this.dispose();
        });

        JButton butonResumeGame = creeazaButonInvizibil();
        butonResumeGame.setBounds(510, 340, 370, 60);
        butonResumeGame.addActionListener(e -> {
            try {
                int[] date = DatabaseManager.getInstance().incarcaSalvareJucator();

                if (date != null) {
                    referintaJoc.incarcaStareSalvata(date[0], date[1], date[2], date[3], date[5], date[6]);
                    referintaJoc.setStareCurenta(Game.GameState.PLAYING);
                    MeniuJoc.this.dispose();
                } else {
                    referintaJoc.setStareCurenta(Game.GameState.CUTSCENE);
                    referintaJoc.setTimpStartCutScene(System.currentTimeMillis());
                    MeniuJoc.this.dispose();
                }
            } catch (DatabaseSyncException ex) {
                JOptionPane.showMessageDialog(this, "Eroare baza de date: " + ex.getMessage());
            }
        });
        adaugaEfectHover(butonResumeGame);

        JButton butonGameplay = creeazaButonInvizibil();
        butonGameplay.setBounds(510, 400, 370, 60);
        butonGameplay.addActionListener(e -> {
            FereastraGameplay fereastraMecanici = new FereastraGameplay(referintaJoc);
            fereastraMecanici.setVisible(true);
            this.dispose();
        });
        adaugaEfectHover(butonGameplay);

        JButton butonQuit = creeazaButonInvizibil();
        butonQuit.setBounds(510, 460, 370, 60);
        butonQuit.addActionListener(e -> {
            referintaJoc.salveazaJoculCurent();
            System.exit(0);
        });
        adaugaEfectHover(butonQuit);

        panouPrincipal.add(butonNewGame);
        panouPrincipal.add(butonResumeGame);
        panouPrincipal.add(butonGameplay);
        panouPrincipal.add(butonQuit);

        add(panouPrincipal);
    }

    private JButton creeazaButonInvizibil() {
        JButton btn = new JButton();
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void adaugaEfectHover(JButton buton) {
        buton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buton.setBorderPainted(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                buton.setBorderPainted(false);
            }
        });
    }
}