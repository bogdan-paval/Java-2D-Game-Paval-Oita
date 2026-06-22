package PaooGame.Graphics;

import PaooGame.exceptions.ResourceLoadException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageLoader {

    public static BufferedImage LoadImage(String path) throws ResourceLoadException {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (Exception e) {
            throw new ResourceLoadException(path, e);
        }
    }
}