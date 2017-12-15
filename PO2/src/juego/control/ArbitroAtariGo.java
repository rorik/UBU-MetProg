package juego.control;

import juego.modelo.*;

import java.util.ArrayList;

/**
 * Arbitro del juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public abstract class ArbitroAtariGo implements Arbitro {
    private final Tablero tablero;
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
     */
    @Override
    public void jugar(Celda celda) {
        ArrayList<Grupo> gruposEnTablero = tablero.obtenerGruposDelJugador(obtenerJugadorConTurno());
        gruposEnTablero.addAll(tablero.obtenerGruposDelJugador(obtenerJugadorSinTurno()));
        obtenerTablero().colocar(obtenerJugadorConTurno().generarPiedra(), celda);
        cambiarTurno();
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
        ArbitroAtariGo copia = generarCopia();
        for (Jugador jugador : jugadores) {
            copia.registrarJugadoresEnOrden(jugador.obtenerNombre());
        }
        if (turno) {
            copia.cambiarTurno();
        }
        copia.jugar(copia.obtenerTablero().obtenerCeldaConMismasCoordenadas(celda));
        return copia.obtenerTablero().obtenerNumeroPiedrasCapturadas(obtenerJugadorSinTurno().obtenerColor()) > 0 ||
                copia.obtenerTablero().obtenerNumeroPiedrasCapturadas(obtenerJugadorConTurno().obtenerColor()) == 0;
    }

    /**
     * Genera una copia del arbitro actual.
     *
     * @return Arbitro con un nuevo tablero y el mismo estado de juego.
     */
    protected abstract ArbitroAtariGo generarCopia();

    /**
     * Obtiene El numero mínimo de piedras que se deben capturar en una sola jugada para finalizar el encuentro.
     *
     * @return Cota del número de capturas
     */
    protected abstract int obtenerCota();
}
