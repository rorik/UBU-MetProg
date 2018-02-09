package juego.control;

import juego.modelo.Tablero;

/**
 * Arbitro Básico.
 * El juego termina al realizar una conquista.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
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
     * Comprueba si se produce ko, es decir,
     * el tablero vuelve al mismo estado que la anterior jugada del jugador.
     *
     * @param tablero Tablero de la jugada a comprobar.
     * @return <code>false</code> en ArbitroBasico.
     */
    @Override
    protected boolean esKo(Tablero tablero) {
        return false;
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
