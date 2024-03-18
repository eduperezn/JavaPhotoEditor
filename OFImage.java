import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * OFImage es una clase que define una imagen en formato OF (Objects First).
 */
public class OFImage extends BufferedImage
{
    /**
     * Crea una OFImage copiada de una BufferedImage.
      * @param image La imagen a copiar.
     */
    public OFImage(BufferedImage image)
    {
         super(image.getColorModel(), image.copyData(null), 
               image.isAlphaPremultiplied(), null);
    }

    /**
     * Crea una OFImage con tamaño especificado y contenido no especificado.
      * @param ancho El ancho de la imagen.
      * @param height La altura de la imagen.
     */
    public OFImage(int width, int height)
    {
        super(width, height, TYPE_INT_RGB);
    }

    /**
     * Establece un píxel dado de esta imagen en un color específico. los
      * el color se representa como un valor (r,g,b).
      * @param x La posición x del píxel.
      * @param y La posición y del píxel.
      * @param col El color del píxel.
     */
    public void setPixel(int x, int y, Color col)
    {
        int pixel = col.getRGB();
        setRGB(x, y, pixel);
    }
    
    /**
     * Obtiene el valor de color en una posición de píxel especificada.
      * @param x La posición x del píxel.
      * @param y La posición y del píxel.
      * @return El color del píxel en la posición dada.
     */
    public Color getPixel(int x, int y)
    {
        int pixel = getRGB(x, y);
        return new Color(pixel);
    }
}
