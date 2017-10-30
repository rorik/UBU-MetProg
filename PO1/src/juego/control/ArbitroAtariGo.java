package juego.control; 

import juego.modelo.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
* Arbitro del juego.
*
* @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
* @version 1.0
*/
public class ArbitroAtariGo {
    private Tablero tablero;
    private boolean turno = false;
	private Jugador[] jugadores = new Jugador[2];
	private Jugador ganador;

	public ArbitroAtariGo(Tablero tablero) {
		this.tablero = tablero;
		// TODO @FWMBR
	}

    /**
     * Registra el primer jugador como color negro, el segundo
     * como color blanco e ignora el resto.
     *
     * @param nombre Nombre del jugador.
     */
	public void registrarJugadoresEnOrden(String nombre) {
        for (int i = 0; i < 2; i++)
            if (jugadores[i] == null) jugadores[i] = new Jugador(nombre, new Color[]{Color.NEGRO, Color.BLANCO}[i]);
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
	
	public boolean estaAcabado() {
		// TODO @FUTURE @PR=5 Create estaAcabado() method.
		return false;
	}

    /**
     * Obtiene el ganador del juego.
     * @return Jugador ganador o <code>null</code> si no ha acabado el juego.
     */
	public Jugador obtenerGanador() {
		return ganador;
	}
	
	public void jugar(Celda celda) {
	    tablero.colocar(obtenerJugadorConTurno().generarPiedra(), celda);
	    cambiarTurno();
		// TODO @MAJOR @PR=8 Finish jugar() method.
	}
	
	public boolean esMovimientoLegal(Celda celda) {
		if (!celda.estaVacia()) return false;
        // TODO @MAJOR @PR=8 Finish esMovimientoLegal() method.
		return true;
	}
	
}
	