package com.ztp.projekt;

import com.ztp.projekt.segment.Segment;

import java.util.List;

//klasa wzorca Builder
interface Builder {

    void addSegmentX(int liczba, char znak);
    void addSegmentBlock(int liczba, char block);
    void addSegmentBlockV(int liczba, char block);
    void addSegmentAnim(int liczba, char block);
    void addSegmentAir(int liczba, char block);
    void addSegmentFinal(int liczba, char block);
    void nextLevel();
    List<Segment> getMap();

}
