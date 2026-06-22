package PaooGame.states;

import PaooGame.main.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FereastraGameplay extends JFrame {

    private GameController referintaJoc;

    public FereastraGameplay(GameController joc) {
        this.referintaJoc = joc;

        setTitle("Mecanici Joc");

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        PanouFundal panouPrincipal = new PanouFundal("/gameplay.png");
        panouPrincipal.setPreferredSize(new Dimension(1385, 1030));
        panouPrincipal.setLayout(null);

        JButton butonInapoi = creeazaButonInvizibil();

        butonInapoi.setBounds(575, 915, 235, 85);
        adaugaEfectHover(butonInapoi);

        butonInapoi.addActionListener(e -> {
            System.out.println("Ne întoarcem la meniul principal...");
            MeniuJoc meniuPrincipal = new MeniuJoc(referintaJoc);
            meniuPrincipal.setVisible(true);
            this.dispose();
        });

        panouPrincipal.add(butonInapoi);
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