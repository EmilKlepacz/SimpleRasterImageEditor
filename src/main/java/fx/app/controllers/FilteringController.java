package fx.app.controllers;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.IOException;

public class FilteringController extends BasicController {

    @FXML
    ImageView imageViewFiltering;

    private OpenFileController openFileController;

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
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
        setPreviousImageAsActualAndErase();
        imageViewFiltering.setImage(image);
    }

    public void saveActionForFilteringController(){
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.addChangesToImage(image);
        } catch (Exception e){
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
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }

    public void gaussianFilter() throws IOException {
        ImagePlus gaussianBlur = new ImagePlus(temporaryImagePath);
        ImageProcessor imgProcessor = gaussianBlur.getProcessor();
        imgProcessor.blurGaussian(8);  // TO DO EMIL SIGMA FRONTEND

        Image negativeImage = SwingFXUtils.toFXImage(gaussianBlur.getBufferedImage(), null);
        addChangesToImage(negativeImage);
    }
}
