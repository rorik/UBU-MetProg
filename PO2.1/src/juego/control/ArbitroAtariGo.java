package juego.control;

import juego.modelo.*;
import juego.util.CoordenadasIncorrectasException;

import java.util.ArrayList;

/**
 * Arbitro del juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
 */
public abstract class ArbitroAtariGo implements Arbitro {
    private Tablero tablero;
    private boolean turno = false;
    private final Jugador[] jugadores = new Jugador[2];

    /**
     * Constructor del arbitro.
     *
     * @param tablero Tablero del juego.
     */
    public ArbitroAtariGo(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Registra el primer jugador como color negro, el segundo
     * como color blanco e ignora el resto.
     *
     * @param nombre Nombre del jugador.
     */
    @Override
    public void registrarJugadoresEnOrden(String nombre) {
        for (int i = 0; i < 2; i++) {
            if (jugadores[i] == null) {
                jugadores[i] = new Jugador(nombre, Color.values()[i]);
                break;
            }
        }
    }

    /**
     * Obtiene el jugador que puede realizar turno.
     *
     * @return Jugador con turno, o <code>null</code> en caso de que no exista.
     */
    @Override
    public Jugador obtenerJugadorConTurno() {
        return jugadores[turno ? 1 : 0];
    }

    /**
     * Obtiene el jugador que no puede realizar turno.
     *
     * @return Jugador sin turno, o <code>null</code> en caso de que no exista.
     */
    @Override
    public Jugador obtenerJugadorSinTurno() {
        return jugadores[turno ? 0 : 1];
    }

    /**
     * Pasa al siguiente turno.
     */
    @Override
    public void cambiarTurno() {
        turno = !turno;
    }

    /**
     * Obtiene el tablero del juego.
     *
     * @return Tablero.
     */
    @Override
    public Tablero obtenerTablero() {
        return tablero;
    }

    /**
     * Obtiene si el juego ha acabado o no.
     *
     * @return <code>true</code> si ha acabado o <code>false</code> en caso contrario.
     */
    @Override
    public boolean estaAcabado() {
        return obtenerGanador() != null || obtenerTablero().estaCompleto();
    }

    /**
     * Ejecuta una jugada y cambia de turno.
     *
     * @param celda Celda en la que realizar jugada.
     * @throws CoordenadasIncorrectasException en caso de que se juegue en una celda que no esté dentro del tablero.
     */
    @Override
    public void jugar(Celda celda) throws CoordenadasIncorrectasException {
        try {
            obtenerTablero().colocar(obtenerJugadorConTurno().generarPiedra(), celda);
            cambiarTurno();
        }
        catch (CoordenadasIncorrectasException e) {
            throw new CoordenadasIncorrectasException("Se ha intentado jugar en una celda que no existe, celda=" +
                    celda.toString());
        }
    }

    /**
     * Obtiene el ganador del juego.
     *
     * @return Jugador ganador o <code>null</code> si no ha acabado el juego.
     */
    @Override
    public Jugador obtenerGanador() {
        if (obtenerTablero().obtenerNumeroPiedrasCapturadas(obtenerJugadorConTurno().obtenerColor()) >= obtenerCota()) {
            return obtenerJugadorSinTurno();
        }
        if (obtenerTablero().obtenerNumeroPiedrasCapturadas(obtenerJugadorSinTurno().obtenerColor()) >= obtenerCota()) {
            return obtenerJugadorConTurno();
        }
        return null;
    }

    /**
     * Comprueba si un movimiento es legal. Comprobando que la celda
     * esté vacía y que el movimiento no resulte en perdida para el jugador.
     *
     * @param celda Celda a ser comprobada.
     * @return <code>true</code> si se puede realizar,
     * <code>false</code> en caso contrario.
     */
    public boolean esMovimientoLegal(Celda celda) {
        if (!celda.estaVacia()) {
            return false;
        }
        Tablero old = obtenerTablero();
        this.tablero = old.generarCopia();
        obtenerTablero().colocar(obtenerJugadorConTurno().generarPiedra(), celda);
        boolean valid = (obtenerGanador() == obtenerJugadorConTurno() ||
                obtenerTablero().obtenerNumeroPiedrasCapturadas(obtenerJugadorSinTurno().obtenerColor()) >
                        old.obtenerNumeroPiedrasCapturadas(obtenerJugadorSinTurno().obtenerColor()) ||
                obtenerTablero().obtenerNumeroPiedrasCapturadas(obtenerJugadorConTurno().obtenerColor()) ==
                        old.obtenerNumeroPiedrasCapturadas(obtenerJugadorConTurno().obtenerColor())) &&
                        ! esKo(obtenerTablero());
        this.tablero = old;
        return valid;
    }

    /**
     * Comprueba si se produce ko, es decir,
     * el tablero vuelve al mismo estado que la anterior jugada del jugador.
     *
     * @param tablero Tablero de la jugada a comprobar.
     * @return <code>true</code> si se produce ko en el tablero,
     * <code>false</code> en caso contrario.
     */
    protected abstract boolean esKo(Tablero tablero);

    /**
     * Obtiene El numero mínimo de piedras que se deben capturar para finalizar el encuentro.
     *
     * @return Cota del número de capturas
     */
    protected abstract int obtenerCota();
}
