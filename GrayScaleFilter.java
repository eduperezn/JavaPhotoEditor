import java.awt.Color;


public class GrayScaleFilter extends Filter
{

    public GrayScaleFilter(String name)
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
                int avg = (pix.getRed() + pix.getGreen() + pix.getBlue()) / 3;
                image.setPixel(x, y, new Color(avg, avg, avg));
            }
        }
    }
}
