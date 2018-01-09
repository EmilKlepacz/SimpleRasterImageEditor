package fx.app.controllers;

import fx.app.processing.ImageProcessorMarvin;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class FilteringController extends BasicController {

    @FXML
    ImageView imageViewFiltering;
    @FXML
    Slider sigmaSlider;
    @FXML
    Slider noiseSlider;


    private OpenFileController openFileController;

    @Override
    protected void addChangesToImage(Image image) {
        imageViewFiltering.setImage(image);
    }

    public void undoActionForFilterController(){
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewFiltering.setImage(image);
        resetSlidersValues();
    }

    public void saveActionForFilteringController(){
        resetSlidersValues();
        addChangesToHistory(imageViewFiltering.getImage());
        handleSaveAction(this.openFileController, imageViewFiltering.getImage());
    }

    public void setLastUnsavedImage(){
        resetSlidersValues();
        imageViewFiltering.setImage(image);
    }

    private void resetSlidersValues(){
        sigmaSlider.setValue(0.0);
        noiseSlider.setValue(0.0);
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
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }

    public void gaussianFilter() throws IOException {

        Image tempImage = image;
        BufferedImage buff = SwingFXUtils.fromFXImage(tempImage, null);
        ImagePlus gaussianBlur = new ImagePlus("ForGaussianBlur", buff);
        ImageProcessor imgProcessor = gaussianBlur.getProcessor();
        imgProcessor.blurGaussian(sigmaSlider.getValue());

        Image finalImage = SwingFXUtils.toFXImage(gaussianBlur.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void setSigmaSliderVisible(){
        sigmaSlider.setVisible(true);
        noiseSlider.setVisible(false);
    }

    public void setNoiseSliderVisible(){
        noiseSlider.setVisible(true);
        sigmaSlider.setVisible(false);
    }

    private void setAllSlidersInvisible(){
        noiseSlider.setVisible(false);
        sigmaSlider.setVisible(false);
    }


    public void laplacianFilter(){
        setAllSlidersInvisible();

        Image tempImage = image;
        BufferedImage buff = SwingFXUtils.fromFXImage(tempImage, null);
        ImagePlus laplace = new ImagePlus("", buff);
        ImageProcessor imgProcessor = laplace.getProcessor();
        imgProcessor.findEdges();

        Image finalImage = SwingFXUtils.toFXImage(laplace.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void sharpen(){
        setAllSlidersInvisible();

        Image tempImage = image;
        BufferedImage buff = SwingFXUtils.fromFXImage(tempImage, null);
        ImagePlus sharpenImg = new ImagePlus("", buff);
        ImageProcessor imgProcessor = sharpenImg.getProcessor();
        imgProcessor.sharpen();

        Image finalImage = SwingFXUtils.toFXImage(sharpenImg.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }


    public void smooth(){
        setAllSlidersInvisible();

        Image tempImage = image;
        BufferedImage buff = SwingFXUtils.fromFXImage(tempImage, null);
        ImagePlus smoothImg = new ImagePlus("", buff);
        ImageProcessor imgProcessor = smoothImg.getProcessor();
        imgProcessor.smooth();

        Image finalImage = SwingFXUtils.toFXImage(smoothImg.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void noise(){

        Image tempImage = image;
        BufferedImage buff = SwingFXUtils.fromFXImage(tempImage, null);
        ImagePlus noiseImg = new ImagePlus("", buff);
        ImageProcessor imgProcessor = noiseImg.getProcessor();
        imgProcessor.noise(noiseSlider.getValue());

        Image finalImage = SwingFXUtils.toFXImage(noiseImg.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void edgeDetectorSobel(){
        setAllSlidersInvisible();

        BufferedImage buff = SwingFXUtils.fromFXImage(image, null);
        MarvinImage sobelMarvinImage = ImageProcessorMarvin.edgeDetectorSobel(buff, getImageFormat());
        Image sobelImage = SwingFXUtils.toFXImage(sobelMarvinImage.getBufferedImage(), null);
        addChangesToImage(sobelImage);
    }


}
