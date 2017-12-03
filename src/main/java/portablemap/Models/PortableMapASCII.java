package portablemap.Models;


public abstract class PortableMapASCII extends PortableMap {
    private HeadingPmASCII headingPmASCII;
    private byte[][] image;

    public PortableMapASCII(int width, int height, HeadingPmASCII headingPM_ascii){
        super(width, height);
        image = new byte[height][width];
        this.headingPmASCII = headingPM_ascii;
    }

    public void setPixelInImage(int row, int column, byte value){
        this.image[row][column] = value;
    }

    public void setImageSize(int width, int height){
        image = new byte[height][width];
    }

    public byte[][] getImage() {
        return image;
    }

    public void setImage(byte[][] image) {
        this.image = image;
    }

    public HeadingPmASCII getHeadingPmASCII() {
        return headingPmASCII;
    }

    public void setHeadingPmASCII(HeadingPmASCII headingPmASCII) {
        this.headingPmASCII = headingPmASCII;
    }

    @Override
    public void printImageToConsole() {
        System.out.println(headingPmASCII + " " + "width: " + getWidth() + " height:" + getHeight());
        for (byte[] row : image) {
            for (byte column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
    }

    public PortableMapASCII() {
    }
}
