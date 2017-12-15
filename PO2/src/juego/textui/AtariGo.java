package juego.textui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import juego.control.Arbitro;
import juego.control.ArbitroAtariGoAvanzado;
import juego.control.ArbitroAtariGoBasico;
import juego.control.ConfiguracionAtariGo;
import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Jugador;
import juego.modelo.Tablero;
import juego.util.ConversorJugada;
import juego.util.CoordenadasIncorrectasException;

/**
 * Juego del AtariGo. Ejercicio práctico - Metodología de la Programación.
 * <P>
 * 2º ITIG - Curso 2017-2018. Interfaz en modo texto del juego.
 * Versión de código incompleto a completar por los alumnos.
 * 
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.0
 * 
 */
public class AtariGo {

	/** Valor indefinido. */
	private static final int INDEFINIDO = -9999;

	/** Logger. */
	private static final Logger logger = LoggerFactory.getLogger(AtariGo.class);

	/** Tamaño. */
	private static int tamaño;

	/** Arbitro. */
	private static Arbitro arbitro;

	/**
	 * Flujo de ejecución principal del juego.
	 * 
	 * @param args
	 *            nombres de los jugadores y tamaño del tablero
	 */
	public static void main(String[] args) {
		if (!sonArgumentosValidos(args)) {
			mostrarAyuda();
			args = new String[]{};
		}
        /*
          La razón de realizar esta comprobación en el main, a pesar de que
          se realice en la función inicializarConArgumentos, es que si no
          se habilitan los asserts (opción por defecto), podemos ejecutar
          el programa con dimensiones y cotas incorrectas.
         */
		inicializarConArgumentos(args);
		while (!arbitro.estaAcabado()) {
			mostrarTablero(arbitro.obtenerTablero());
			mostrarPrompt();
			Celda celda = introducirCelda(new Scanner(System.in), arbitro.obtenerTablero());
			realizarJugadaSiEsValida(celda);
			mostrarInformeCapturas(arbitro.obtenerTablero());
		}
		mostrarResultadoFinalPartida(arbitro);
	}

	/**
	 * Muestra el informe de piedras capturadas de cada color.
	 * 
	 * @param tablero tablero
	 */
	private static void mostrarInformeCapturas(Tablero tablero) {
		System.out.println("Piedras capturadas de color " + Color.NEGRO + ":\t"
				+ tablero.obtenerNumeroPiedrasCapturadas(Color.NEGRO));
		System.out.println("Piedras capturadas de color " + Color.BLANCO + ":\t"
				+ tablero.obtenerNumeroPiedrasCapturadas(Color.BLANCO));
	}

	/**
	 * Realiza la jugada si es valida, mostrando un error en caso contario.
	 * 
	 * @param celda celda
	 */
	private static void realizarJugadaSiEsValida(Celda celda) {
		try {
			if (celda != null && arbitro.esMovimientoLegal(celda)) {
				arbitro.jugar(celda);
			} else {
				mostarMensajeMovimientoIncorrecto();
			}
		} catch (CoordenadasIncorrectasException ex) {
			throw new RuntimeException("Error en validación de datos.", ex);
		}
	}

	/**
	 * Lee la entrada del usuario traduciéndola a la celda correspondiente.
	 * 
	 * @param scanner scanner
	 * @param tablero tablero
	 * @return celda correspondiente o null en caso de no existir equivalencia
	 */
	private static Celda introducirCelda(Scanner scanner, Tablero tablero) {
		String entrada;
		entrada = scanner.next().toUpperCase();
		return ConversorJugada.convertir(entrada, tablero);
	}

	/**
	 * Muestra la información sobre un movimiento incorrecto.
	 */
	private static void mostarMensajeMovimientoIncorrecto() {
		System.err.print("Movimiento incorrecto.\n" + " Compruebe sintaxis y semántica de la jugada "
				+ " (no se puede colocar en celda ocupada ni se permiten suicidios).");
	}

	/**
	 * Mustra el prompt del juego para introducir datos desde teclado.
	 * 
	 */
	private static void mostrarPrompt() {
		System.out.println();
		System.out.printf("El turno es de: %s con piedras %c de color %s%n",
				arbitro.obtenerJugadorConTurno().obtenerNombre(),
				arbitro.obtenerJugadorConTurno().obtenerColor().toChar(),
				arbitro.obtenerJugadorConTurno().obtenerColor());
		System.out.print("Introduce jugada: ");
	}

	/**
	 * Muestra el resultado final de la partida.
	 * 
	 * @param arbitro arbitro
	 */
	private static void mostrarResultadoFinalPartida(Arbitro arbitro) {
		mostrarTablero(arbitro.obtenerTablero());
		System.out.println();
		Jugador jugador = arbitro.obtenerGanador();
		System.out.println("El ganador es " + jugador.obtenerNombre());
	}

