package juego.modelo;

/**
* Colores de jugadores y piedras.
*
* @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
* @version 1.0
*/
public enum Color {
	BLANCO('O'),
	NEGRO('X');
	
	private char caracter;
	
	/**
	 * Constructor del color.
	 * 
	 * @param caracter Carácter del color.
	 */
	Color(char caracter) {
		this.caracter = caracter;
	}
	
	/**
	 * Obtiene el carácter asignado al color.
	 * @return Carácter.
	 */
	public char toChar() {
		return this.caracter;
	}
}
