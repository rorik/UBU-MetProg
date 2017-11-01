package juego.control;

import juego.modelo.*;
import juego.util.Sentido;

import java.util.ArrayList;

/**
 * Arbitro del juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class ArbitroAtariGo {
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
    public void registrarJugadoresEnOrden(String nombre) {
        for (int i = 0; i < 2; i++)
            if (jugadores[i] == null) {
                jugadores[i] = new Jugador(nombre, new Color[]{Color.NEGRO, Color.BLANCO}[i]);
                break;
            }
    }

    /**
     * Obtiene el jugador que puede realizar turno.
     *
     * @return Jugador con turno.
     */
    public Jugador obtenerJugadorConTurno() {
        return jugadores[turno ? 1 : 0];
    }

    /**
     * Obtiene el jugador que no puede realizar turno.
     *
     * @return Jugador sin turno.
     */
    public Jugador obtenerJugadorSinTurno() {
        return jugadores[turno ? 0 : 1];
    }

    /**
     * Pasa al siguiente turno.
     */
    public void cambiarTurno() {
        turno = !turno;
    }

    /**
     * Obtiene el tablero del juego.
     *
     * @return Tablero.
     */
    public Tablero obtenerTablero() {
        return tablero;
    }

    /**
     * Obtiene si el juego ha acabado o no.
     *
     * @return <code>true</code> si ha acabado o <code>false</code> en caso contrario.
     */
    public boolean estaAcabado() {
        return obtenerGanador() != null;
    }

    /**
     * Obtiene el ganador del juego.
     *
     * @return Jugador ganador o <code>null</code> si no ha acabado el juego.
     */
    public Jugador obtenerGanador() {
        for (int i = 0; i < jugadores.length; i++) {
            boolean puedeRealizarMovimientos = false;
            ArrayList grupos = tablero.obtenerGruposDelJugador(jugadores[i]);
            for (Object grupo : grupos) {
                if (((Grupo) grupo).estaVivo()) puedeRealizarMovimientos = true;
            }
            if (!puedeRealizarMovimientos && (tablero.estaCompleto() || grupos.size() > 0))
                return jugadores[++i % 2];
        }
        return null;
        // @TODO @PR=10 @NEXTVERSION Game stops when a single group is dead.
    }

    /**
     * Ejecuta una jugada y cambia de turno.
     *
     * @param celda Celda en la que realizar jugada.
     */
    public void jugar(Celda celda) {
        tablero.colocar(obtenerJugadorConTurno().generarPiedra(), celda);
        cambiarTurno();
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
        if (!celda.estaVacia()) return false;
        ArbitroAtariGo copia = new ArbitroAtariGo(tablero.generarCopia());
        for (Jugador jugador : jugadores)
            copia.registrarJugadoresEnOrden(jugador.obtenerNombre());
        if (turno) copia.cambiarTurno();
        copia.jugar(copia.obtenerTablero().obtenerCeldaConMismasCoordenadas(celda));
        return esEspacioAbierto(copia.obtenerTablero().obtenerCeldaConMismasCoordenadas(celda), copia.obtenerTablero());
    }

    private boolean esEspacioAbierto(Celda celda, Tablero tablero) {
        ArrayList paredes = new ArrayList();
        agruparAdyacentes(new Grupo(celda, tablero), celda, tablero, paredes);
        return paredes.contains(Sentido.NORTE) && paredes.contains(Sentido.SUR) ||
                paredes.contains(Sentido.OESTE) && paredes.contains(Sentido.ESTE);
    }

    private void agruparAdyacentes(Grupo grupo, Celda celda, Tablero tablero, ArrayList paredes) {
        if (celda.obtenerFila() == 0 && !paredes.contains(Sentido.NORTE))
            paredes.add(Sentido.NORTE);
        else if (celda.obtenerFila() == tablero.obtenerNumeroFilas() - 1 && !paredes.contains(Sentido.SUR))
            paredes.add(Sentido.SUR);
        if (celda.obtenerColumna() == 0 && !paredes.contains(Sentido.OESTE))
            paredes.add(Sentido.OESTE);
        else if (celda.obtenerColumna() == tablero.obtenerNumeroColumnas() - 1 && !paredes.contains(Sentido.ESTE))
            paredes.add(Sentido.ESTE);
        for (Object celdaAdyacente : tablero.obtenerCeldasAdyacentes(celda)) {
            if (agruparCeldaAdyacente(grupo, (Celda) celdaAdyacente, tablero))
                agruparAdyacentes(grupo, (Celda) celdaAdyacente, tablero, paredes);
        }
    }

    private boolean agruparCeldaAdyacente(Grupo grupo, Celda celda, Tablero tablero) {
        if ((celda.estaVacia() || celda.obtenerColorDePiedra() == grupo.obtenerColor()) && !grupo.contiene(celda)) {
            grupo.añadirCeldas(new Grupo(celda, tablero));
            return true;
        }
        return false;
    }

}
