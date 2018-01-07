package fx.app.controllers;


import fx.app.processing.ImageProcessorMarvin;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NegativeController extends BasicController {

    private static final String START_VIEW_PATH = "/views/start_view.fxml";

    private OpenFileController openFileController;

    @FXML
    private ImageView imageViewNegative;
    private boolean[] rgbChannels = {false,false,false};

    public void setImage(Image image) {
        this.image = image;
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    protected void addChangesToImage(Image image) {
        imageViewNegative.setImage(image);
        addChangesToHistory(image);
    }

    public void undoActionForNegativeController(){
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewNegative.setImage(image);
    }

    public void saveActionForNegativeController(){
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.setImagePath(imagePath);
            openFileController.addChangesToImage(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void negative(){

        MarvinImage negativeMarvinImage = ImageProcessorMarvin.invertImage(imagePath);
        Image negativeImage = SwingFXUtils.toFXImage(negativeMarvinImage.getBufferedImage(), null);
        addChangesToImage(negativeImage);
    }

    public void channelR(){
        resetChannels();
        rgbChannels[1] = true;
        rgbChannels[2] = true;
        negativeChannels(rgbChannels);
    }
    public void channelG(){
        resetChannels();
        rgbChannels[0] = true;
        rgbChannels[2] = true;
        negativeChannels(rgbChannels);
    }
    public void channelB(){
        resetChannels();
        rgbChannels[0] = true;
        rgbChannels[1] = true;
        negativeChannels(rgbChannels);
    }
    public void channelRG(){
        resetChannels();
        rgbChannels[2] = true;
        negativeChannels(rgbChannels);
    }
    public void channelRB(){
        resetChannels();
        rgbChannels[1] = true;
        negativeChannels(rgbChannels);
    }
    public void channelGB(){
        resetChannels();
        rgbChannels[0] = true;
        negativeChannels(rgbChannels);
    }

    private void resetChannels(){
        rgbChannels[0] = false;
        rgbChannels[1] = false;
        rgbChannels[2] = false;
    }

    private void negativeChannels(boolean rgb[]){

        ImagePlus imgPlus = new ImagePlus(imagePath);
        ImageProcessor imgProcessor = imgPlus.getProcessor();
        imgProcessor.invert();
        int r,g,b;

        BufferedImage bufferedImage = imgProcessor.getBufferedImage();
        for(int y=0;y<bufferedImage.getHeight();y++)
        {
            for(int x=0;x<bufferedImage.getWidth();x++)
            {
                Color color = new Color(bufferedImage.getRGB(x, y));
                r = color.getRed();
                g = color.getGreen();
                b =  color.getBlue();
                if(rgb[0]){
                    r  = 255-r;
                }
                if(rgb[1]){
                    g = 255 - g;
                }
                if(rgb[2]){
                    b = 255 - b;
                }

                Color finalImage = new Color(r, g, b);

                bufferedImage.setRGB(x, y, finalImage.getRGB());
            }
        }
        ImagePlus grayImg = new ImagePlus("", bufferedImage);
        Image negativeImage = SwingFXUtils.toFXImage(grayImg.getBufferedImage(), null);
        addChangesToImage(negativeImage);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
