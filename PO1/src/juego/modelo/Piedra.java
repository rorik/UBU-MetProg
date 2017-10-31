package juego.modelo;

/**
 * Unidad de juego, se colocan en el tablero en cada celda.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class Piedra {
    private Color color;
    private Celda celda;

    /**
     * Constructor de Piedra.
     *
     * @param color: Color de la piedra.
     */
    public Piedra(Color color) {
        assert color != null;
        establecerColor(color);
    }

    /**
     * Obtiene el color de la piedra.
     *
     * @return Color.
     */
    public Color obtenerColor() {
        return this.color;
    }

    /**
     * Cambia el color de la piedra.
     *
     * @param color: Color al cual establecer la piedra.
     */
    private void establecerColor(Color color) {
        this.color = color;
    }

    /**
     * Establece la celda en la que se encuentre la piedra.
     *
     * @param celda: Celda a ser asignada.
     */
    public void colocarEn(Celda celda) {
        this.celda = celda;
    }

    /**
     * Obtiene la celda en la que se encuentra.
     *
     * @return Celda.
     */
    public Celda obtenerCelda() {
        return celda;
    }

    /**
     * Información del objeto en String.
     *
     * @return String del objeto.
     */
    public String toString() {
        return (obtenerCelda() == null ? "null" : obtenerCelda().toString()) + "/" + obtenerColor();
    }
}
