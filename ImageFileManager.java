import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * En esta clase se usan metodos estaticos para cargar y guardar las imagenes
 * 
 * El formato se define por IMAGE_FORMAT y solo recibe formatos de imagen
 */
public class ImageFileManager
{
    // Los formatos definidos son "jpg" and "png".
    private static final String IMAGE_FORMAT = "jpg";
    
    /**
     * Lee un archivo de imagen en los archivos y lo devuelve como una imagen.
     * Los formatos disponibles son JPG y PNG, si el archivo no es compatible
     * este metodo devuelve 'null'
     * 
     * @param imageFile  Es el archivo que se va a cargar
     * @return           Si no se pudo entonces devuelve 'null'
     */
    public static OFImage loadImage(File imageFile)
    {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            if(image == null || (image.getWidth(null) < 0)) {
                // No se pudo cargar la imagen
                return null;
            }
            return new OFImage(image);
        }
        catch(IOException exc) {
            return null;
        }
    }

    /**
     * Escribe un archivo de imagen en el disco. el formato es JPG
     * @param image Es la imagen que se va a guardar
     * @param file El archivo para guardar
     */
    public static void saveImage(OFImage image, File file)
    {
        try {
            ImageIO.write(image, IMAGE_FORMAT, file);
        }
        catch(IOException exc) {
            return;
        }
    }
}
