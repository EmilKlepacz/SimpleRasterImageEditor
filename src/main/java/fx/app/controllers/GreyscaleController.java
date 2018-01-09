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

    private OpenFileController openFileController;

    @Override
    protected void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    @Override
    protected void addChangesToImage(Image image) {
        imageViewGreyscale.setImage(image);
        addChangesToHistory(image);
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewGreyscale.setImage(image);
    }

    public void saveActionForHistogramController(){
        handleSaveAction(this.openFileController, imageViewGreyscale.getImage());
    }

    public void undoActionForGrayscaleController(){
        handleUndoAction();
    }

    public void greyscale(){
        BufferedImage buff = SwingFXUtils.fromFXImage(imageViewGreyscale.getImage(), null);
        ImagePlus imgPlus = new ImagePlus("", buff);
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

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {

        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
