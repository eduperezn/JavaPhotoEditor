import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

public class EdgeFilter extends Filter
{
    private static final int TOLERANCE = 20;
    
    private OFImage original;
    private int width;
    private int height;

    /**
     * Constructor de objetos de la clase EdgeFilter.
     * @param nombre El nombre del filtro.
     */
    public EdgeFilter(String name)
    {
        super(name);
    }

    /**
     * Aplica este filtro a una imagen.
     *
     * @param image La imagen que cambiará este filtro.
     */
    public void apply(OFImage image)
    {
        original = new OFImage(image);
        width = original.getWidth();
        height = original.getHeight();
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                image.setPixel(x, y, edge(x, y));
            }
        }
    }

    /**
      * Devuelve un nuevo color que es el color suavizado de un determinado
      * posición. El "color suavizado" es el valor de color que es el
      * promedio de este píxel y todos los píxeles adyacentes.
      * @param xpos La posición x del píxel.
      * @param ypos La posición y del píxel.
      * @return El color suavizado.
     */
    private Color edge(int xpos, int ypos)
    {
        List<Color> pixels = new ArrayList<Color>(9);
        
        for(int y = ypos-1; y <= ypos+1; y++) {
            for(int x = xpos-1; x <= xpos+1; x++) {
                if( x >= 0 && x < width && y >= 0 && y < height ) {
                    pixels.add(original.getPixel(x, y));
                }
            }
        }

        return new Color(255 - diffRed(pixels), 255 - diffGreen(pixels), 255 - diffBlue(pixels));
    }

    /**
     * @param pixels La lista de píxeles a promediar.
     * @return El promedio de todos los valores rojos en la lista dada de píxeles.
     */
    private int diffRed(List<Color> pixels)
    {
        int max = 0;
        int min = 255;
        for(Color color : pixels) {
            int val = color.getRed();
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        int difference = max - min - TOLERANCE;
        if(difference < 0) {
            difference = 0;
        }
        return difference;
    }

    /**
      * @param pixels La lista de píxeles a promediar.
      * @return El promedio de todos los valores verdes en la lista dada de píxeles.
     */
    private int diffGreen(List<Color> pixels)
    {
        int max = 0;
        int min = 255;
        for(Color color : pixels) {
            int val = color.getGreen();
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        int difference = max - min - TOLERANCE;
        if(difference < 0) {
            difference = 0;
        }
        return difference;
    }

    /**
     * @param pixels La lista de píxeles a promediar.
     * @return El promedio de todos los valores azules en la lista dada de píxeles.
     */
    private int diffBlue(List<Color> pixels)
    {
        int max = 0;
        int min = 255;
        for(Color color : pixels) {
            int val = color.getBlue();
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        int difference = max - min - TOLERANCE;
        if(difference < 0) {
            difference = 0;
        }
        return difference;
    }

}
