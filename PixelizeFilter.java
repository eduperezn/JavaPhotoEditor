import java.awt.Color;


public class PixelizeFilter extends Filter
{

    public PixelizeFilter(String name)
    {
        super(name);
    }


    public void apply(OFImage image)
    {
        final int PIXEL_SIZE = 5;
        int width = image.getWidth();
        int height = image.getHeight();
        
        for(int y = 0; y < height; y += PIXEL_SIZE) {
            for(int x = 0; x < width; x += PIXEL_SIZE) {
                Color pix = image.getPixel(x, y);
                for(int dy = y; dy < y + PIXEL_SIZE; dy++) {
                    for(int dx = x; dx < x + PIXEL_SIZE; dx++) {
                        if( dx < width && dy < height )
                            image.setPixel(dx, dy, pix);
                    }
                }
            }
        }
    }
}
