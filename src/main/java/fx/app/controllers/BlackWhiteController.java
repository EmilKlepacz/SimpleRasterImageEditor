package fx.app.controllers;


import ij.ImagePlus;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhiteController extends BasicController {
    @FXML
    ImageView imageViewBlackWhite;

    private OpenFileController openFileController;

    @Override
    protected void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    @Override
    protected void addChangesToImage(Image image) {
        imageViewBlackWhite.setImage(image);
        addChangesToHistory(image);
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewBlackWhite.setImage(image);
    }

    public void saveActionForBlackWhiteController(){
        handleSaveAction(this.openFileController, imageViewBlackWhite.getImage());
    }

    public void undoActionForBlackWhiteController(){
        handleUndoAction();
    }

    public void blackAndWhite() throws IOException {
        BufferedImage startingImage;
        BufferedImage blackWhite;
        startingImage = SwingFXUtils.fromFXImage(imageViewBlackWhite.getImage(), null);

        blackWhite = new BufferedImage(startingImage.getWidth(), startingImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = blackWhite.createGraphics();
        g2d.drawImage(startingImage, 0, 0, null);

        ImagePlus blackAndWhiteImagePlus = new ImagePlus("gray", blackWhite);
        Image blackAndWhiteImage = SwingFXUtils.toFXImage(blackAndWhiteImagePlus.getBufferedImage(), null);
        addChangesToImage(blackAndWhiteImage);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
