package com.ztp.projekt.segment;

import java.awt.*;

/**
 * Segment animowany
 */
public class SegmentAnim extends Segment {

    private final int[] anim;
    private int frame = 0;

    public SegmentAnim(int x, int y, String file, int[] sequence, char block) {
        super(x, y, file, block);
        anim = sequence;
    }


    @Override
    public void tick() {
        frame++;
        while (frame >= anim.length) {
            frame -= anim.length;
        }
    }

    @Override
    public void draw(Graphics g, int przesuniecie) {
        g.drawImage(img, x - przesuniecie, y, x + W - przesuniecie, y + H / 4, 0, anim[frame] * H / 4, W, anim[frame] * H / 4 + H / 4, null);
    }

}
