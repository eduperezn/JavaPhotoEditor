import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ImageViewer
{
    // campos estáticos:
    private static final String VERSION = "Version final";
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    // campos:
    private JFrame frame;
    private ImagePanel imagePanel;
    private JLabel filenameLabel;
    private JLabel statusLabel;
    private JButton smallerButton;
    private JButton largerButton;
    private OFImage currentImage;
    
    private List<Filter> filters;
    
    /**
     * Crea un ImageViewer y muestre su GUI en la pantalla.
     */
    public ImageViewer()
    {
        currentImage = null;
        filters = createFilters();
        makeFrame();
    }


    // ---- Menu de opciones ----
    
    /**
     * Abre la función: Abre el selector de archivos
     * muestra la imagen seleccionada.
     */
    private void openFile()
    {
        int returnVal = fileChooser.showOpenDialog(frame);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // Cancelar
        }
        File selectedFile = fileChooser.getSelectedFile();
        currentImage = ImageFileManager.loadImage(selectedFile);
        
        if(currentImage == null) {   // Si el archivo no es una imagen
            JOptionPane.showMessageDialog(frame,
                    "El archivo no estaba en un formato de archivo de imagen reconocido.",
                    "Error al cargar la imagen",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        imagePanel.setImage(currentImage);
        setButtonsEnabled(true);
        showFilename(selectedFile.getPath());
        showStatus("Imagen cargada");
        frame.pack();
    }

    /**
     * Cierra la función: cierra la imagen actual.
     */
    private void close()
    {
        currentImage = null;
        imagePanel.clearImage();
        showFilename(null);
        setButtonsEnabled(false);
    }

    /**
     * Opcion "Guardar como": guarda la imagen.
     */
    private void saveAs()
    {
        if(currentImage != null) {
            int returnVal = fileChooser.showSaveDialog(frame);
    
            if(returnVal != JFileChooser.APPROVE_OPTION) {
                return;  // cancelled
            }
            File selectedFile = fileChooser.getSelectedFile();
            ImageFileManager.saveImage(currentImage, selectedFile);
            
            showFilename(selectedFile.getPath());
        }
    }

    /**
     * Quit: Salir de la aplicacion
     */
    private void quit()
    {
        System.exit(0);
    }

    /**
     * Aplicar un filtro dado a la imagen actual.
     * 
     * @param filter El objeto de filtro que se va a aplicar.
     */
    private void applyFilter(Filter filter)
    {
        if(currentImage != null) {
            filter.apply(currentImage);
            frame.repaint();
            showStatus("Aplicado: " + filter.getName());
        }
        else {
            showStatus("No se cargó la imagen");
        }
    }

    /**
     * función 'About': muestra un recuadro 'acerca de'.
     */
    private void showAbout()
    {
        JOptionPane.showMessageDialog(frame, 
                    "Editor de imagenes V1.0\n" + VERSION,
                    "Acerca de", 
                    JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Hacer mas grande la imagen.
     */
    private void makeLarger()
    {
        if(currentImage != null) {
            // crea nueva imagen con el doble de su tamaño
            int width = currentImage.getWidth();
            int height = currentImage.getHeight();
            OFImage newImage = new OFImage(width * 2, height * 2);

            // copia los datos de los pixeles en una nueva imagen
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    Color col = currentImage.getPixel(x, y);
                    newImage.setPixel(x * 2, y * 2, col);
                    newImage.setPixel(x * 2 + 1, y * 2, col);
                    newImage.setPixel(x * 2, y * 2 + 1, col);
                    newImage.setPixel(x * 2+1, y * 2 + 1, col);
                }
            }
            
            currentImage = newImage;
            imagePanel.setImage(currentImage);
            frame.pack();
        }
    }
    

    /**
     * Reducir el tamaño de la imagen actual.
     */
    private void makeSmaller()
    {
        if(currentImage != null) {
            // crea una imagen con el doble de tamaño reducido
            int width = currentImage.getWidth() / 2;
            int height = currentImage.getHeight() / 2;
            OFImage newImage = new OFImage(width, height);

            // copia los datos de los pixeles en una nueva imagen
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    newImage.setPixel(x, y, currentImage.getPixel(x * 2, y * 2));
                }
            }
            
            currentImage = newImage;
            imagePanel.setImage(currentImage);
            frame.pack();
        }
    }
    
    // ---- métodos de apoyo ----

    /**
     *  Muestra el nombre de la imagen en la etiqueta de visualización de archivos.
     *  'null' se usa si no hay ningún archivo cargado
     * 
     * @param filename El nombre del archivo que se mostrará, o será nulo si no hay archivo
     */
    private void showFilename(String filename)
    {
        if(filename == null) {
            filenameLabel.setText("No hay ningún archivo");
        }
        else {
            filenameLabel.setText("Archivo: " + filename);
        }
    }
    
    
    /**
     * Muestra un mensaje en la barra de estado en la parte inferior de la pantalla.
     * @param text es el mensaje de estado
     */
    private void showStatus(String text)
    {
        statusLabel.setText(text);
    }
    
    
    /**
     * Habilita o desabilita los botones de la barra de herramientas.
     * 
     * @param status  'true' habilita los botones, 'false' los deshabilita.
     */
    private void setButtonsEnabled(boolean status)
    {
        smallerButton.setEnabled(status);
        largerButton.setEnabled(status);
    }
    
    
    /**
     * Crea la lista con todos los filtros disponibles.
     * @return es la lista de los filtros.
     */
    private List<Filter> createFilters()
    {
        List<Filter> filterList = new ArrayList<Filter>();
        filterList.add(new DarkerFilter("Más oscuro"));
        filterList.add(new LighterFilter("Más claro"));
        filterList.add(new ThresholdFilter("Threshold"));
        filterList.add(new InvertFilter("Colores invertidos"));
        filterList.add(new SolarizeFilter("Solarize"));
        filterList.add(new SmoothFilter("Suavizar"));
        filterList.add(new PixelizeFilter("Pixelizar"));
        filterList.add(new MirrorFilter("Mirror"));
        filterList.add(new GrayScaleFilter("Escala de grises"));
        filterList.add(new EdgeFilter("Deteción de bordes"));
        filterList.add(new FishEyeFilter("Fish Eye"));
       
        return filterList;
    }
    
    // ---- Se contruye la ventana y los menus ----
    
    /**
     * Crea el marco y el contenido con Swing
     */
    private void makeFrame()
    {
        frame = new JFrame("Editor de imagenes");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));

        makeMenuBar(frame);
        
        // Se especifican los bordes
        contentPane.setLayout(new BorderLayout(6, 6));
        
        // Se crea el panel del contenido de las imagenes mesa de trabajo
        imagePanel = new ImagePanel();
        imagePanel.setBorder(new EtchedBorder());
        contentPane.add(imagePanel, BorderLayout.CENTER);

        // Se crean las etiquetas para el nombre de archivo y mensajes de estado
        filenameLabel = new JLabel();
        contentPane.add(filenameLabel, BorderLayout.NORTH);

        statusLabel = new JLabel(VERSION);
        contentPane.add(statusLabel, BorderLayout.SOUTH);
        
        // Crea la barra de herramientas con los botones
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));
        
        smallerButton = new JButton("Más pequeña");
        smallerButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { makeSmaller(); }
                           });
        toolbar.add(smallerButton);
        
        largerButton = new JButton("Más grande");
        largerButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { makeLarger(); }
                           });
        toolbar.add(largerButton);

        // Agrega la barra de herramientas
        JPanel flow = new JPanel();
        flow.add(toolbar);
        
        contentPane.add(flow, BorderLayout.WEST);
        
        // Organizamos los componentes  
        showFilename(null);
        setButtonsEnabled(false);
        frame.pack();
        
        // Se coloca la mesa de trabajo en el centro y se muestra
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
    
    /**
     * Crea la bara del menu principal
     * 
     * @param frame El marco al que se debe agregar la barra de tareas.
     */
    private void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("Archivos");
        menubar.add(menu);
        
        item = new JMenuItem("Abrir");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { openFile(); }
                           });
        menu.add(item);

        item = new JMenuItem("Cerrar");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { close(); }
                           });
        menu.add(item);
        menu.addSeparator();

        item = new JMenuItem("Guardar como...");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { saveAs(); }
                           });
        menu.add(item);
        menu.addSeparator();
        
        item = new JMenuItem("Quitar");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);


        // Se crea el menú de los filtros
        menu = new JMenu("Filtros");
        menubar.add(menu);
        
        for(final Filter filter : filters) {
            item = new JMenuItem(filter.getName());
            item.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) { 
                                    applyFilter(filter);
                                }
                           });
             menu.add(item);
         }

        // Se crea el menú de ayuda y acerca de
        menu = new JMenu("Mas");
        menubar.add(menu);
        
        item = new JMenuItem("Acerca de...");
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { showAbout(); }
                           });
        menu.add(item);

    }
}
