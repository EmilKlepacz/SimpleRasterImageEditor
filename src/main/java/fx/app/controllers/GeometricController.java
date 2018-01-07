package fx.app.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

public class GeometricController extends BasicController {
    @FXML
    private ImageView imageViewGeometric;
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;

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

    public void saveActionForGeometricController(){
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
        MarvinImage rotateMarvinImage = ImageProcessorMarvin.rotate(imagePath, 90);
        Image negativeImage = SwingFXUtils.toFXImage(rotateMarvinImage.getBufferedImage(), null);
        imageViewGeometric.setImage(negativeImage);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
