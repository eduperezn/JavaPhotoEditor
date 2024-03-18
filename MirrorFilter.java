import java.awt.Color;


public class MirrorFilter extends Filter
{

    public MirrorFilter(String name)
    {
        super(name);
    }


    public void apply(OFImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width / 2; x++) {
                Color left = image.getPixel(x, y);
                image.setPixel(x, y, image.getPixel(width - 1 - x, y));
                image.setPixel(width - 1 - x, y, left);
            }
        }
    }
}
