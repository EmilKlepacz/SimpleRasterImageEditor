package fx.app.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GeometricController extends BasicController {
    @FXML
    private ImageView imageViewGeometric;
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;

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
        imageViewGeometric.setImage(image);
    }

}
