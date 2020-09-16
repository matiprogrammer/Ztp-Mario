package com.ztp.projekt;

import com.ztp.projekt.segment.*;

import java.util.ArrayList;
import java.util.List;

public class BuilderSegment implements Builder {

    private final List<Segment> board;
    private int x;
    private int y;
    private final int TITLE_SIZE;

    public BuilderSegment(int x, int y, int titleSize) {
        this.board = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.TITLE_SIZE = titleSize;
    }

    @Override //dodanie pustego segmentu
    public void addSegmentX(int liczba, char znak) {
        for (int i = 0; i < liczba; i++) {
            Segment s = new SegmentX(x, y, null, znak);
            board.add(s);
            x += TITLE_SIZE;
        }
    }

    @Override //dodanie segemntu podstawy platformy
    public void addSegmentBlock(int liczba, char znak) {
        for (int i = 0; i < liczba; i++) {
            Segment s = new SegmentBlock(x, y, "block1.png", znak);
            board.add(s);
            x += TITLE_SIZE;
        }
    }

    @Override //dodanie segmentu przez ktory mozna przejsc
    public void addSegmentBlockV(int liczba, char znak) {
        for (int i = 0; i < liczba; i++) {
            Segment s = new SegmentBlockV(x, y, "block2.png", znak);
            board.add(s);
            x += TITLE_SIZE;
        }
    }

    @Override //dodanie monety z animacja
    public void addSegmentAnim(int liczba, char znak) {
        for (int i = 0; i < liczba; i++) {
            Segment s = new SegmentAnim(x, y, "bonus.png", new int[]{0, 0, 0, 1, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 1, 0, 0}, znak);
            board.add(s);
            x += TITLE_SIZE;
        }
    }

    @Override //dodanie segmentu przez ktory nie mozna przejsc
    public void addSegmentAir(int liczba, char znak) {
        for (int i = 0; i < liczba; ++i) {
            Segment s = new SegmentBlock(x, y, "block3.png", znak);
            board.add(s);
            x += TITLE_SIZE;
        }
    }

    @Override //dodanie segmentu konca mapy/gry
    public void addSegmentFinal(int liczba, char znak) {
        for (int i = 0; i < liczba; ++i) {
            Segment s = new SegmentFinal(x, y, "finish.png", znak);
            board.add(s);
            x += TITLE_SIZE;
        }
    }

    @Override
    public void nextLevel() {
        y += TITLE_SIZE;
        x = 4;
    }

    @Override //zwraca liste segementow
    public List<Segment> getMap() {
        return board;
    }

}
