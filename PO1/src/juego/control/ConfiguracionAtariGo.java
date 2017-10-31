package juego.control;

/**
 * Configuración del juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class ConfiguracionAtariGo {
    public static final int TAMAÑO_POR_DEFECTO = 9;
    private static final int[] TAMANAÑOS = {9, 13, 19};
    public static final char LETRA_NO_UTILIZADA = '|';

    /**
     * Constructor privado vacío de clase estática.
     */
    private ConfiguracionAtariGo() {

    }

    /**
     * Obtiene el valor máximos de los tamaños.
     *
     * @return Tamaño máximo.
     */
    public static int obtenerTamañoMaximo() {
        int maximo = 0;
        for (int tamaño: TAMANAÑOS)
            if (tamaño > maximo)
                maximo = tamaño;
        return maximo;
    }

    /**
     * Comprueba que un tamaño esté entre los permitidos.
     *
     * @param tamañoSugerido Tamaño a ser comprobado.
     * @return <code>true</code> si es válido, si no, <code>false</code>.
     */
    public static boolean esTamañoValido(int tamañoSugerido) {
        for (int tamaño: TAMANAÑOS)
            if (tamaño == tamañoSugerido)
                return true;
        return false;
    }

    /**
     * Genera un String con ayuda sobre la configuración.
     *
     * @return String de ayuda.
     */
    public static String generarAyuda() {
        String ayuda = "";
        for (int tamaño : TAMANAÑOS)
            ayuda = ayuda.concat(" " + tamaño + ',');
        return ayuda.substring(0, ayuda.length() - 1).concat(".");
    }
}
