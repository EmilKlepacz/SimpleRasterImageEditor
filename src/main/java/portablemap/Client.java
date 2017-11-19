package portablemap;


import java.io.IOException;

public class Client {
    public static void main(String[] args) {

        PbmAscii pbmAscii = null;
        try {
            pbmAscii = (PbmAscii) FileUtils.readFromFile("test2P1.pbm");
        } catch (IOException e) {
            e.printStackTrace();
        }
        pbmAscii.printImageToConsole();

    }
}
