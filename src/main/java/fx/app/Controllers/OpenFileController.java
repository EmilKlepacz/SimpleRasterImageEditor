package fx.app.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.w3c.dom.*;

import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;


public class OpenFileController {

    @FXML
    private MenuItem openFromFileItem;

    @FXML
    private ImageView imageView;

    private Stage stage;

    private void setImageFromFileInImageView(File file) throws MalformedURLException, FileNotFoundException {
            if (file != null) {
                String imageFilePath = file.toURI().toURL().toString();
                Image image = new Image(imageFilePath);
                imageView.setImage(image);

            } else throw new FileNotFoundException();

    }

    private void openFileChooserAndSetImage() throws MalformedURLException, FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("formats (*.pbm, *.pgm, *.ppm, *.png, *.jpeg)", "*.pbm", "*.pgm", "*.ppm", "*.png", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            readAndDisplayMetadata(selectedFile.getPath());
            setImageFromFileInImageView(selectedFile);
        }
    }

    public void handleOpenFromFile() {
        try {
            openFileChooserAndSetImage();
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleOpenFileInformation(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File statistics");
        alert.setHeaderText("FILE NAME HERE");
        alert.setContentText("There will be file info");

        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void readAndDisplayMetadata( String fileName ) {

        if(fileName!=null) {
            try {

                File file = new File(fileName);
                double bytes = file.length();
                System.out.println("File Size: " + String.format("%.2f", bytes / 1024) + "kb");

                ImageInputStream iis = ImageIO.createImageInputStream(file);
                Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

                if (readers.hasNext()) {

                    // pick the first available ImageReader
                    ImageReader reader = readers.next();

                    // attach source to the reader
                    reader.setInput(iis, true);

                    // read metadata of first image
                    IIOMetadata metadata = reader.getImageMetadata(0);

                    String[] names = metadata.getMetadataFormatNames();
                    for (String name : names) {
                        System.out.println("Format name: " + name);
                        displayMetadata(metadata.getAsTree(name));
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    private void indent(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("    ");
    }

    private void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        System.out.print("<" + node.getNodeName());
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                System.out.print(" " + attr.getNodeName() +
                        "=\"" + attr.getNodeValue() + "\"");
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            System.out.println("/>");
            return;
        }

        // children, so close current tag
        System.out.println(">");
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        System.out.println("</" + node.getNodeName() + ">");
    }
}
