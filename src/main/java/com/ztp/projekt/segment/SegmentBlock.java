package com.ztp.projekt.segment;

import com.ztp.projekt.Player;
import com.ztp.projekt.enemies.Shooting;
import com.ztp.projekt.enemies.Walking;

/**
 * Segment bez mozliwosci przejscia
 */
public class SegmentBlock extends Segment {

    public SegmentBlock(int x, int y, String file, char block) {
        super(x, y, file, block);
    }

    @Override
    public void collisionV(Player player) {
        player.stopJump();
    }

    @Override
    public void collisionH(Player player) {
        player.stopMove();
    }

    @Override
    public void collisionV(Walking walkingEnemy) {
        walkingEnemy.stopJump();
    }

    @Override
    public void collisionH(Walking walkingEnemy) {
        walkingEnemy.stopMove();
    }

    @Override
    public void collisionV(Shooting bullet) {
        bullet.resetBullet();
    }

    @Override
    public void collisionH(Shooting bullet) {
        bullet.resetBullet();
    }

}
