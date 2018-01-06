package portablemap.operators;


import java.awt.image.BufferedImage;

public class Binarization extends Operator{

    private int rshift=0,gshift=0,bshift=0;

    public Binarization(){}

    public Binarization(int threshold){
        rshift+=64-(threshold*128)/100;
        gshift+=64-(threshold*128)/100;
        bshift+=64-(threshold*128)/100;
    }

    public BufferedImage filter(BufferedImage image){
        switch(image.getType()){
            case BufferedImage.TYPE_BYTE_BINARY: return image;
            case BufferedImage.TYPE_BYTE_GRAY:   return filterGray(image);
            default:                             return filterRGB(image);
        }
    }

    public BufferedImage filterGray(BufferedImage image){
        int c;
        int w=image.getWidth();
        int h=image.getHeight();

        Histogram hs=new Histogram(256);

        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                c=image.getRGB(x,y)&0x000000FF;
                hs.write(c);
            }
        }
        int t=hs.getThreshold();

        System.out.println("Threshold=["+t+"]");

        BufferedImage dest=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);

        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                c=image.getRGB(x,y)&0x000000FF;
                if(c>t){
                    dest.setRGB(x,y,-1);
                }else{
                    dest.setRGB(x,y,0);
                }
            }
        }
        return dest;
    }

    public BufferedImage filterRGB(BufferedImage image){
        int c;
        int w=image.getWidth();
        int h=image.getHeight();

        Histogram rhs=new Histogram(256);
        Histogram ghs=new Histogram(256);
        Histogram bhs=new Histogram(256);

        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                c=image.getRGB(x,y);
                rhs.write((c>>16)&0x000000FF);
                ghs.write((c>> 8)&0x000000FF);
                bhs.write( c     &0x000000FF);
            }
        }
        int rt=rhs.getThreshold()+rshift;
        int gt=ghs.getThreshold()+gshift;
        int bt=bhs.getThreshold()+bshift;

        System.out.println("Threshold[r,g,b]=["+rt+","+gt+","+bt+"]");

        BufferedImage dest=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);

        int r,g,b;
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                c=image.getRGB(x,y);
                r=(c>>16)&0x000000FF;
                g=(c>> 8)&0x000000FF;
                b= c     &0x000000FF;

                if((r>rt)||(g>gt)||(b>bt)){
                    dest.setRGB(x,y,-1);
                }else{
                    dest.setRGB(x,y,0);
                }
            }
        }
        return dest;
    }
}