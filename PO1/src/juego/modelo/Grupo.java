package juego.modelo;

import java.util.ArrayList;

/**
 * Grupo de celdas de un mismo jugador.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class Grupo {
    private static int contador = 0;
    private final int id;
    private final Color color;
    private final Tablero tablero;
    public final ArrayList celdas = new ArrayList();

    public Grupo(Celda celda, Tablero tablero) {
        this.id = contador++;
        this.color = celda.obtenerPiedra() == null ? null : celda.obtenerPiedra().obtenerColor();
        this.tablero = tablero;
        celdas.add(celda);
        // TODO @FWMBR contador counts testing groups.
    }

    /**
     * Obtiene el número identificativo del grupo.
     *
     * @return id del grupo.
     */
    public int obtenerId() {
        return id;
    }

    /**
     * Obtiene el color de las piedras del grupo,
     * o null en caso de que el grupo haya sido capturado
     *
     * @return color del grupo o null
     */
    public Color obtenerColor() {
        return color;
    }

    /**
     * Comprueba si los grados de libertad son mayores que cero.
     *
     * @return <code>true</code> si tiene al menos un grado de libertad,
     * <code>false</code> en caso contrario.
     */
    public boolean estaVivo() {
        int gradosDeLibertad = 0;
        for (Object celda : celdas)
            gradosDeLibertad += tablero.obtenerGradosDeLibertad((Celda) celda);
        return gradosDeLibertad > 0;
    }

    /**
     * Obtiene el número de celdas que componen un grupo.
     *
     * @return número de celdas del grupo.
     */
    public int obtenerTamaño() {
        return celdas.size();
    }

    /**
     * Comprobar si el grupo contiene una determinada celda.
     *
     * @param celda Celda a ser comprobada.
     * @return <code>true</code> si es contenida por el grupo,
     * <code>false</code> si no lo es.
     */
    public boolean contiene(Celda celda) {
        for (Object celdaComparar : celdas)
            if (celda.tieneIgualesCoordenadas((Celda) celdaComparar))
                return true;
        return false;
    }

    /**
     * Añade un grupo de celdas al grupo.
     *
     * @param grupo Grupo de celdas a ser añadidas.
     */
    public void añadirCeldas(Grupo grupo) {
        celdas.addAll(grupo.celdas);
    }

    /**
     * Elimina todas las piedras del grupo.
     */
    public void eliminarPiedras() {
        for (Object celda : celdas)
            ((Celda) celda).eliminarPiedra();
    }

    /**
     * Crea una copia del grupo en un tablero.
     *
     * @param otroTablero Tablero donde crear copia.
     * @return Grupo equivalente en otroTablero.
     */
    public Grupo generarCopiaEnOtroTablero(Tablero otroTablero) {
        for (Object celda : celdas)
            otroTablero.colocar(new Piedra(((Celda) celda).obtenerColorDePiedra()), otroTablero.obtenerCeldaConMismasCoordenadas((Celda) celda));
        Grupo nuevo = new Grupo(otroTablero.obtenerCeldaConMismasCoordenadas((Celda) celdas.get(0)), otroTablero);
        for (int i = 1; i < obtenerTamaño(); i++) {
            nuevo.añadirCeldas(new Grupo(otroTablero.obtenerCeldaConMismasCoordenadas((Celda) celdas.get(i)), otroTablero));
        }
        return nuevo;
    }

    /**
     * Información del objeto en String.
     *
     * @return String del objeto.
     */
    public String toString() {
        return "Grupo(" + obtenerId() + ")" + "{ tamaño=" + obtenerTamaño() + ", color=" + obtenerColor() + ", vivo=" + (estaVivo()?"true":"false") +" }";
    }
}
