package juego.control;

/**
 * Configuración del juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.0
 */
public class ConfiguracionAtariGo {
    /**
     * Tamaño usado cuando no se le da ninguno.
     */
    public static final int TAMAÑO_POR_DEFECTO = 9;

    /**
     * Tamaños permitidos del tablero.
     */
    private static final int[] TAMANAÑOS = {9, 13, 19};

    /**
     * Letra que no se debe mostrar en pantalla.
     */
    public static final char LETRA_NO_UTILIZADA = 'I';

    /**
     * Cantidad mínima de capturas que se deben producir.
     */
    public static final int MINIMO_CAPTURAS = 2;

    /**
     * Cantidad máxima de capturas que se pueden producir.
     */
    public static final int MAXIMO_CAPTURAS = 10;

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
        for (int tamaño: TAMANAÑOS) {
            if (tamaño > maximo) {
                maximo = tamaño;
            }
        }
        return maximo;
    }

    /**
     * Comprueba que un tamaño esté entre los permitidos.
     *
     * @param tamañoSugerido Tamaño a ser comprobado.
     * @return <code>true</code> si es válido, si no, <code>false</code>.
     */
    public static boolean esTamañoValido(int tamañoSugerido) {
        for (int tamaño: TAMANAÑOS) {
            if (tamaño == tamañoSugerido) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera un String con ayuda sobre la configuración.
     *
     * @return String de ayuda.
     */
    public static String generarAyuda() {
        String ayuda = "";
        for (int tamaño : TAMANAÑOS) {
            ayuda = ayuda.concat(" " + tamaño + ',');
        }
        return ayuda.substring(0, ayuda.length() - 1).concat(".");
    }
}
