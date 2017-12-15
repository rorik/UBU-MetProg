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
    private int id;
    private Tablero tablero;
    private final ArrayList<Celda> celdas = new ArrayList<>();

    /**
     * Constructor del grupo.
     * @param celda Primera celda a ser añadida.
     * @param tablero Tablero del juego.
     */
    public Grupo(Celda celda, Tablero tablero) {
        assert !celda.estaVacia() : "La celda [" + celda.toString() + "] está vacía";
        id = contador++;
        this.tablero = tablero;
        celdas.add(celda);
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
     * o <code>null</code> en caso de que el grupo haya sido capturado.
     *
     * @return color del grupo o <code>null</code> si no esta vivo.
     */
    public Color obtenerColor() {
        return estaVivo() ? celdas.get(0).obtenerColorDePiedra() : null;
    }

    /**
     * Comprueba si los grados de libertad son mayores que cero.
     *
     * @return <code>true</code> si tiene al menos un grado de libertad,
     * <code>false</code> en caso contrario.
     */
    public boolean estaVivo() {
        for (Celda celda : celdas) {
            if (tablero.obtenerGradosDeLibertad(celda) > 0) {
                return true;
            }
        }
        return false;
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
        for (Celda celdaComparar : celdas) {
            if (celda.tieneIgualesCoordenadas(celdaComparar)) {
                return true;
            }
        }
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
        for (Celda celda : celdas) {
            celda.eliminarPiedra();
        }
    }

    /**
     * Crea una copia del grupo en un tablero.
     *
     * @param otroTablero Tablero donde crear copia.
     * @return Grupo equivalente en otroTablero.
     */
    public Grupo generarCopiaEnOtroTablero(Tablero otroTablero) {
        Grupo nuevo = moverCelda(0, otroTablero);
        for (int i = 1; i < obtenerTamaño(); i++) {
            nuevo.añadirCeldas(moverCelda(i, otroTablero));
        }
        return nuevo;
    }

    /**
     * Mueve una celda del tablero a otro tablero, generando una nueva piedra si corresponde.
     *
     * @param indice Índice de la celda en celdas.
     * @param otroTablero El tablero al cual se va a copiar la celda
     * @return Grupo que contiene la celda.
     */
    private Grupo moverCelda(int indice, Tablero otroTablero) {
        Celda celda = celdas.get(indice);
        if (celda.obtenerColorDePiedra() != null) {
            otroTablero.colocar(new Piedra(celda.obtenerColorDePiedra()), otroTablero.obtenerCeldaConMismasCoordenadas(celda));
        }
        return new Grupo(otroTablero.obtenerCeldaConMismasCoordenadas(celda), otroTablero);
    }

    /**
     * Información del objeto en String.
     *
     * @return String del objeto.
     */
    public String toString() {
        return "Grupo(" + obtenerId() + ")" + "{ tamaño=" + obtenerTamaño() + ", color=" + obtenerColor() + ", vivo=" + (estaVivo() ? "true" : "false") + " }";
    }
}
