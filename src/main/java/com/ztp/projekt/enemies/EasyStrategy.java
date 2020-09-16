package com.ztp.projekt.enemies;

import com.ztp.projekt.Player;

public class EasyStrategy implements EnemyStrategy {



    @Override
    public void nextMove(Enemy enemy) {
       int moving=0;
        if(enemy instanceof Walking)  //z jakim wrogiem mamy do czynienia
        {
             moving=(((Walking) enemy).getMove());
            if((((Walking) enemy).walkedDistance())>50 ||moving==0)   //gdy przejdzie 50px zawraca i tak w kolko
                moving=1;
            else if((((Walking) enemy).walkedDistance())<=0)
                moving=-1;


            ((Walking)enemy).move(moving);    //wrog, rusz sie
        }
        else if(enemy instanceof  Shooting)
        {
                 ((Shooting)enemy).shoot(1,false);
        }
    }
}
