package com.ztp.projekt;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Iterator;

public class App {

    static List<Game> lista;
    public static Long timeStart;
    public static final Integer lvl = 3;
    private static final JFrame frame = new JFrame("Mario");
    private static final JFrame frame1 = new JFrame("Mario");

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);

        JButton button = new JButton("Start game");
        JLabel jLabel1 = new JLabel(new ImageIcon(ImgUtils.getImage("super-mario2.jpg")));

        //usuniecie obramowan oraz tla przycisku
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        //ustawienie rozmiaru, czcionki
        button.setBounds(121, 5, 400, 40);
        button.setFont(new Font("arial", Font.ITALIC, 40));
        button.setForeground(Color.WHITE);

        //dodanie akcji uruchomienia gry
        button.addActionListener(e -> {
            timeStart = System.currentTimeMillis();
            Iteratorr iteratorr = new Iteratorr();
            Iterator<Game> iter = iteratorr.iterator();
            //initList(lvl, iter);
            Game game = iter.next();
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.getContentPane().add(new JScrollPane(game));
            frame1.pack();
            frame1.setVisible(true);
            frame.dispose();
        });

        //ustawienie tla, rozmiaru okna, dodanie przycisku
        frame.setContentPane(jLabel1);
        frame.setSize(jLabel1.getIcon().getIconWidth(), jLabel1.getIcon().getIconHeight());
        frame.add(button);
    }

    //Iterator zwracajacy kolejne poziomy gry
    static class Iteratorr implements Iterable<Game> {
        @Override
        public Iterator<Game> iterator() {
            Iterator<Game> basicIterator = new Iterator<Game>() {
                int flag = 0;

                @Override
                public boolean hasNext() {
                    return flag < 4;
                }

                @Override
                public Game next() {
                    flag++;
                    return new Game(flag, frame1, this);
                }

                @Override
                public void remove() {

                }
            };
            return basicIterator;
        }
    }

}
