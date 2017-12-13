package juego.control;

import juego.modelo.Celda;
import juego.modelo.Jugador;
import juego.modelo.Tablero;

/**
 * Arbitro Básico. TODO @PR=2 Additional doc.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class ArbitroAtariGoBasico extends ArbitroAtariGo {
    /**
     * Constructor del arbitro básico.
     *
     * @param tablero Tablero del juego.
     */
    public ArbitroAtariGoBasico(Tablero tablero) {
        super(tablero);
    }

    /**
     * Obtiene si el juego ha acabado o no.
     *
     * @return <code>true</code> si ha acabado o <code>false</code> en caso contrario.
     */
    @Override
    public boolean estaAcabado() {
        return conquistaUltimoTurno || obtenerTablero().estaCompleto();
    }

    /**
     * Comprueba si un movimiento es legal. Comprobando que la celda
     * esté vacía y que el movimiento no resulte en perdida para el jugador.
     *
     * @param celda Celda a ser comprobada.
     * @return <code>true</code> si se puede realizar,
     * <code>false</code> en caso contrario.
     */
    @Override
    public boolean esMovimientoLegal(Celda celda) {
        if (!celda.estaVacia()) {
            return false;
        }
        ArbitroAtariGo copia = new ArbitroAtariGoBasico(obtenerTablero().generarCopia());
        for (Jugador jugador : jugadores) {
            copia.registrarJugadoresEnOrden(jugador.obtenerNombre());
        }
        if (turno) {
            copia.cambiarTurno();
        }
        copia.jugar(copia.obtenerTablero().obtenerCeldaConMismasCoordenadas(celda));
        return !copia.estaAcabado() || copia.obtenerGanador().obtenerColor() == obtenerJugadorConTurno().obtenerColor();
    }
}
