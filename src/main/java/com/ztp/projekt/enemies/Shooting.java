package com.ztp.projekt.enemies;

import com.ztp.projekt.ImgUtils;
import com.ztp.projekt.segment.Segment;

import java.awt.*;
import java.util.ArrayList;

public class Shooting extends Power {

    private boolean isShooting = false;
    private boolean shootDirection = true; //true->w prawo, false-> w lewo
    private int speed = 3;
    private Image bullet;
    private int bulletX;   //wspołrzedne pocisku
    private int bulletY;
    private int W = 26;     //szerokosc
    private int H = 17;   //wysokosc
    private EnemyStrategy strategy;

    public Shooting(Enemy e, EnemyStrategy strategy) {
        super(e);
        bullet = ImgUtils.getImage("bullet3.png");
        bulletX = getX();
        bulletY = getY();
        this.strategy = strategy;
    }

    @Override
    public void usePower() {
        enemy.usePower();
        strategy.nextMove(this);
    }

    @Override
    public void draw(Graphics g, int przesuniecie) {
        enemy.draw(g, getX() + (!shootDirection ? getW() : 0) - przesuniecie, getX() + (!shootDirection ? 0 : getW()) - przesuniecie, getY() + getH(), enemy.getAnim()[enemy.getFrame()] * getW(), 0, enemy.getAnim()[enemy.getFrame()] * getW() + getW(), przesuniecie);
        g.drawImage(bullet, bulletX + (shootDirection ? W : 0) - przesuniecie, bulletY, bulletX + (shootDirection ? 0 : W) - przesuniecie, bulletY + H, 0, 0, W, H, null);
    }

    @Override
    public void draw(Graphics g, int dx1, int dx2, int dy2, int sx1, int sy1, int sx2, int przesuniecie) {
        enemy.draw(g, dx1, dx2, dy2, sx1, sy1, sx2, przesuniecie);
        g.drawImage(bullet, bulletX + (shootDirection ? W : 0) - przesuniecie, bulletY, bulletX + (shootDirection ? 0 : W) - przesuniecie, bulletY + H, 0, 0, W, H, null);
    }

    @Override
    public ArrayList<Rectangle> getEnemyBounds() {
        ArrayList<Rectangle> r = new ArrayList<>();
        r.add(new Rectangle(bulletX, bulletY, W, H));
        r.add(new Rectangle(getX(), getY(), getW(), getH()));   //wkladam obiekt, ktory strzela i pocisk
        return r;
    }

    @Override
    public void tick() {
        enemy.tick();
        usePower();
        // przesuniecie w poziomie
        for (int i = 0; i < Math.abs(speed); ++i) {
            collide((int) Math.signum(speed), 0);
            usePower();
        }
    }

    private void collide(int dx, int dy) {
        for (Segment s : enemy.getBoard()) {
            if (s.getBounds().intersects(bulletX + dx, bulletY + dy, W, H)) {
                if (dx != 0) {
                    isShooting = false;
                    s.collisionH(this);
                }
                if (dy != 0) {
                    isShooting = false;
                    s.collisionV(this);
                }
            }

        }
    }

    @Override
    //wyrzucenie wrogow poza mape, zeby nie kolidowali z playerem
    public void disappear() {
        super.disappear();
        resetBullet();
    }

    public void resetBullet() {
        bulletX = getX();
        bulletY = getY();
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void shoot(int speed, boolean shootDirection) {
        this.shootDirection = shootDirection;
        if (Math.abs(getX() - bulletX) > 300) {   //jezeli pocisk sie oddali o 300 px to resetuje pozycje
            resetBullet();
            isShooting = false;
        } else {
            isShooting = true;
            if (shootDirection)
                bulletX += speed;
            else
                bulletX -= speed;
        }
    }

    @Override
    public void resetPosition() {
        resetBullet();
        enemy.resetPosition();
    }
}
