package fx.app.controllers;


import fx.app.processing.ImageNegativeMaker;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

public class NegativeController implements BasicController {
    @FXML
    private ImageView imageViewNegative;
    @FXML
    private Button standNegBtn;

    private Image image;

    private String imagePath;

    public void setImage(Image image) {
        this.image = image;
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        imageViewNegative.setImage(image);
    }

    @Override
    public void setImagePath(String filePath) {
        imagePath = filePath;
    }

    public void negative(){
        ImageNegativeMaker imgNegativeMaker = new ImageNegativeMaker();
        MarvinImage negativeMarvinImage = imgNegativeMaker.invertImage(imagePath);
        Image negativeImage = SwingFXUtils.toFXImage(negativeMarvinImage.getBufferedImage(), null);
        imageViewNegative.setImage(negativeImage);


    }

}
