package portablemap.Models;


public enum HeadingPmASCII {
    P1("Portable bitmap"), P2("Portable graymap"), P3("Portable pixmap");

    private final String text;

    HeadingPmASCII(final String text) {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
