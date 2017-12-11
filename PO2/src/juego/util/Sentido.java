package juego.util;

/**
 * Sentido para comprobar celdas adyacentes.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:dcaubilla@ubu.es">David Caubilla</a>
 * 
 * @version 1.0 20051122
 * @see juego.modelo.Tablero
 */
public enum Sentido {
	/** Norte. */
	NORTE(0, -1),
	/** Este. */
	ESTE(+1, 0),
	/** Sur. */
	SUR(0, +1),
	/** Oeste. */
	OESTE(-1, 0);
	
	/** Desplazamiento en horizontal. */
	private int desplazamientoHorizontal;

	/** Desplazamiento en vertical. */
	private int desplazamientoVertical;

	/**
	 * Constructor.
	 * 
	 * @param desplazamientoHorizontal desplazamiento en horizontal
	 * @param desplazamientoVertical desplazamiento en vertical
	 */
	private Sentido(int desplazamientoHorizontal, int desplazamientoVertical) {
		establecerDesplazamientoHorizontal(desplazamientoHorizontal);
		establecerDesplazamientoVertical(desplazamientoVertical);
	}

	/**
	 * Obtiene el desplazamiento en horizontal.
	 * 
	 * @return desplazamiento en horizontal
	 */
	public int obtenerDesplazamientoHorizontal() {
		return desplazamientoHorizontal;
	}

	/**
	 * Establece el desplazamiento en horizontal.
	 * 
	 * @param desplazamientoHorizontal desplazamiento en horizontal
	 */
	private void establecerDesplazamientoHorizontal(int desplazamientoHorizontal) {
		this.desplazamientoHorizontal = desplazamientoHorizontal;
	}

	/**
	 * Obtiene el desplazamiento en vertical.
	 * 
	 * @return desplazamiento en vertical
	 */
	public int obtenerDesplazamientoVertical() {
		return desplazamientoVertical;
	}

	/**
	 * Establece el desplazamiento en vertical.
	 * 
	 * @param desplazamientoVertical desplazamiento en vertical.
	 */
	private void establecerDesplazamientoVertical(int desplazamientoVertical) {
		this.desplazamientoVertical = desplazamientoVertical;
	}

}
