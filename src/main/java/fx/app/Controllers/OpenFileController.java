package fx.app.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;


public class OpenFileController {

    private static final String GAMMA_VIEW_PATH = "/views/gamma_view.fxml";
    private static final String NEGATIVE_VIEW_PATH ="/views/negative_view.fxml";
    private static final String GAMMA_STAGE_TITLE = "Gamma correction";
    private static final String NEGATIVE_STAGE_TITLE = "Negative operation";

    @FXML
    private StackPane stackPane;
    @FXML
    private Button gammaBtn;
    @FXML
    private Button negativeBtn;
    @FXML
    private Button filteringBtn;
    @FXML
    private Button histogramBtn;
    @FXML
    private Button geometricBtn;
    @FXML
    private ImageView imageView;

    private Stage stage;
    private Image image;


    private void enableOperationButtons(){
        gammaBtn.setDisable(false);
        negativeBtn.setDisable(false);
        filteringBtn.setDisable(false);
        histogramBtn.setDisable(false);
        geometricBtn.setDisable(false);
    }
    
    private void setImageHightWidthFitBorderPane(Image image){
        imageView.fitHeightProperty().bind(stackPane.widthProperty());
        imageView.fitWidthProperty().bind(stackPane.heightProperty());
        imageView.setImage(image);
    }

    private void setImageFromFileInImageView(File file) throws MalformedURLException, FileNotFoundException {
            if (file != null) {
                String imageFilePath = file.toURI().toURL().toString();
                image = new Image(imageFilePath);
                setImageHightWidthFitBorderPane(image);
                
                enableOperationButtons();
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

    public void openGammaWindow(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GAMMA_VIEW_PATH));
            Parent root = loader.load();

            //set start image in Gamma View as copy of loaded image
            //by GammaController before showing window
            GammaController gammaController = loader.getController();
            gammaController.setImage(image);
            gammaController.setStartImageInImageView(image);

            createAndShowNewStage(GAMMA_STAGE_TITLE, new Scene(root));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAndShowNewStage(String stageTitle, Scene scene) {
        Stage stageGamma = new Stage();
        stageGamma.setTitle(stageTitle);
        stageGamma.setScene(scene);
        stageGamma.setResizable(false);
        stageGamma.show();
    }


    public void openNegativeWindow(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(NEGATIVE_VIEW_PATH));
            Parent root = loader.load();

            NegativeController negativeController = loader.getController();
            negativeController.setImage(image);
            negativeController.setStartImageInImageView(image);

            createAndShowNewStage(NEGATIVE_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
