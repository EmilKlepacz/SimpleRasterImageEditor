package fx.app.controllers;


import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GreyscaleController extends BasicController{

    @FXML
    private ImageView imageViewGreyscale;

    @Override
    void setStartImageInImageView(Image image) {
        imageViewGreyscale.setImage(image);
    }

    @Override
    void addChangesToImage(Image image) {
        imageViewGreyscale.setImage(image);
        addChangesToHistory(image);
    }

    public void greyscale(){
        ImagePlus imgPlus = new ImagePlus(imagePath);
        ImageProcessor imgProcessor = imgPlus.getProcessor();
        int r,g,b;

        BufferedImage bufferedImage = imgProcessor.getBufferedImage();
        for(int y=0;y<bufferedImage.getHeight();y++)
        {
            for(int x=0;x<bufferedImage.getWidth();x++)
            {
                Color color = new Color(bufferedImage.getRGB(x, y));
                int grayLevel = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                r = grayLevel;
                g = grayLevel;
                b = grayLevel;
                int rgb = (r<<16)  | (g<<8)  | b;
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        ImagePlus grayImg = new ImagePlus("gray", bufferedImage);
        Image grayScale = SwingFXUtils.toFXImage(grayImg.getBufferedImage(), null);
        addChangesToImage(grayScale);
    }
}
