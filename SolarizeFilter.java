import java.awt.Color;


public class SolarizeFilter extends Filter
{

    public SolarizeFilter(String name)
    {
        super(name);
    }


    public void apply(OFImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Color pix = image.getPixel(x, y);
                int red = pix.getRed();
                if(red <= 127) {
                    red = 255 - red;
                }
                int green = pix.getGreen();
                if(green <= 127) {
                    green = 255 - green;
                }
                int blue = pix.getBlue();
                if(blue <= 127) {
                    blue = 255 - blue;
                }
                image.setPixel(x, y, new Color(red, green, blue));
            }
        }
    }

}
