package juego.modelo;

/**
 * Colores de jugadores y piedras.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
 */
public enum Color {
    /**
     * Color negro con representación X.
     */
    NEGRO('X'),
    /**
     * Color blanco con representación O.
     */
    BLANCO('O');

    private final char caracter;

    /**
     * Constructor del color.
     *
     * @param caracter Carácter del color.
     */
    Color(char caracter) {
        this.caracter = caracter;
    }

    /**
     * Obtiene el carácter asignado al color.
     *
     * @return Carácter.
     */
    public char toChar() {
        return this.caracter;
    }
}
