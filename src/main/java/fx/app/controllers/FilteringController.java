package fx.app.controllers;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        addChangesToHistory(image);
        saveTemporaryFile(imageViewFiltering.getImage());
    }

    public void undoActionForFilterController(){
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        imageViewFiltering.setImage(image);
        saveTemporaryFile(imageViewFiltering.getImage());
        sigmaSlider.setValue(0.0);
    }

    public void saveActionForFilteringController(){
        handleSaveAction(this.openFileController, imageViewFiltering.getImage());
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

        ImagePlus laplace = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = laplace.getProcessor();
        imgProcessor.findEdges();

        Image finalImage = SwingFXUtils.toFXImage(laplace.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void sharpen(){
        setAllSlidersInvisible();

        ImagePlus sharpenImg = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = sharpenImg.getProcessor();
        imgProcessor.sharpen();

        Image finalImage = SwingFXUtils.toFXImage(sharpenImg.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void smooth(){
        setAllSlidersInvisible();

        ImagePlus smoothImg = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = smoothImg.getProcessor();
        imgProcessor.smooth();

        Image finalImage = SwingFXUtils.toFXImage(smoothImg.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }

    public void noise(){
        ImagePlus noiseImg = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = noiseImg.getProcessor();
        imgProcessor.noise(50); // ADD HERE VALUE FROM FRONTEND

        Image finalImage = SwingFXUtils.toFXImage(noiseImg.getBufferedImage(), null);
        addChangesToImage(finalImage);
    }
}
