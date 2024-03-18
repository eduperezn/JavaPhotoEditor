/**
 * Filter es una superclase abstracta para todos los filtros de imagen en este
  * solicitud. Los filtros se pueden aplicar a OFImages invocando la aplicación
  * método.
**/
public abstract class Filter
{
    private String name;

    /**
     * Crea un nuevo filtro con un nombre dado.
     * @param nombre El nombre del filtro.
     */
    public Filter(String name)
    {
        this.name = name;
    }
    
    /**
     * Devuelve el nombre de este filtro.
     *
     * @return El nombre de este filtro.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Aplica este filtro a una imagen.
     *
     * @param image La imagen que cambiará este filtro.
     */
    public abstract void apply(OFImage image);
}
