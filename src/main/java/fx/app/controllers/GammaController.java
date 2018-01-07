package fx.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GammaController extends BasicController {
    @FXML
    private ImageView imageViewGamma;

    private OpenFileController openFileController;

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    protected void addChangesToImage(Image image) {
        imageViewGamma.setImage(image);
        addChangesToHistory(image);
    }

    public void undoActionForGammaController(){
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewGamma.setImage(image);
    }

    public void saveActionForGammaController(){
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.addChangesToImage(image);
            saveTemporaryFile(imageViewGamma.getImage());
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
}
