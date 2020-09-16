package com.ztp.projekt.segment;

import com.ztp.projekt.ImgUtils;
import com.ztp.projekt.Player;
import com.ztp.projekt.enemies.Shooting;
import com.ztp.projekt.enemies.Walking;

import java.awt.*;

/**
 * Klasa bazowa dla segmentow
 */
public abstract class Segment {

    Image img;
    int x;
    int y;
    int W;
    int H;
    private char block;

    Segment(int x, int y, String file, char block) {
        this.x = x;
        this.y = y;
        img = ImgUtils.getImage(file);
        W = img.getWidth(null);
        H = img.getHeight(null);
        this.block = block;
    }

    Segment(int x, int y, char block) {
        this.x = x;
        this.y = y;
        this.block = block;
    }



    public void setX() {
        this.x -= 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return W;
    }

    public int getH() {
        return H;
    }

    public char getBlock() {
        return block;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, W, H);
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    public void draw(Graphics g, int przesuniecie) {
        g.drawImage(img, x - przesuniecie, y, null);
    }

    public void tick() {
    }

    public void collisionV(Player sprite) {
    }

    public void collisionH(Player sprite) {
    }

    public void collisionV(Walking walkingEnemy) {
    }

    public void collisionH(Walking walkingEnemy) {
    }

    public void collisionV(Shooting bullet) {
    }

    public void collisionH(Shooting bullet) {

    }

}
