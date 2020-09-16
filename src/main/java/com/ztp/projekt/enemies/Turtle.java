package com.ztp.projekt.enemies;

import com.ztp.projekt.ImgUtils;
import com.ztp.projekt.segment.Segment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Turtle implements Enemy {

    private int x;// pozycja na ekranie x
    private int y; // pozycja na ekranie y
    private int defaultX;
    private int defaultY;
    private Image img;
    private final List<Segment> board;
    private final JFrame owner;

    private final int W = 18; // szerokosc wroga
    private final int H = 26; // wysokosc wroga
    private final int level;
    private int[] anim = {0, 1};
    private int frame = 1; // klatka animacji

    public Turtle(String imgName, int level, int x, int y, List<Segment> board, JFrame owner) {
        this.img = ImgUtils.getImage(imgName);
        this.board = board;
        this.owner = owner;
        this.level = level;
        this.x = x;
        this.y = y;
        this.defaultX = x;
        this.defaultY = y;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void usePower() {}

    @Override
    public int[] getAnim() {
        return anim;
    }

    @Override
    public int getFrame() {
        return frame;
    }

    @Override
    public void setFrame(int value) {
        this.frame = value;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Image getImg() {
        return img;
    }

    @Override
    public int getW() {
        return W;
    }

    @Override
    public int getH() {
        return H;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public List<Segment> getBoard() {
        return board;
    }

    @Override
    public ArrayList<Rectangle> getEnemyBounds() {
        ArrayList<Rectangle> r = new ArrayList<>();
        r.add(new Rectangle(x, y, W, H));
        return r;
    }

    @Override
    public void draw(Graphics g, int dx1, int dx2, int dy2, int sx1, int sy1, int sx2, int przesuniecie) {
        g.drawImage(img, dx1, y, dx2, dy2, sx1, 0, sx2, H, null);
    }

    @Override
    public void disappear() {
        this.y = 300;
    }

    public void tick() {}

    @Override
    public void resetPosition() {
        this.x = defaultX;
        this.y = defaultY;
    }

    @Override
    public int getDefaultX() {
        return defaultX;
    }

    public void draw(Graphics g, int przesuniecie) {
        g.drawImage(getImg(), getX() - przesuniecie, getY(), getX() + W - przesuniecie, getY() + getH(), getW(), 0, getW() + getW(), getH(), null);
    }

}
