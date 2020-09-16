package com.ztp.projekt.enemies;


import com.ztp.projekt.segment.Segment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Enemy {

void usePower();
int getX();
int getY();
void draw(Graphics g, int przesuniecie);
Image getImg();
 void tick();
 int getLevel();
 int getW();
 int getH();
 void setX(int x);
 void setY(int y);
 int[] getAnim();
 int getFrame();
 void setFrame(int value);
 List<Segment> getBoard();
 ArrayList<Rectangle> getEnemyBounds();
 void draw(Graphics g, int dx1, int dx2, int dy2, int sx1, int sy1, int sx2, int przesuniecie);
 void disappear();
 void resetPosition();
 int getDefaultX();


}
