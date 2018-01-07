package fx.app.controllers;

import fx.app.processing.ImageProcessorMarvin;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

public class HistogramController extends BasicController {
    @FXML
    private ImageView imageViewHistogram;

    private OpenFileController openFileController;

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
        imageViewHistogram.setImage(image);
        addChangesToHistory(image);
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewHistogram.setImage(image);
    }

    public void saveActionForHistogramController(){
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.addChangesToImage(image);
            saveTemporaryFile(imageViewHistogram.getImage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void undoActionForHistogramController(){
        handleUndoAction();
    }

    public void equalization(){
        MarvinImage histogramEqualImg = ImageProcessorMarvin.histogramEqualization(temporaryImagePath);
        Image histogramEqualImage = SwingFXUtils.toFXImage(histogramEqualImg.getBufferedImage(), null);
        imageViewHistogram.setImage(histogramEqualImage);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