	/**
	 * Valida los argumentos del juego introducidos por teclado.
	 * 
	 * @param args argumentos
	 * @return true si son correctos, false en caso contario
	 */
	private static boolean sonArgumentosValidos(String[] args) {
		boolean validacion = true;
		if (args.length != 0 && args.length != 3 && args.length != 4) {
			validacion = false;
		} else if (args.length >= 3) {
			// Validacion de tamaños
			tamaño = Integer.parseInt(args[2]);
			if (!ConfiguracionAtariGo.esTamañoValido(tamaño)) {
				validacion = false;
			}
			if (args.length == 4) {
				int cota = Integer.parseInt(args[3]);
				if (cota < ConfiguracionAtariGo.MINIMO_CAPTURAS || cota > ConfiguracionAtariGo.MAXIMO_CAPTURAS) {
					validacion = false;
				}
			}
		}
		return validacion;
	}

	/**
	 * Inicializa los elementos del juego. Se supone que los argumentos han sido
	 * validados previamente.
	 * 
	 * @param args argumentos válidos
	 */
	private static void inicializarConArgumentos(String[] args) {
		assert sonArgumentosValidos(args);
		String jugador1;
		String jugador2;
		int cota = INDEFINIDO;
		if (args.length == 0) {
			// Valores por defecto
			tamaño = ConfiguracionAtariGo.TAMAÑO_POR_DEFECTO;
			jugador1 = "Abel";
			jugador2 = "Cain";
		} else {
			tamaño = Integer.parseInt(args[2]);
			jugador1 = args[0];
			jugador2 = args[1];
			if (args.length == 4) {
				cota = Integer.parseInt(args[3]);
			}
		}
		inicializarArbitro(jugador1, jugador2, tamaño, cota);
	}

	/**
	 * Inicializa el árbitro.
	 * 
	 * @param jugador1 jugador1
	 * @param jugador2 jugador2
	 * @param tamaño tamaño
	 * @param cota cota de capturas mínima
	 */
	private static void inicializarArbitro(String jugador1, String jugador2, int tamaño, int cota) {
		Tablero tablero = new Tablero(tamaño, tamaño);
		arbitro = (cota == INDEFINIDO) ? new ArbitroAtariGoBasico(tablero) : new ArbitroAtariGoAvanzado(tablero, cota);
		arbitro.registrarJugadoresEnOrden(jugador1);
		arbitro.registrarJugadoresEnOrden(jugador2);
	}

	/**
	 * Muestra la ayuda en línea para la inicialización correcta del juego.
	 */
	private static void mostrarAyuda() {
		System.out.println("Juego del AtariGo");
		System.out.println();
		System.out.println("Uso1: java juego.textui.AtariGo nombreJugador1 nombreJugador2 tamaño [cota]");
		System.out.println("Uso2 (valores por defecto): java juego.textui.AtariGo");
		System.out.println("El valor [cota] es opcional indicando su presencia el modo avanzado.");
		System.err.println("Tamaño y cota debe ser uno de los permitidos en la configuración del juego."
				+ ConfiguracionAtariGo.generarAyuda());
	}

	/**
	 * Muestra el estado actual del tablero.
	 * 
	 * @param tablero tablero a pintar en pantalla.
	 */
	private static void mostrarTablero(Tablero tablero) {
		pintarFilas(tablero);
		pintarLetrasEnLineaInferior(tablero);
	}

	/**
	 * Pinta las filas con el número correspondiente y las piedras en cada fila.
	 * 
	 * @param tablero tablero
	 */
	private static void pintarFilas(Tablero tablero) {
		try {
			System.out.println();
			for (int i = 0; i < tablero.obtenerNumeroFilas(); i++) {
				System.out.print((tablero.obtenerNumeroFilas() - i) + "\t");
				for (int j = 0; j < tablero.obtenerNumeroColumnas(); j++) {
					Celda celda = tablero.obtenerCelda(i, j);
					if (celda.estaVacia()) {
						System.out.print(" - ");
					} else {
						Color color = celda.obtenerColorDePiedra();
						System.out.printf(" %s ", color.toChar());
					}
				}
				System.out.println();
			}
		} catch (CoordenadasIncorrectasException ex) {
			throw new RuntimeException("Error en generación de filas del tablero.", ex);
		}
	}

	/**
	 * Pinta la fila inferior con las letras.
	 * 
	 * @param tablero tablero
	 */
	private static void pintarLetrasEnLineaInferior(Tablero tablero) {
		System.out.println();
		System.out.print("\t");
		for (int i = 0; i < tablero.obtenerNumeroColumnas() + 1; i++) {
			char letra = (char) (i + 'A');
			if (letra != ConfiguracionAtariGo.LETRA_NO_UTILIZADA) {
				System.out.print(" " + letra + " ");
			}
		}
		System.out.println();
	}

	/**
	 * Muestra la ayuda en línea en caso de error grave en la aplicación.
	 * 
	 * @param ex excepción que provoca la interrupción de la ejecución.
	 */
	private static void mostrarError(Exception ex) {
		System.out.println("Juego del AtariGo");
		System.out.println();
		System.out.println("Error grave en el programa, se recomienda no seguir con la ejecución.");
		System.out.println("Error detectado:" + ex.toString());
		System.err.println("Remita el log a su proveedor para ayudar a la corrección del error.");
		logger.error("Error grave en ejecución. Ver traza.", ex);
	}
}