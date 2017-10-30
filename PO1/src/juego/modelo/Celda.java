package juego.modelo;

/**
* Celda donde se colocaran las piedras.
*
* @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
* @version 1.0
*/
public class Celda {
	private int fila;
	private int columna;
	private Piedra piedra;
	
	/**
	 * Constructor de la celda.
	 * 
	 * @param fila Fila en la que se encuentra.
	 * @param columna Columna en la que se encuentra.
	 */
	public Celda(int fila, int columna) {
		assert fila >= 0 && columna >= 0;
		this.fila = fila;
		this.columna = columna;
	}
	
	/**
	 * Devuelve la piedra que tiene.
	 * 
	 * @return Piedra de la celda.
	 */
	public Piedra obtenerPiedra() {
		return this.piedra;
	}
	
	/**
	 * Obtiene el color de una piedra
	 *
	 * @return Color de la piedra
	 */
	public Color obtenerColorDePiedra() {
	    assert ! estaVacia();
		return piedra.obtenerColor();
	}
	
	/**
	 * Asigna una determinada piedra a la celda.
	 * 
	 * @param piedra Nueva piedra de la celda.
	 */
	public void establecerPiedra(Piedra piedra) {
		this.piedra = piedra;
	}
	
	/**
	 * Comprobación de si la celda está vacía.
	 * 
	 * @return Verdadero en caso de que esté vacía.
	 */
	public boolean estaVacia() {
		return this.piedra == null;
	}
	
	/**
	 * Obtiene la fila en la que se encuentra.
	 * 
	 * @return Fila del tablero.
	 */
	public int obtenerFila() {
		return this.fila;
	}
	
	/**
	 * Obtiene la columna en la que se encuentra.
	 * 
	 * @return Columna del tablero.
	 */
	public int obtenerColumna() {
		return this.columna;
	}
	
	/**
	 * Comprobación de mismas coordenadas con otra celda.
	 * 
	 * @param otra Celda a comparar.
	 * @return Verdadero en caso de que estén en las mismas coordenadas.
	 */
	public boolean tieneIgualesCoordenadas(Celda otra) {
		return obtenerFila() == otra.obtenerFila() && obtenerColumna() == otra.obtenerColumna(); 
	}
	
	
	/**
	 * Elimina la piedra asginada a la celda.
	 */
	public void eliminarPiedra() {
		this.piedra = null;
	}
	
	/**
	 * Información del objeto en String.
	 * 
	 * @return String del objeto.
	 */
	public String toString() {
		return "(" + obtenerFila() + " / " + obtenerColumna() + ")";
	}
}
