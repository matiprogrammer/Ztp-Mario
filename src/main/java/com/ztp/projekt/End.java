package com.ztp.projekt;

import javax.swing.*;
import java.awt.*;


//Klasa wyswietlajaca dialog koncowy (przegrana lub wygrana gra)
class End extends JDialog {

    public static int level = 1;

    public End(String message, JFrame owner, int pom, char block) {
        super(owner, message, true);
        add(new JLabel(message), BorderLayout.CENTER);
        String time = String.valueOf((System.currentTimeMillis() - App.timeStart) / 1000);
        add(new JLabel("Zdobyłeś " + pom + " punktów w " + time + "s."), BorderLayout.CENTER);
        JButton ok = new JButton("Ok");
        ok.addActionListener(event -> System.exit(0));
        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        this.setSize(200, 100);
    }
}
