package fx.app.controllers;


import fx.app.processing.ImageProcessorMarvin;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NegativeController extends BasicController {
    @FXML
    private ImageView imageViewNegative;

    public void setImage(Image image) {
        this.image = image;
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        imageViewNegative.setImage(image);
    }

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    void addChangesToImage(Image image) {
        imageViewNegative.setImage(image);
        addChangesToHistory(image);
    }

    public void negative(){
        MarvinImage negativeMarvinImage = ImageProcessorMarvin.invertImage(imagePath);
        Image negativeImage = SwingFXUtils.toFXImage(negativeMarvinImage.getBufferedImage(), null);
        imageViewNegative.setImage(negativeImage);
    }
}
