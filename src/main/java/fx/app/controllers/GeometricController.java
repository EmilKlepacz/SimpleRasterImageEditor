package fx.app.controllers;


import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
    @FXML
    private Text widthValText;
    @FXML
    private Text heightValText;

    private OpenFileController openFileController;

    @Override
    protected void addChangesToImage(Image image) {
        imageViewGeometric.setImage(image);
        addChangesToHistory(image);
        saveTemporaryFile(imageViewGeometric.getImage());
    }

    public void undoActionForGeometricController()
    {
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewGeometric.setImage(image);
        saveTemporaryFile(imageViewGeometric.getImage());
    }

    public void saveActionForGeometricController() {
        handleSaveAction(this.openFileController, imageViewGeometric.getImage());
    }

    void setTextInWidthValText(String text){
        this.widthValText.setText(text);
    }

    void setTextInHeightValText(String text){
        this.heightValText.setText(text);
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

    public void rotate(){
        ImagePlus imgPlus = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = imgPlus.getProcessor();
        imgProcessor.rotate(rotateSlider.getValue());

        BufferedImage bufferedImage = imgProcessor.getBufferedImage();
        ImagePlus rotatedImagePlus = new ImagePlus("", bufferedImage);
        Image rotatedImage = SwingFXUtils.toFXImage(rotatedImagePlus.getBufferedImage(), null);

        addChangesToImage(rotatedImage);
        rotateSlider.setValue(0.0);
    }

    public void scale() {
        ImagePlus imgPlus = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = imgPlus.getProcessor();
        int x = Integer.parseInt(widthSpinner.getEditor().getCharacters().toString());
        int y = Integer.parseInt(heightSpinner.getEditor().getCharacters().toString());
        ImageProcessor imageProcessorAfterResize = imgProcessor.resize(x,y, true);


        BufferedImage bufferedImage = imageProcessorAfterResize.getBufferedImage();
        ImagePlus scaledImagePlus = new ImagePlus("", bufferedImage);
        Image scaledImage = SwingFXUtils.toFXImage(scaledImagePlus.getBufferedImage(), null);

        addChangesToImage(scaledImage);
        changeImagePropertiesInText(x, y);
    }

    private void changeImagePropertiesInText(int width, int height){
        widthValText.setText(String.valueOf(width));
        heightValText.setText(String.valueOf(height));
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
