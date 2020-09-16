package com.ztp.projekt;

import com.ztp.projekt.segment.Segment;

import javax.swing.*;
import java.util.List;

//klasa kontrolujaca gracza
class Controller implements Runnable {

    private final Player player;
    private final List<Segment> board;
    private final JPanel jPanel;
    private final Game game;

    public Controller(Player player, List<Segment> board, JPanel jPanel, Game game) {
        this.player = player;
        this.board = board;
        this.jPanel = jPanel;
        this.game = game;
    }

    @Override
    public void run() {
        while (!game.gameEnd) {
            player.tick();
            for (Segment s : board) {
                s.tick();
            }
            jPanel.repaint();
            Thread.yield();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
