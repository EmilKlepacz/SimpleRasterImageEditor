package portablemap.Models;



/* Common attributes for PBM, PGM, PPM, PNM */

public abstract class PortableMap {
    private int width;
    private int height;

    public PortableMap(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public PortableMap() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void printImageToConsole();

    @Override
    public String toString() {
        return String.format("{ width = %d, height = %d }", width, height);
    }
}
