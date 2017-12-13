package juego.control;

import juego.modelo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Arbitro del juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public abstract class ArbitroAtariGo implements Arbitro {
    private final Tablero tablero;
    protected boolean turno = false;
    protected final Jugador[] jugadores = new Jugador[2];
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
     * Obtiene el ganador del juego.
     *
     * @return Jugador ganador o <code>null</code> si no ha acabado el juego.
     */
    @Override
    public Jugador obtenerGanador() {
        ArrayList<Grupo> grupos = obtenerTablero().obtenerGruposDelJugador(obtenerJugadorConTurno());
        for (Grupo grupo : grupos) {
            if (!grupo.estaVivo()) {
                return obtenerJugadorSinTurno();
            }
        }
        grupos = obtenerTablero().obtenerGruposDelJugador(obtenerJugadorSinTurno());
        for (Grupo grupo : grupos) {
            if (!grupo.estaVivo()) {
                return obtenerJugadorConTurno();
            }
        }
        return null;
    }

    /**
     * Obtiene todos los grupos de un jugador que estén muertos.
     *
     * @param jugador Jugador del cual obtener los grupos.
     * @return Grupos que no estén vivos.
     */
    private List<Grupo> obtenerGruposMuertos(Jugador jugador) {
        ArrayList<Grupo> gruposMuertos = new ArrayList<>();
        for (Grupo grupo : obtenerTablero().obtenerGruposDelJugador(jugador)) {
            if (!grupo.estaVivo()) {
                gruposMuertos.add(grupo);
            }
        }
        return gruposMuertos;
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
        //conquistaUltimoTurno = obtenerGanador() != null;
        List<Grupo> muertos = obtenerGruposMuertos(obtenerJugadorSinTurno());
        for (Grupo grupo : muertos) {
            System.out.println(grupo);
        }
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
