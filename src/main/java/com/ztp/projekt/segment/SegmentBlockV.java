package com.ztp.projekt.segment;

import com.ztp.projekt.Player;
import com.ztp.projekt.enemies.Walking;

/**
 * Segment, na ktory mozna wskoczyc
 */
public class SegmentBlockV extends Segment {

    public SegmentBlockV(int x, int y, String file, char block) {
        super(x, y, file, block);
    }

    @Override
    public void collisionV(Player player) {
        if (player.jumpingDown() && player.getBottomY() == y) {
            player.stopJump();
        }
    }

    @Override
    public void collisionV(Walking walkingEnemy) {
        if (walkingEnemy.jumpingDown() && walkingEnemy.getBottomY() == y) {
            walkingEnemy.stopJump();
        }


    }

}
