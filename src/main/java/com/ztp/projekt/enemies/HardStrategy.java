package com.ztp.projekt.enemies;

import com.ztp.projekt.Player;

public class HardStrategy implements EnemyStrategy {

    private Player player;

    public HardStrategy(Player player)
    {
        this.player=player;
    }
    @Override
    public void nextMove(Enemy enemy) {
        if(enemy instanceof Walking)
        {

            if(Math.abs(player.getX()-enemy.getX())<300) {
                if(player.getX()<enemy.getX())
                ((Walking) enemy).move(-1);
            else((Walking) enemy).move(1);
            }

            if(player.getY()+player.getH()<enemy.getY()+enemy.getH())
                ((Walking)enemy).jump();
        }
        else if(enemy instanceof  Shooting)
        {   boolean shootDirection;

                if (player.getX()>enemy.getX())                    //sprawdzenie, w kotra strone ma strzelac wrog
                    shootDirection=true;
                else shootDirection=false;

            ((Shooting)enemy).shoot(2,shootDirection);
        }
    }
}
