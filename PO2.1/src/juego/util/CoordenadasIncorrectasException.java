package juego.util;

/**
 * Excepción de tiempo de ejecución correspondiente a unas coordenadas no válidas.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
 */
public class CoordenadasIncorrectasException extends RuntimeException {

    /**
     * Construye una una excepción de coordenadas con <code>null</code> como mensaje de detalle.
     */
    public CoordenadasIncorrectasException() {
        super();
    }

    /**
     * Construye una una excepción de coordenadas con un mensaje de detalle específico.
     *
     * @param arg0 Mensaje de detalle.
     */
    public CoordenadasIncorrectasException(String arg0) {
        super(arg0);
    }

    /**
     * Construye una una excepción de coordenadas con la causa especificada y el detalle
     * del <code>Throwable</code> o <code>null</code> en caso de que sea nulo.
     *
     * @param arg0 Causa y Detalle de la excepción.
     */
    public CoordenadasIncorrectasException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Construye una una excepción de coordenadas con un mensaje de detalle específico y una causa.
     *
     * @param arg0 Detalle de la excepción.
     * @param arg1 Causa de la excepción.
     */
    public CoordenadasIncorrectasException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Construye una una excepción de coordenadas con un mensaje de detalle específico, una causa,
     * habilidad para suprimir la excepción y habilidad para escribir en el strack trace.
     *
     * @param arg0 Detalle de la excepción.
     * @param arg1 Causa de la excepción.
     * @param arg2 Supresión de la excepción habilitada.
     * @param arg3 Escritura en stack trace habilitada.
     */
    public CoordenadasIncorrectasException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }
}
