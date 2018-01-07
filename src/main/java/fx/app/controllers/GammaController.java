package fx.app.controllers;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GammaController extends BasicController {
    @FXML
    private ImageView imageViewGamma;

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        imageViewGamma.setImage(image);
    }
}
