import java.awt.Color;


public class FishEyeFilter extends Filter
{
    // constants:
    private final static int SCALE = 20;   // this defines the strenght of the filter
    private final static double TWO_PI = 2 * Math.PI;


    public FishEyeFilter(String name)
    {
        super(name);
    }


    public void apply(OFImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        OFImage original = new OFImage(image);

        int[] xa = computeXArray(width);
        int[] ya = computeYArray(height);
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                image.setPixel(x, y, original.getPixel(x + xa[x], y + ya[y]));
            }
        }
    }


    private int[] computeXArray(int width)
    {
        int[] xArray = new int[width];
        
        for(int i=0; i < width; i++) {
            xArray[i] = (int)(Math.sin( ((double)i / width) * TWO_PI) * SCALE);
        }
        return xArray;
    }


    private int[] computeYArray(int height)
    {
        int[] yArray = new int[height];
        
        for(int i=0; i < height; i++) {
            yArray[i] = (int)(Math.sin( ((double)i / height) * TWO_PI) * SCALE);
        }
        return yArray;
    }
}
