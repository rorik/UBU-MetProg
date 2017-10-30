package juego.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import juego.control.ConfiguracionAtariGo;
import juego.modelo.Celda;
import juego.modelo.Tablero;

/**
 * Jugada introducida por el usuario. Agradecimientos a César I. García Osorio
 * por la revisión del uso de expresiones regulares y a Carlos Pardo por la
 * simplificación del código en el tratamiento de números y letras.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:dcaubilla@ubu.es">David Caubilla</a>
 * @version 1.0 20171009
 */
public class ConversorJugada {

	/** Logger. */
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConversorJugada.class);

	/** Posición de la letra especial I. */
	private static int POSICION_I = 8;

	/** Oculta el constructor público. */
	private ConversorJugada() {
		throw new IllegalStateException("Utility singleton class");
		// se verá en posteriores temas el lanzamiento de excepciones
	}

	/**
	 * Valida la corrección de la jugada.
	 * 
	 * @param textoJugada
	 *            texto a validar
	 * @param tablero
	 *            tablero con las figuras colocadas
	 * @return celda si existe la correspondencia o null en caso contrario
	 */
	public static Celda convertir(String textoJugada, Tablero tablero) {
		Celda celda = null;
		String textoJugadaTransformada = textoJugada.toUpperCase();
		// Suponemos que el tablero es siempre cuadrado...
		assert tablero.obtenerNumeroFilas() == tablero.obtenerNumeroColumnas() : "Tablero cuadrado";
		if (validarSintaxis(textoJugadaTransformada, tablero.obtenerNumeroFilas())) {
			celda = traducirACelda(textoJugadaTransformada, tablero);
		}
		return celda;
	}

	/**
	 * Valida la sintaxis de la jugada en función del tamaño del tablero.
	 * 
	 * @param textoJugada
	 *            texto
	 * @param tamaño
	 *            tamaño
	 * @return true si es válida y false en caso contrario
	 */
	public static boolean validarSintaxis(String textoJugada, int tamaño) {
		String textoJugadaTransformada = textoJugada.toUpperCase();
		final char LIM_CAR_SUP = calcularLetraSuperior('A', tamaño);

		// Patrón de texto a utilizar. 
		final String primeraParteDelPatron;
		if (tamaño <= 9) {
			primeraParteDelPatron = "^[1-" + tamaño + "]";
		} else {
			primeraParteDelPatron = "^([1-9]|(1[0-" + (tamaño % 10) + "]))";
		}		
		String patronCompleto = primeraParteDelPatron + "[A-" + LIM_CAR_SUP + "&&[^"
				+ ConfiguracionAtariGo.LETRA_NO_UTILIZADA + "]]$";
		
		final Pattern patrón = Pattern.compile(patronCompleto);
		Matcher matcher = patrón.matcher(textoJugadaTransformada);
		return matcher.matches();
	}

	/**
	 * Calcula el valor límite a introducir en letras.
	 * 
	 * @param letra
	 *            inicio
	 * @param tamaño
	 *            tamaño
	 * @return límite superior
	 */
	private static char calcularLetraSuperior(char letra, int tamaño) {
		// Suponemos que el tamaño mínimo siempre contiene la I (simplificación)
		return (char) (letra + tamaño);
	}

	/**
	 * Convierte una coordenadas en texto a su correspondiente celda.
	 * 
	 * @param coordenadas
	 *            coordenadas en texto Ej: 0a
	 * @param tablero
	 *            tablero
	 * @return celda correspondiente a las coordenadas
	 */
	private static Celda traducirACelda(String coordenadas, Tablero tablero) {
		int ultimaPosicionEnCoordenadas = coordenadas.length() - 1;
		int numeroFilaEnPantalla = Integer.parseInt(coordenadas.substring(0, ultimaPosicionEnCoordenadas));
		int x = Math.abs(numeroFilaEnPantalla - tablero.obtenerNumeroFilas());

		char letra = coordenadas.charAt(ultimaPosicionEnCoordenadas);
		int y = obtenerPosicionParaLetra(letra);

		logger.debug("Coordenadas obtenidas: " + x + "/" + y);
		return tablero.obtenerCelda(x, y);
	}

	/**
	 * Traduce la letra a la posición empezando desde cero, teniendo en cuenta que
	 * la letra i no existe en el tablero.
	 * 
	 * @param letra
	 *            letra
	 * @return fila correspondiente en el tablero
	 */
	private static int obtenerPosicionParaLetra(char letra) {
		return (letra < 'J') ? (letra - 'A') : (letra - 'A' - 1);
	}

	/**
	 * Convierte una celda al texto correspondiente a introducir por un usuario.
	 * 
	 * @param celda
	 *            celda
	 * @param tablero
	 *            tablero
	 * @return texto asociado
	 */
	public static String traducirCelda(Celda celda, Tablero tablero) {
		int fila = celda.obtenerFila();
		int columna = celda.obtenerColumna();
		String textoFila = traducirFila(fila, tablero.obtenerNumeroFilas());
		String textoColumna = traducirColumna(columna);
		return textoFila + textoColumna;
	}

	/**
	 * Calcula el número correspondiente a la fila.
	 * 
	 * @param fila
	 *            fila
	 * @param tamañoEnFilas
	 *            tamaño en filas
	 * @return numero correspondiente a esa fila en el tablero
	 */
	private static String traducirFila(int fila, int tamañoEnFilas) {
		return Integer.toString(tamañoEnFilas - fila);
	}

	/**
	 * Calcula la letra correspondiente a la columna.
	 * 
	 * @param columna
	 *            columna 
	 * @return letra correspondiente a esa columna en el tablero
	 */
	private static String traducirColumna(int columna) {
		int columnaRecalculada = columna < POSICION_I ? columna : columna + 1;
		char letra = (char) ('A' + columnaRecalculada);
		return Character.toString(letra);
	}
} 
