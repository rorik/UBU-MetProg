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
     * Una variable que usaremos para indicar que se ha producido conquista.
     */
    protected boolean conquistaUltimoTurno = false;

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
                jugadores[i] = new Jugador(nombre, new Color[]{Color.NEGRO, Color.BLANCO}[i]);
                break;
            }
        }
    }

    /**
     * Obtiene el jugador que puede realizar turno.
     *
     * @return Jugador con turno.
     */
    @Override
    public Jugador obtenerJugadorConTurno() {
        return jugadores[turno ? 1 : 0];
    }

    /**
     * Obtiene el jugador que no puede realizar turno.
     *
     * @return Jugador sin turno.
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
     * Obtiene el ganador del juego.
     *
     * @return Jugador ganador o <code>null</code> si no ha acabado el juego.
     */
    @Override
    public Jugador obtenerGanador() {
        ArrayList grupos = obtenerTablero().obtenerGruposDelJugador(obtenerJugadorConTurno());
        for (Object grupo : grupos) {
            if (!((Grupo) grupo).estaVivo()) {
                return obtenerJugadorSinTurno();
            }
        }
        grupos = obtenerTablero().obtenerGruposDelJugador(obtenerJugadorSinTurno());
        for (Object grupo : grupos) {
            if (!((Grupo) grupo).estaVivo()) {
                return obtenerJugadorConTurno();
            }
        }
        return null;
    }

    /**
     * Ejecuta una jugada y cambia de turno.
     *
     * @param celda Celda en la que realizar jugada.
     */
    @Override
    public void jugar(Celda celda) {
        obtenerTablero().colocar(obtenerJugadorConTurno().generarPiedra(), celda);
        comprobarConquista(celda);
        cambiarTurno();
    }

    /**
     * Comprueba si se ha producido una conquista.
     *
     * @param celda Celda donde se ha producido el movimiento (para eliminar grupos conquistados).
     */
    private void comprobarConquista(Celda celda) {
        conquistaUltimoTurno = obtenerGanador() != null;
        /*
        if (conquistaUltimoTurno) {
            // Eliminar celdas...
        }*/
    }

    /**
     * Comprueba si un movimiento es legal. Comprobando que la celda
     * esté vacía y que el movimiento no resulte en perdida para el jugador.
     *
     * @param celda Celda a ser comprobada.
     * @return <code>true</code> si se puede realizar,
     * <code>false</code> en caso contrario.
     */
    public abstract boolean esMovimientoLegal(Celda celda);

}
