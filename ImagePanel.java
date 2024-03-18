import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * Un ImagePanel es un componente Swing que puede mostrar una OFImage.
 * Se contruye como una clase JComponent
 */
public class ImagePanel extends JComponent
{
    // Ancho y altura del panel
    private int width, height;

    // Un búfer de imagen interno que se utiliza para pintar. Para 
    // la visualización real, este búfer de imagen se copia en la pantalla.
    private OFImage panelImage;

    /**
     * Crea un ImagePanel, nuevo y vacio.
     */
    public ImagePanel()
    {
        width = 360;    // tamaño del vacio
        height = 240;
        panelImage = null;
    }

    /**
     * La imagen que debe mostrar el panel.
     * 
     * @param image  Es la imagen que se mostrará.
     */
    public void setImage(OFImage image)
    {
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            panelImage = image;
            repaint();
        }
    }
    
    /**
     * Borra la imagen del panel.
     */
    public void clearImage()
    {
        Graphics imageGraphics = panelImage.getGraphics();
        imageGraphics.setColor(Color.LIGHT_GRAY);
        imageGraphics.fillRect(0, 0, width, height);
        repaint();
    }
    
    // The following methods are redefinitions of methods
    // inherited from superclasses.
    
    /**
     * (This method gets called by layout managers for placing
     * the components.)
     * 
     * @return La dimensión preferida para este componente.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }
    
    /**
     * Copia la imagen interna
     * Llamamos este metodo con Swing
     */
    public void paintComponent(Graphics g)
    {
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(panelImage != null) {
            g.drawImage(panelImage, 0, 0, null);
        }
    }
}
