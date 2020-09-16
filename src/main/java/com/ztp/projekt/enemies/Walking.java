package com.ztp.projekt.enemies;

import com.ztp.projekt.segment.Segment;

import java.awt.*;
import java.util.List;

public class Walking extends Power {

    private int moving = 0; // ruch w poziomie
    private int jumping = 0; // ruch w pionie
    private boolean mirror = true;
    private boolean isJumping = false;
    private EnemyStrategy strategy;

    public Walking(Enemy e, EnemyStrategy strategy) {
        super(e);
        this.strategy = strategy;
    }

    @Override
    public void usePower() {
        enemy.usePower();
        strategy.nextMove(this);
    }

    public void move(int moving)
    {   this.moving=moving;
        if(moving>0)mirror=false;
        else mirror=true;
    }

    public int getMove() {
    return moving;
}

    @Override
    public void draw(Graphics g, int przesuniecie) {
        enemy.draw(g, getX() + (mirror ? getW() : 0) - przesuniecie, getX() + (mirror ? 0 : getW()) - przesuniecie, getY() + getH(), enemy.getAnim()[enemy.getFrame()] * getW(), 0, enemy.getAnim()[enemy.getFrame()] * getW() + getW(), przesuniecie);
    }

    @Override
    public void draw(Graphics g, int dx1, int dx2, int dy2, int sx1, int sy1, int sx2, int przesuniecie) {
        enemy.draw(g, dx1, dx2, dy2, sx1, sy1, sx2, przesuniecie);
    }

    @Override
    public void tick() {
        enemy.tick();
        usePower();
        // animacja ruchu
        if (moving != 0) {
            enemy.setFrame(enemy.getFrame() + 1);
            while (enemy.getFrame() >= enemy.getAnim().length) {
                enemy.setFrame(enemy.getFrame() - enemy.getAnim().length);
            }
        }
        // przesuniecie w poziomie
        if ((!mirror && getX() >= 20) || (mirror && getX() > 20))
            for (int i = 0; i < Math.abs(moving); ++i) {
                collide((int) Math.signum(moving), 0);
                setX(getX() + (int) Math.signum(moving));

            }
        // przesuniecie w pionie
        for (int i = 0; i < Math.abs(jumping); ++i) {
            collide(0, -(int) Math.signum(jumping));
            setY(getY() - (int) Math.signum(jumping));
        }
        // czy mamy grunt pod nogami?
        jumping--;
        collide(0, 1);
        if (jumping != 0) {
            enemy.setFrame(0);
        }
        if (getY() > 320) {
            setY(400);
        }
    }
    public int walkedDistance()
    {
        return enemy.getDefaultX()-enemy.getX();
    }
    public void stopJump() {
        jumping = 0;
    }

    public void jump() {
        if (jumping == 0 && !isJumping) {
            jumping = 5;
            isJumping = true;
        }
    }

    public boolean jumpingDown() {
        return jumping < 0;
    }

    public void stopMove() {
        moving = 0;
    }

    public int getBottomY() {
        return getY() + getH();
    }

    private void collide(int dx, int dy) {
        for (Segment s : enemy.getBoard()) {
            if (s.getBounds().intersects(getX() + dx, getY() + dy, getW(), getH())) {
                if (dx != 0) {
                    s.collisionH(this);
                }
                if (dy != 0) {
                    s.collisionV(this);
                    isJumping = false;
                }
            }
        }
    }

}
