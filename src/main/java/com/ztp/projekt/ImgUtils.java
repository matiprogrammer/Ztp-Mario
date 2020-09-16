package com.ztp.projekt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

//klasa wspomagajaca, sluzaca do wgrywania grafik
public class ImgUtils {
    /**
     * Laduje i zwraca obraz z katalogu /img/ w resources
     *
     * @param imgName nazwa pliku
     * @return zaladowany obraz
     */
    public static Image getImage(String imgName) {
        InputStream fileName = ImgUtils.class.getResourceAsStream("/img/" + imgName);
        BufferedImage img;
        try {
            img = ImageIO.read(fileName);
        } catch (IOException ex) {
            Logger.getLogger(ImgUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return img;
    }
}
