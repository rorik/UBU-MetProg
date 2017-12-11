package juego.control;

import juego.modelo.Celda;
import juego.modelo.Tablero;

/**
 * Arbitro Avanzado. TODO @PR=2 Additional doc.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class ArbitroAtariGoAvanzado extends ArbitroAtariGo {

    private int cotaNumeroCapturas;

    /**
     * Constructor del arbitro avanzado.
     *
     * @param tablero Tablero del juego.
     */
    public ArbitroAtariGoAvanzado(Tablero tablero, int cotaNumeroCapturas) {
        super(tablero);
        this.cotaNumeroCapturas = cotaNumeroCapturas;
    }

    /**
     * Obtiene si el juego ha acabado o no.
     *
     * @return <code>true</code> si ha acabado o <code>false</code> en caso contrario.
     */
    @Override
    public boolean estaAcabado() {
        return false || obtenerTablero().estaCompleto();
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
        return false;
    }
}
