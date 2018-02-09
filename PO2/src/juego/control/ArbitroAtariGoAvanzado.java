package juego.control;

import juego.modelo.Tablero;


/**
 * Arbitro Avanzado. Acepta una cota mínima en el constructor.
 * El juego termina cuando se conquiste al menos el mismo número de
 * piedras que la cota mínima establecida.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.0
 */
public class ArbitroAtariGoAvanzado extends ArbitroAtariGo {

    private final int cotaNumeroCapturas;

    /**
     * Constructor del arbitro avanzado.
     *
     * @param tablero Tablero del juego.
     * @param cotaNumeroCapturas Número de piedras a ser capturadas para ganar.
     */
    public ArbitroAtariGoAvanzado(Tablero tablero, int cotaNumeroCapturas) {
        super(tablero);
        this.cotaNumeroCapturas = cotaNumeroCapturas;
    }

    /**
     * Obtiene el numero mínimo de piedras que se deben capturar para finalizar el encuentro.
     *
     * @return Cota del número de capturas
     */
    @Override
    protected int obtenerCota() {
        return cotaNumeroCapturas;
    }
}
