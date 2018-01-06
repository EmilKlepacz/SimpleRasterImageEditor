package fx.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import portablemap.readers.PPMImageReader;
import portablemap.readers.PPMImageReaderSpi;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;


public class OpenFileController {

    private static final String GAMMA_VIEW_PATH = "/views/gamma_view.fxml";
    private static final String GAMMA_STAGE_TITLE = "Gamma correction";
    private static final String NEGATIVE_VIEW_PATH = "/views/negative_view.fxml";
    private static final String NEGATIVE_STAGE_TITLE = "Negative operation";
    private static final String FILTERING_VIEW_PATH = "/views/filtering_view.fxml";
    private static final String FILTERING_STAGE_TITLE = "Filtering";
    private static final String HISTOGRAM_VIEW_PATH = "/views/histogram_view.fxml";
    private static final String HISTOGRAM_STAGE_TITLE = "Histogram based operations";
    private static final String GEOMETRIC_VIEW_PATH = "/views/geometric_view.fxml";
    private static final String GEOMETRIC_STAGE_TITLE = "Geometric transformations";
    private static final String URL_STAGE_TITLE = "Loading Image from URL";
    private static final String URL_VIEW_PATH = "/views/url_view.fxml";
    private static final int MIN_SPINNER_WIDTH_VAL = 1;
    private static final int MAX_SPINNER_WIDTH_VAL = 100000;
    private static final int ON_START_SPINNER_WIDTH_VAL = 0;
    private static final int MIN_SPINNER_HEIGHT_VAL = 1;
    private static final int MAX_SPINNER_HEIGHT_VAL = 100000;
    private static final int ON_START_SPINNER_HEIGHT_VAL = 0;

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
    private String imagePath;

    private void setImagePath(String filePath){

        imagePath = filePath;
    }


    private void enableOperationButtons() {
        gammaBtn.setDisable(false);
        negativeBtn.setDisable(false);
        filteringBtn.setDisable(false);
        histogramBtn.setDisable(false);
        geometricBtn.setDisable(false);
    }

    private void setImageHeightWidthFitBorderPane(Image image) {
        imageView.fitHeightProperty().bind(stackPane.widthProperty());
        imageView.fitWidthProperty().bind(stackPane.heightProperty());
        imageView.setImage(image);
    }

    //method return image Path of image in ImageView
    private void setImageFromFileInImageView(File file) throws MalformedURLException, FileNotFoundException {
        if (file != null) {
            String imageFilePath = file.toURI().toURL().toString();

            image = new Image(imageFilePath);
            setImageHeightWidthFitBorderPane(image);

            enableOperationButtons();
        } else throw new FileNotFoundException();

    }

    private void openFileChooserAndSetImage() throws MalformedURLException, FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(getClass().getClassLoader().getResource("images").getPath()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("formats (*.pbm, *.pgm, *.ppm, *.png, *.jpeg)", "*.pbm", "*.pgm", "*.ppm", "*.png", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            readAndDisplayMetadata(selectedFile.getPath());
            setImageFromFileInImageView(selectedFile);
            setImagePath(selectedFile.getAbsolutePath());
        }
    }

    public void handleOpenFromFile() {
        try {
            openFileChooserAndSetImage();
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleOpenFromURL(String url) throws IOException {
        File f = new File("temp");
        String chosenURL = url;
        BufferedImage img = ImageIO.read(new URL(chosenURL));
        String extension = chosenURL.substring(chosenURL.lastIndexOf(".") + 1).trim();
        ImageIO.write(img, extension, f);
        setImageFromFileInImageView(f);
    }

    public void handleOpenFileInformation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File statistics");
        alert.setHeaderText("FILE NAME HERE");
        alert.setContentText("There will be file info");

        alert.showAndWait();
    }


    public void openGammaWindow() {
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

    public void openNegativeWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(NEGATIVE_VIEW_PATH));
            Parent root = loader.load();

            NegativeController negativeController = loader.getController();
            negativeController.setImage(image);
            negativeController.setImagePath(imagePath);
            negativeController.setStartImageInImageView(image);

            createAndShowNewStage(NEGATIVE_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFilteringWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FILTERING_VIEW_PATH));
            Parent root = loader.load();

            FilteringController filteringController = loader.getController();
            filteringController.setImage(image);
            filteringController.setStartImageInImageView(image);

            createAndShowNewStage(FILTERING_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openHistogramWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(HISTOGRAM_VIEW_PATH));
            Parent root = loader.load();

            HistogramController histogramController = loader.getController();
            histogramController.setImage(image);
            histogramController.setStartImageInImageView(image);

            createAndShowNewStage(HISTOGRAM_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGeometricWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GEOMETRIC_VIEW_PATH));
            Parent root = loader.load();

            GeometricController geometricController = loader.getController();
            geometricController.setImage(image);
            geometricController.setStartImageInImageView(image);

            geometricController.setWidthSpinnerValue(MIN_SPINNER_WIDTH_VAL, MAX_SPINNER_WIDTH_VAL, ON_START_SPINNER_WIDTH_VAL);
            geometricController.setHeightSpinnerValue(MIN_SPINNER_HEIGHT_VAL, MAX_SPINNER_HEIGHT_VAL, ON_START_SPINNER_HEIGHT_VAL);

            createAndShowNewStage(GEOMETRIC_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUrlWindow() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load from url");
        dialog.setHeaderText("Look, put your url to file below");
        dialog.setContentText("Please enter file url:");

        Image image = new Image(getClass().getResource("/images/url.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        dialog.setGraphic(imageView);

        dialog.setGraphic(imageView);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                handleOpenFromURL(dialog.getEditor().getCharacters().toString());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("WRONG URL WAS USED!");
                alert.showAndWait();
                openUrlWindow();
            }
        }

    }

    private void createAndShowNewStage(String stageTitle, Scene scene) {
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void readAndDisplayMetadata(String fileName) {

        if (fileName != null) {
            try {

                File file = new File(fileName);
                double bytes = file.length();
                System.out.println("File Size: " + String.format("%.2f", bytes / 1024) + "kb");
                PPMImageReaderSpi pnmReaderSpi;
                PPMImageReader pnmReader;
                if(fileName.contains("pgm")||fileName.contains("pbm")||fileName.contains("ppm")) {
                    pnmReaderSpi = new PPMImageReaderSpi();
                    pnmReader = (PPMImageReader) pnmReaderSpi.createReaderInstance();
                }
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