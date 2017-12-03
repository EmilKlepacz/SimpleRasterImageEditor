package fx.app.Controllers;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GammaController {
    @FXML
    ImageView imageViewGamma;

    private Image image;

    public void setImage(Image image) {
        this.image = image;
    }

    // this image is copy of image in start_view
    public void setStartImageInImageView(){
        imageViewGamma.setImage(image);
    }
}
