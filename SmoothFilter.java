import java.awt.Color;
import java.util.List;
import java.util.ArrayList;


public class SmoothFilter extends Filter
{
    private OFImage original;
    private int width;
    private int height;
    

    public SmoothFilter(String name)
    {
        super(name);
    }


    public void apply(OFImage image)
    {
        original = new OFImage(image);
        width = original.getWidth();
        height = original.getHeight();
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                image.setPixel(x, y, smooth(x, y));
            }
        }
    }
    
    private Color smooth(int xpos, int ypos)
    {
        List<Color> pixels = new ArrayList<Color>(9);
        
        for(int y = ypos - 1; y <= ypos + 1; y++) {
            for(int x = xpos - 1; x <= xpos + 1; x++) {
                if( x >= 0 && x < width && y >= 0 && y < height )
                    pixels.add(original.getPixel(x, y));
            }
        }

        return new Color(avgRed(pixels), avgGreen(pixels), avgBlue(pixels));
    }

    private int avgRed(List<Color> pixels)
    {
        int total = 0;
        for(Color color : pixels) {
            total += color.getRed();
        }
        return total / pixels.size();
    }


    private int avgGreen(List<Color> pixels)
    {
        int total = 0;
        for(Color color : pixels) {
            total += color.getGreen();
        }
        return total / pixels.size();
    }


    private int avgBlue(List<Color> pixels)
    {
        int total = 0;
        for(Color color : pixels) {
            total += color.getBlue();
        }
        return total / pixels.size();
    }
}
