package juego.control;

import juego.modelo.Tablero;


/**
 * Arbitro Avanzado. Acepta una cota mínima en el constructor.
 * El juego termina cuando se realice una conquista que elimine
 * al menos el mismo número de la cota mínima establecida.
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
     * Obtiene el numero mínimo de piedras que se deben capturar en una sola jugada para finalizar el encuentro.
     *
     * @return Cota del número de capturas
     */
    @Override
    protected int obtenerCota() {
        return cotaNumeroCapturas;
    }

    /**
     * Genera una copia del arbitro actual.
     *
     * @return Arbitro con un nuevo tablero y el mismo estado de juego.
     */
    @Override
    protected ArbitroAtariGo generarCopia() {
        return new ArbitroAtariGoAvanzado(obtenerTablero().generarCopia(), cotaNumeroCapturas);
    }
}
