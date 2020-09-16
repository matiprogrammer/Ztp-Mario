package com.ztp.projekt;

import com.ztp.projekt.enemies.Enemy;
import com.ztp.projekt.segment.Segment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//singleton, klasa gracza
public final class Player {

    private static volatile Player instance = null;

    private Image img; //tekstura gracza
    private List<Segment> board;
    private JFrame owner;

    private static int points = 0; //punkty
    private int[] anim = {0, 1, 2, 1}; //animacje
    private int frame = 2; // klatka animacji
    private boolean mirror = false; // postac patrzy w lewo/ prawo
    private int moving = 0; // ruch w poziomie
    private int jumping = 0; // ruch w pionie
    private int x = 20;// pozycja na ekranie x
    private int y = 0; // pozycja na ekranie y
    private final int W = 16; // szerokosc sprite'a
    private final int H = 27; // wysokosc sprite'a
    private int lifes = 3; //zycia
    private static boolean next = false;
    private List<Enemy> enemies; //przeciwnicy


    //singleton, zwraca instancje gracza
    public static Player getInstance() {
        if (instance == null) {
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player();
                }
            }
        }
        return instance;
    }

    private Player() {
    }

    //inicjalizacja gracza
    public void initialize(String spriteImgName, List<Segment> board, JFrame owner, List<Enemy> enemies) {
        this.img = ImgUtils.getImage(spriteImgName);
        this.board = board;
        this.owner = owner;
        next = false;
        mirror = true;
        this.enemies = enemies;
    }

    //zwraca pozycje X gracza na planszy
    public int getX() {
        return x;
    }

    //zwraca pozycje Y gracza na planszy
    public int getY() {
        return y;
    }

    //zwraca szerokosc tekstury gracza
    public int getW() {
        return W;
    }

    //zwraca wysokosc tekstury gracza
    public int getH() {
        return H;
    }

    //dodanie punktu
    public static void increasePoints() {
        points++;
    }

    public static int getPoints() {
        return points;
    }

    public int getLifes() {
        return lifes;
    }

    public int getBottomY() {
        return y + H;
    }

    //zatrzymanie skoku
    public void stopJump() {
        jumping = 0;
    }

    //spadanie
    public boolean jumpingDown() {
        return jumping < 0;
    }

    //zatrzymanie ruchu
    public void stopMove() {
        moving = 0;
    }

    //ruch w lewo
    public void left() {
        moving = -3;
        mirror = false;
    }

    //ruch w prawo
    public void right() {
        moving = 3;
        mirror = true;
    }

    //zatrzymanie
    public void stop() {
        moving = 0;
        frame = 2;
    }

    //skok
    public void jump() {
        if (jumping == 0) {
            jumping = 10;
        }
    }

    //sprawdzenie kolizji gracza z segmentami z mapy
    private void collide(int dx, int dy) {
        for (Segment s : board) {
            if (s.getBounds().intersects(x + dx, y + dy, W, H)) {
                if (dx != 0) {
                    s.collisionH(this);
                }
                if (dy != 0) {
                    s.collisionV(this);
                }
            }
            for (Enemy enemy : enemies) {   //"enemy" jest ArrayList, w przypadku klasy Shooting wkladamy tam pocisk i armatę, w Walking jest tam tylko 1 obiekt
                for (Rectangle enemyBound : enemy.getEnemyBounds()) {   // bierzemy pocisk i armate
                    if (enemyBound.intersects(x + dx, y + dy, W, H)) {                         //kolidowanie playera z wrogami
//                        resetEnemiesPosition();
//                        lifes--;    //zakomentowac w celow debugowania
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        x = 25;
//                        y = 0;
                    }
                }
            }
        }
    }

    //resetowanie pozycji przeciwnikow
    private void resetEnemiesPosition() {
        for (Enemy enemy : enemies) {
            enemy.resetPosition();
        }
    }

    //klatka gry, odpowiada za odpowiednie poruszanie sie gracza
    public void tick() {
        // animacja ruchu
        if (moving != 0) {
            frame++;
            while (frame >= anim.length) {
                frame -= anim.length;
            }
        }
        // przesuniecie w poziomie
        if ((mirror && x >= 20) || (!mirror && x > 20))
            for (int i = 0; i < Math.abs(moving); ++i) {
                collide((int) Math.signum(moving), 0);
                x += (int) Math.signum(moving);
            }
        // przesuniecie w pionie
        for (int i = 0; i < Math.abs(jumping); ++i) {
            collide(0, -(int) Math.signum(jumping));
            y -= (int) Math.signum(jumping);
        }
        // czy mamy grunt pod nogami?
        jumping--;
        collide(0, 1);
        if (jumping != 0) {
            frame = 0;
        }
        if (jumping == 0 && moving == 0) {
            frame = 2;
        }
        // czy Mario spadl
        if (y > 320) {
            lifes--;
            // przywrócenie Mario na mapę
            x = 25;
            y = 0;
        }
        // koniec żyć, koniec gry
        if (lifes == 0) {
            End theEnd = new End("Koniec Gry", owner, points, 'Q');
            theEnd.setVisible(true);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(img, x + (mirror ? W : 0), y, x + (mirror ? 0 : W), y + H, anim[frame] * W, 0, anim[frame] * W + W, H, null);
    }

    //rysowanie gracza z przesunieciem
    public void draw(Graphics g, int przesuniecie) {
        g.drawImage(img, x + (mirror ? W : 0) - przesuniecie, y, x + (mirror ? 0 : W) - przesuniecie, y + H, anim[frame] * W, 0, anim[frame] * W + W, H, null);
    }

    //ustawienie pozycji X gracza na mapie
    public void setX(int x) {
        this.x = x;
    }

    //ustawienie pozycji Y gracza na mapie
    public void setY(int y) {
        this.y = y;
    }

    public void setMoving(int moving) {
        this.moving = moving;
    }
}
