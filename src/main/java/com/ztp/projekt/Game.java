package com.ztp.projekt;

import com.ztp.projekt.enemies.*;
import com.ztp.projekt.segment.Segment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//klasa opowiedzialna za tworzenie oraz wyswietlanie poziomow gry
public class Game extends JPanel {

    private final Player player; //obiekt gracza
    private final List<Segment> board; //lista segementow
    private final List<Enemy> enemies; //lista przeciwnikow
    private static int points = 0; //licznik punktow
    private static JFrame owner;
    private static Iterator<Game> iteratorG; //iterator
    private static int przesuniecie = 0;
    private Integer level; //poziom gry
    public boolean gameEnd = false; ////flaga potrzebna do zatrzymania watkow wrogów

    //konstruktor, przyjmujacy numer mapy, ownera oraz iterator, tworzy nowa plansze, uruchamia kontrolery
    public Game(Integer level, JFrame owner, Iterator<Game> iterator) {
        this.owner = owner;
        this.level = level;
        this.iteratorG = iterator;
        setPreferredSize(new Dimension(424, 268));
        setFocusable(true);
        board = createBoard("/data/plansza" + level + ".txt");
        player = Player.getInstance();   //singleton player
        enemies = new ArrayList<>();
        //inicjalizowanie playera
        player.initialize("mario.png", board, (JFrame) this.getParent(), enemies);


        createEnemy();
        createKeyEvent();

        for (Enemy enemy : enemies) {
            //if (enemy.getLevel() == level)
            new Thread(new EnemyController(enemy, board, this, this)).start();
        }
        System.out.println("next");
        new Thread(new Controller(player, board, this, this)).start();
    }

    //tworzenie przeciwnikow
    private void createEnemy() {
        EnemyStrategy easy=new EasyStrategy();
        EnemyStrategy hard=new HardStrategy(player);
        if (level == 1) {
            enemies.add(new Walking(new Turtle("turtle2.png", 1, 200, 105, board, (JFrame) this.getParent()),easy ));
        }
        if (level == 2) {                                                                                                                 //dodawanie wrogow do listy uzaleznione od levelu
            enemies.add(new Walking(new Turtle("turtle2.png", 2, 200, 20, board, (JFrame) this.getParent()), easy));
            enemies.add(new Shooting(new Cannon("cannon3.png", 1, 500, 170, board, (JFrame) this.getParent()), easy));
            enemies.add(new Walking(new Shooting(new Cannon("cannon3.png", 1, 1000, 170, board, (JFrame) this.getParent()), hard), hard));
        }
        if (level == 3) {
            enemies.add(new Walking(new Turtle("turtle2.png", 3, 300, 20, board, (JFrame) this.getParent()), hard));
            enemies.add(new Shooting(new Cannon("cannon3.png", 3, 600, 170, board, (JFrame) this.getParent()), hard));
        }
    }

    //dodanie reakcji na klikniecia przyciskow
    private void createKeyEvent() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ev) {
                switch (ev.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        player.stop();
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent ev) {
                switch (ev.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.right();
                        break;
                    case KeyEvent.VK_SPACE:
                    case KeyEvent.VK_UP:
                        player.jump();
                        break;
                }
            }
        });
    }

    //tworzenie listy segementow
    private List<Segment> createBoard(String file) {
        Builder builder = new BuilderSegment(4, 4, 32);
        String linia;
        int liczba;
        int znaki;
        char znak;
        char cyfra1;
        char cyfra2;
        try (InputStream stream = getClass().getResourceAsStream(file);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            while ((linia = bufferedReader.readLine()) != null) {
                znaki = 0;
                while ((linia.length() - znaki) >= 3) {
                    znak = linia.charAt(znaki++);
                    cyfra1 = linia.charAt(znaki++);
                    cyfra2 = linia.charAt(znaki++);
                    liczba = (cyfra1 - '0') * 10 + (cyfra2 - '0');
                    FabricSegment.createSegment(znak, liczba, builder);
                }
                builder.nextLevel();
            }
            return builder.getMap();
        } catch (IOException e) {
            System.out.println("Error read map");
            e.printStackTrace();
            return null;
        }
    }

    @Override //rysowanie planszy do panelu
    public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);
        Image image = ImgUtils.getImage("tlo.jpg");
        g.drawRenderedImage((RenderedImage) image, null);
        for (Iterator<Segment> iterator = board.iterator(); iterator.hasNext(); ) {
            Segment segment = iterator.next();

            przesuniecie = 0;
            if ((player.getX() - this.getParent().getWidth() / 2) > 0) {
                przesuniecie = player.getX() - this.getParent().getWidth() / 2;
            }

            if (((new Rectangle(segment.getX(), segment.getY() - 16, segment.getW() / 2, segment.getH() / 2)).intersects(player.getX(), player.getY(), player.getW(), player.getH()) && segment.getBlock() == 'G') || ((new Rectangle(segment.getX(), segment.getY(), segment.getW(), segment.getH())).intersects(player.getX(), player.getY(), player.getW(), player.getH()) && segment.getBlock() == 'F')) {
                if (segment.getBlock() == 'G') {
                    iterator.remove();
                    Player.increasePoints();
                    points++;
                } else if (segment.getBlock() == 'F') {
                    blockF(segment);
                }
            }

            if ((segment.getX() + segment.getW() <= 424 && player.getX() <= 160) || (player.getX() > 160 && segment.getX() + segment.getW() <= 424 + (player.getX() - 160))) {
                segment.draw(g, przesuniecie);
            }
        }
        for (int i = 0; i < player.getLifes(); i++)
            g.drawImage(ImgUtils.getImage("heart.png"), 5 + i * 20, 5, null);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        g.drawString(String.valueOf(points), this.getParent().getWidth() - 25, 25);

        for (Enemy enemy : enemies) {
            //  if (enemy.getLevel() == level)
            enemy.draw(g, przesuniecie);
        }

        player.draw(g, przesuniecie);
    }

    //sprawdzanie czy nalezy przejsc do kolejnej mapy czy zakonczyc gre
    private void blockF(Segment segment) {
        if (End.level != App.lvl) {
            player.setMoving(0);
            End.level++;
            owner.dispose();
            player.setX(20);
            player.setY(0);
            gameEnd = true;   //flaga potrzebna do zatrzymania watkow wrogów
            Game game = iteratorG.next();
            owner.getContentPane().removeAll();
            owner.getContentPane().add(new JScrollPane(game));
            owner.pack();
            owner.setVisible(true);
        } else {
            owner.dispose();
            End theFinish = new End("You WON!!", owner, points, segment.getBlock());
            theFinish.setVisible(true);
        }
    }
}
