package com.ztp.projekt.enemies;

import com.ztp.projekt.segment.Segment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Power implements Enemy {

    protected Enemy enemy;

    Power(Enemy e) {
        this.enemy = e;
    }

@Override
public void resetPosition()
{    enemy.resetPosition();
}
@Override
public int getDefaultX()
{
    return enemy.getDefaultX();
}
    @Override
    public int getX() {
        return enemy.getX();
    }

    @Override
    public int getY() {
        return enemy.getY();
    }

    @Override
    public void setX(int x) {
        enemy.setX(x);
    }

    @Override
    public void setY(int y) {
        enemy.setY(y);
    }

    @Override
    public ArrayList<Rectangle> getEnemyBounds() {
        return enemy.getEnemyBounds();
    }

    @Override
    public int getLevel() {
        return enemy.getLevel();
    }

    @Override
    public int getW() {
        return enemy.getW();
    }

    @Override
    public int getH() {
        return enemy.getH();
    }

    @Override
    public void usePower() {enemy.usePower(); }

    @Override
    public List<Segment> getBoard() {
        return enemy.getBoard();
    }

    @Override
    public Image getImg() {
        return enemy.getImg();
    }

    @Override
    public int[] getAnim() {
        return enemy.getAnim();
    }

    @Override
    public int getFrame() {
        return enemy.getFrame();
    }

    @Override
    public void setFrame(int value) {
        enemy.setFrame(value);
    }

    @Override
    public void disappear() {
        enemy.disappear();
    }
}
