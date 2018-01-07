package fx.app.controllers;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FilteringController extends BasicController {

    @FXML
    ImageView imageViewFiltering;
    @FXML
    Slider sigmaSlider;

    private OpenFileController openFileController;

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    protected void addChangesToImage(Image image) {
        imageViewFiltering.setImage(image);
        addChangesToHistory(image);
        saveTemporaryFile(imageViewFiltering.getImage());
    }

    public void undoActionForFilterController() {
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewFiltering.setImage(image);
        saveTemporaryFile(imageViewFiltering.getImage());
    }

    public void saveActionForFilteringController() {
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.addChangesToImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if (this.openFileController == null)
            this.openFileController = openFileController;
    }

    public void gaussianFilter() throws IOException {

        ImagePlus gaussianBlur = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = gaussianBlur.getProcessor();
        imgProcessor.blurGaussian(sigmaSlider.getValue());

        Image finalImage = SwingFXUtils.toFXImage(gaussianBlur.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void meanFilter() throws IOException {

        BufferedImage img = ImageIO.read(new File(temporaryImagePath));

        //get dimensions
        int maxHeight = img.getHeight();
        int maxWidth = img.getWidth();

        //create 2D Array for new picture
        int pictureFile[][] = new int[maxHeight][maxWidth];
        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                pictureFile[i][j] = img.getRGB(j, i);
            }
        }

        int output[][] = new int[maxHeight][maxWidth];

        //Apply Mean Filter
        for (int v = 1; v < maxHeight; v++) {
            for (int u = 1; u < maxWidth; u++) {
                //compute filter result for position (u,v)

                int sum = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        if ((u + (j) >= 0 && v + (i) >= 0 && u + (j) < maxWidth && v + (i) < maxHeight)) {
                            int p = pictureFile[v+(i)][u+(j)];
                            sum = sum + p;
                        }
                    }
                }

                int q = (int) (sum /9);
                output[v][u] = q;
            }
        }

        //Turn the 2D array back into an image
        BufferedImage theImage = new BufferedImage(
                maxHeight,
                maxWidth,
                BufferedImage.TYPE_INT_ARGB);
        int value;
        for (int y = 1; y < maxHeight; y++) {
            for (int x = 1; x < maxWidth; x++) {
                value = output[y][x];
                theImage.setRGB(x, y, value);
            }
        }

        ImagePlus grayImg = new ImagePlus("mean", theImage);
        Image negativeImage = SwingFXUtils.toFXImage(grayImg.getBufferedImage(), null);
        addChangesToImage(negativeImage);
    }

    public void laplacianFilter(){
        ImagePlus laplace = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = laplace.getProcessor();
        imgProcessor.findEdges();


        Image finalImage = SwingFXUtils.toFXImage(laplace.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void setSigmaSliderVisible(){
        sigmaSlider.setVisible(true);
    }

}
