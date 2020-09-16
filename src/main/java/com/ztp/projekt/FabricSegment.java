package com.ztp.projekt;
//klasa wzorca Fabryka, uzywana do tworzenia struktury mapy, wspolpracuje w builderem
class FabricSegment {

    static void createSegment(char znak, int liczba, Builder builder) {
        switch (znak) {
            case 'X':
                builder.addSegmentX(liczba, znak);
                break;
            case 'A':
                builder.addSegmentBlock(liczba, znak);
                break;
            case 'B':
                builder.addSegmentBlockV(liczba, znak);
                break;
            case 'C':
                builder.addSegmentAir(liczba, znak);
                break;
            case 'G':
                builder.addSegmentAnim(liczba, znak);
                break;
            case 'F':
                builder.addSegmentFinal(liczba, znak);
                break;
        }
    }
}
