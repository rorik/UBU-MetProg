package juego.modelo;

/**
* Información del jugador.
*
* @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
* @version 1.0
*/
public class Jugador {
	private String nombre;
	private Color color;
	
	/**
	 * Constructor del jugador.
	 * 
	 * @param nombre: Nombre del jugador.
	 * @param color: Color del jugador.
	 */
	public Jugador(String nombre, Color color) {
		assert color != null;
		this.nombre = nombre;
		this.color = color;
	}
	
	/**
	 * Devuelve el color del jugador.
	 * 
	 * @return Color.
	 */
	public Color obtenerColor() {
		return this.color;
	}
	
	/**
	 * Devuelve le nombre del jugador.
	 * 
	 * @return Nombre.
	 */
	public String obtenerNombre() {
		return this.nombre;
	}
	
	/**
	 * Genera una nueva piedra del mismo color que el jugador.
	 * 
	 * @return Nueva piedra.
	 */
	public Piedra generarPiedra() {
		return new Piedra(obtenerColor());
	}
	
	/**
	 * Información del objeto en String.
	 * 
	 * @return String del objeto.
	 */
	public String toString() {
		return obtenerNombre() + "/" + obtenerColor();
	}
}
