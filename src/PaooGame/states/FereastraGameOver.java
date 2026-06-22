package PaooGame.states;

import PaooGame.main.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FereastraGameOver extends JFrame {
    private GameController referintaJoc;

    public FereastraGameOver(GameController joc) {
        this.referintaJoc = joc;

        setTitle("Game Over");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        PanouFundal panouPrincipal = new PanouFundal("/game_over.png");
        panouPrincipal.setPreferredSize(new Dimension(1385, 1030));
        panouPrincipal.setLayout(null);

        JButton butonRestart = creeazaButonInvizibil();
        butonRestart.setBounds(513, 740, 365, 105);
        adaugaEfectHover(butonRestart);

        butonRestart.addActionListener(e -> {
            System.out.println("Restartam jocul!");
            referintaJoc.restartJoc();
            this.dispose();
        });

        JButton butonMeniu = creeazaButonInvizibil();
        butonMeniu.setBounds(513, 885, 365, 105);
        adaugaEfectHover(butonMeniu);

        butonMeniu.addActionListener(e -> {
            System.out.println("Inapoi la meniul principal...");
            referintaJoc.intoarcereLaMeniu();
            this.dispose();
        });

        panouPrincipal.add(butonRestart);
        panouPrincipal.add(butonMeniu);

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
            public void mouseEntered(MouseEvent e) { buton.setBorderPainted(true); }
            @Override
            public void mouseExited(MouseEvent e) { buton.setBorderPainted(false); }
        });
    }
}