package juego.control;

import juego.modelo.Celda;
import juego.modelo.Tablero;
import juego.util.CoordenadasIncorrectasException;


/**
 * Arbitro Avanzado. Acepta una cota mínima en el constructor.
 * El juego termina cuando se conquiste al menos el mismo número de
 * piedras que la cota mínima establecida.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
 */
public class ArbitroAtariGoAvanzado extends ArbitroAtariGo {

    private final int cotaNumeroCapturas;
    private Tablero[] ultimoMovimiento = new Tablero[2];

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
     * Ejecuta una jugada y cambia de turno.
     *
     * @param celda Celda en la que realizar jugada.
     * @throws CoordenadasIncorrectasException en caso de que se juegue en una celda que no esté dentro del tablero.
     */
    @Override
    public void jugar(Celda celda) throws CoordenadasIncorrectasException {
        super.jugar(celda);
        ultimoMovimiento[obtenerJugadorSinTurno().obtenerColor().ordinal()] = obtenerTablero().generarCopia();
    }

    /**
     * Comprueba si se produce ko, es decir,
     * el tablero vuelve al mismo estado que la anterior jugada del jugador.
     *
     * @param tablero Tablero de la jugada a comprobar.
     * @return <code>true</code> si se produce ko en el tablero,
     * <code>false</code> en caso contrario.
     */
    @Override
    protected boolean esKo(Tablero tablero) {
        int jugador = obtenerJugadorConTurno().obtenerColor().ordinal();
        return ultimoMovimiento[jugador] != null &&
                tablero.esIgual(ultimoMovimiento[jugador]);
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
