package portablemap.Models;


public enum HeadingPmBinary {
    P4("Portable bitmap"), P5("Portable graymap"), P6("Portable pixmap");

    private final String text;

    HeadingPmBinary(final String text) {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
