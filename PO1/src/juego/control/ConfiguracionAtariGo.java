package juego.control;

import java.util.Collection;
import java.util.stream.IntStream;

/**
* Configuración del juego.
*
* @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
* @version 1.0
*/
public class ConfiguracionAtariGo {
	public static int TAMAÑO_POR_DEFECTO = 9;
	private static int[] TAMANAÑOS = {9, 13, 19};
	public static char LETRA_NO_UTILIZADA = '|';

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
		return IntStream.of(TAMANAÑOS).reduce(Integer.MIN_VALUE, Integer::max);
	}

    /**
     * Comprueba que un tamaño esté entre los permitidos.
     *
     * @param tamañoSugerido Tamaño a ser comprobado.
     * @return <code>true</code> si es válido, si no, <code>false</code>.
     */
	public static boolean esTamañoValido(int tamañoSugerido) {
		return IntStream.of(TAMANAÑOS).anyMatch(x -> x == tamañoSugerido);
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
		return ayuda.substring(0, ayuda.length()-1).concat(".");
	}
}
