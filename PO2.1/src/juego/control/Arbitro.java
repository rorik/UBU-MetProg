package juego.control;

import juego.modelo.Celda;
import juego.modelo.Jugador;
import juego.modelo.Tablero;
import juego.util.CoordenadasIncorrectasException;

/**
 * Interfaz del arbitro.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
 */
public interface Arbitro {
    /**
     * Registra el primer jugador como color negro, el segundo
     * como color blanco e ignora el resto.
     *
     * @param nombre Nombre del jugador.
     */
    void registrarJugadoresEnOrden(String nombre);

    /**
     * Obtiene el jugador que puede realizar turno.
     *
     * @return Jugador con turno.
     */
    Jugador obtenerJugadorConTurno();

    /**
     * Obtiene el jugador que no puede realizar turno.
     *
     * @return Jugador sin turno.
     */
    Jugador obtenerJugadorSinTurno();

    /**
     * Pasa al siguiente turno.
     */
    void cambiarTurno();

    /**
     * Obtiene el tablero del juego.
     *
     * @return Tablero.
     */
    Tablero obtenerTablero();

    /**
     * Obtiene si el juego ha acabado o no.
     *
     * @return <code>true</code> si ha acabado o <code>false</code> en caso contrario.
     */
    boolean estaAcabado();

    /**
     * Obtiene el ganador del juego.
     *
     * @return Jugador ganador o <code>null</code> si no ha acabado el juego.
     */
    Jugador obtenerGanador();

    /**
     * Ejecuta una jugada y cambia de turno.
     *
     * @param celda Celda en la que realizar jugada.
     */
    void jugar(Celda celda) throws CoordenadasIncorrectasException;

    /**
     * Comprueba si un movimiento es legal. Comprobando que la celda
     * esté vacía y que el movimiento no resulte en perdida para el jugador.
     *
     * @param celda Celda a ser comprobada.
     * @return <code>true</code> si se puede realizar,
     * <code>false</code> en caso contrario.
     */
    boolean esMovimientoLegal(Celda celda);

}
