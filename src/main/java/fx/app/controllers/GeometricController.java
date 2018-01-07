package fx.app.controllers;


import fx.app.processing.ImageProcessorMarvin;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

import java.awt.image.BufferedImage;

public class GeometricController extends BasicController {
    @FXML
    private ImageView imageViewGeometric;
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Slider rotateSlider;

    private OpenFileController openFileController;

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    protected void addChangesToImage(Image image) {
        imageViewGeometric.setImage(image);
        addChangesToHistory(image);
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewGeometric.setImage(image);
    }

    public void saveActionForGeometricController() {
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.setImagePath(imagePath);
            openFileController.addChangesToImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void setWidthSpinnerValue(int min, int max, int onStart) {
        SpinnerValueFactory<Integer> widthValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, onStart);
        this.widthSpinner.setValueFactory(widthValueFactory);
    }

    void setHeightSpinnerValue(int min, int max, int onStart) {
        SpinnerValueFactory<Integer> heightValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, onStart);
        this.heightSpinner.setValueFactory(heightValueFactory);
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    public void rotate() {
        ImagePlus imgPlus = new ImagePlus(imagePath);
        ImageProcessor imgProcessor = imgPlus.getProcessor();
        imgProcessor.rotate(rotateSlider.getValue());

        BufferedImage bufferedImage = imgProcessor.getBufferedImage();
        ImagePlus rotatedImagePlus = new ImagePlus("", bufferedImage);
        Image rotatedImage = SwingFXUtils.toFXImage(rotatedImagePlus.getBufferedImage(), null);

        addChangesToImage(rotatedImage);
    }

    public void scale() {
        ImagePlus imgPlus = new ImagePlus(imagePath);
        ImageProcessor imgProcessor = imgPlus.getProcessor();
        ImageProcessor imageProcessorAfterResize = imgProcessor.resize(widthSpinner.getValue(), heightSpinner.getValue(), true);

        BufferedImage bufferedImage = imageProcessorAfterResize.getBufferedImage();
        ImagePlus scaledImagePlus = new ImagePlus("", bufferedImage);
        Image scaledImage = SwingFXUtils.toFXImage(scaledImagePlus.getBufferedImage(), null);

        addChangesToImage(scaledImage);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if (this.openFileController == null)
            this.openFileController = openFileController;
    }
}
