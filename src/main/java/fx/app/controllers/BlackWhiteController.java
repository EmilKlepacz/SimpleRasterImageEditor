package fx.app.controllers;


import ij.ImagePlus;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhiteController extends BasicController {
    @FXML
    ImageView imageViewBlackWhite;

    @Override
    void setStartImageInImageView(Image image) {
        imageViewBlackWhite.setImage(image);
    }

    @Override
    void addChangesToImage(Image image) {
        imageViewBlackWhite.setImage(image);
        addChangesToHistory(image);
    }

    public void blackAndWhite() throws IOException {
        BufferedImage startingImage;
        BufferedImage blackWhite;
        startingImage = ImageIO.read(new File(imagePath));

        blackWhite = new BufferedImage(startingImage.getWidth(), startingImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = blackWhite.createGraphics();
        g2d.drawImage(startingImage, 0, 0, null);

        ImagePlus blackAndWhiteImagePlus = new ImagePlus("gray", blackWhite);
        Image blackAndWhiteImage = SwingFXUtils.toFXImage(blackAndWhiteImagePlus.getBufferedImage(), null);
        addChangesToImage(blackAndWhiteImage);
    }
}
