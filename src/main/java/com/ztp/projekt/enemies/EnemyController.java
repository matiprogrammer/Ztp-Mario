package com.ztp.projekt.enemies;

import com.ztp.projekt.Game;
import com.ztp.projekt.segment.Segment;

import javax.swing.*;
import java.util.List;

public class EnemyController implements Runnable {

    private final Enemy enemy;
    private final List<Segment> board;
    private final JPanel jPanel;
    private final Game game;

    public EnemyController(Enemy enemy, List<Segment> board, JPanel jPanel, Game game) {
        this.enemy = enemy;
        this.board = board;
        this.jPanel = jPanel;
        this.game = game;
    }

    @Override
    public void run() {
        while (!game.gameEnd) {
            enemy.tick();
            jPanel.repaint();
            Thread.yield();
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        enemy.disappear();   //wyrzucenie wrogow poza mape, zeby nie kolidowali z playerem
    }
}
