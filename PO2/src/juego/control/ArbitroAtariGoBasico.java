package juego.control;

import juego.modelo.Tablero;

/**
 * Arbitro Básico.
 * El juego termina al realizar una conquista.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.0
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
     * Obtiene el numero mínimo de piedras que se deben capturar en una sola jugada para finalizar el encuentro.
     * En el caso del arbitro básico, siempre va a ser 1, ya que termina con la primera conquista.
     *
     * @return Cota del número de capturas
     */
    @Override
    protected int obtenerCota() {
        return 1;
    }
}
