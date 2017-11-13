package juego.modelo;

import juego.util.Sentido;

import java.util.ArrayList;

/**
 * Tablero de juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class Tablero {
    private Celda[][] celdas;
    private final ArrayList grupos = new ArrayList();

    /**
     * Constructor, itera por cada Celda y la inicializa.
     *
     * @param filas    número de filas
     * @param columnas número columnas
     */
    public Tablero(int filas, int columnas) {
        assert filas > 0 && columnas > 0;
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(i, j);
            }
        }
    }

    /**
     * Enlaza una piedra a una columna.
     *
     * @param piedra Piedra
     * @param celda  Celda
     */
    public void colocar(Piedra piedra, Celda celda) {
        celda.establecerPiedra(piedra);
        piedra.colocarEn(celda);
        fusionarGruposAdyacentes(celda);
    }

    /**
     * Añade una celda a un nuevo grupo y comprueba si hay grupos adyacentes,
     * en caso de que haya, agrupa todos en uno solo.
     *
     * @param celda Celda a meter en grupo y fusionar.
     */
    private void fusionarGruposAdyacentes(Celda celda) {
        Grupo nuevoGrupo = new Grupo(celda, this);
        grupos.add(nuevoGrupo);
        ArrayList gruposAdyacentes = obtenerGruposAdyacentes(celda);
        if (gruposAdyacentes.size() > 0) {
            ((Grupo) gruposAdyacentes.get(0)).añadirCeldas(nuevoGrupo);
            grupos.remove(nuevoGrupo);
            for (int i = 1; i < gruposAdyacentes.size(); i++) {
                ((Grupo) gruposAdyacentes.get(0)).añadirCeldas((Grupo) gruposAdyacentes.get(i));
                grupos.remove(gruposAdyacentes.get(i));
            }
        }
    }

    /**
     * Obtiene los grupos del mismo jugador que contienen
     * celdas adyacentes a la celda dada.
     *
     * @param celda Celda a la que obtener grupos adyacentes.
     * @return Lista de grupos adyacentes del mismo color.
     */
    private ArrayList obtenerGruposAdyacentes(Celda celda) {
        ArrayList adyacentesDelMismoColor = obtenerCeldasAdyacentesDelMismoColor(celda);
        ArrayList gruposAdyacentes = new ArrayList();
        for (Object celdaAdyacente : adyacentesDelMismoColor) {
            for (Object grupo : grupos) {
                if (((Grupo) grupo).obtenerColor() == ((Celda) celdaAdyacente).obtenerColorDePiedra() &&
                        ((Grupo) grupo).contiene((Celda) celdaAdyacente) &&
                        !gruposAdyacentes.contains(grupo)) {
                    gruposAdyacentes.add(grupo);
                }
            }
        }
        return gruposAdyacentes;
    }

    /**
     * Obtiene todas las celdas que contengan una piedra del mismo color,
     * relativo a la celda dada.
     *
     * @param celda Celda a la que obtener adyacentes y color.
     * @return Lista de celdas adyacentes del mismo color.
     */
    private ArrayList obtenerCeldasAdyacentesDelMismoColor(Celda celda) {
        ArrayList adyacentesDelMismoColor = new ArrayList();
        for (Object celdaAdyacente : obtenerCeldasAdyacentes(celda)) {
            if (!((Celda) celdaAdyacente).estaVacia()
                    && ((Celda) celdaAdyacente).obtenerColorDePiedra() == celda.obtenerColorDePiedra()) {
                adyacentesDelMismoColor.add(celdaAdyacente);
            }
        }
        return adyacentesDelMismoColor;
    }

    /**
     * Obtiene la celda que se encuentra en una determinada posición.
     *
     * @param fila    Fila
     * @param columna Columna
     * @return devuelve la celda que se encuentre en la posición (fila, columna), o
     * null en caso de que se salga de los límites
     */
    public Celda obtenerCelda(int fila, int columna) {
        return estaEnTablero(fila, columna) ? celdas[fila][columna] : null;
    }

    /**
     * Obtiene la celda que se tenga las mismas coordenadas que otra celda.
     *
     * @param celda celda de la cual conocemos las coordenadas
     * @return devuelve la celda que se encuentre en la posición (fila, columna), o
     * null en caso de que se salga de los límites
     */
    public Celda obtenerCeldaConMismasCoordenadas(Celda celda) {
        if (estaEnTablero(celda.obtenerFila(), celda.obtenerColumna())) {
            return celdas[celda.obtenerFila()][celda.obtenerColumna()];
        }
        return null;
    }

    /**
     * Calcula si una posición está dentro del tablero.
     *
     * @param fila    Fila
     * @param columna Columna
     * @return verdadero en caso de que se encuentre dentro, falso en el caso
     * contrario
     */
    public boolean estaEnTablero(int fila, int columna) {
        return fila >= 0 && fila < obtenerNumeroFilas() && columna >= 0 && columna < obtenerNumeroColumnas();
    }

    /**
     * Recorre toda el tablero y cuenta el número de piedras de un determinado color.
     *
     * @param color Color de las piedras a buscar
     * @return número de piedras encontradas
     */
    public int obtenerNumeroPiedras(Color color) {
        int cuenta = 0;
        for (int i = 0; i < obtenerNumeroFilas(); i++) {
            for (int j = 0; j < obtenerNumeroColumnas(); j++) {
                if (!obtenerCelda(i, j).estaVacia() && obtenerCelda(i, j).obtenerPiedra().obtenerColor() == color) {
                    cuenta++;
                }
            }
        }
        return cuenta;
    }

    /**
     * Devuelve el número de filas.
     *
     * @return número de filas
     */
    public int obtenerNumeroFilas() {
        return this.celdas.length;
    }

    /**
     * Devuelve el número de columnas.
     *
     * @return número de columnas
     */
    public int obtenerNumeroColumnas() {
        return this.celdas[0].length;
    }

    /**
     * Comprueba si toda el tablero está lleno de piedras.
     *
     * @return verdadero si esta completo, falso en caso contrario
     */
    public boolean estaCompleto() {
        for (int i = 0; i < obtenerNumeroFilas(); i++) {
            for (int j = 0; j < obtenerNumeroColumnas(); j++) {
                if (obtenerCelda(i, j).estaVacia()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Obtiene el grupo de celdas adyacentes
     *
     * @param celda Celda a la que se obtendrá sus adyacentes
     * @return Grupo de celdas adyacentes
     */
    public ArrayList obtenerCeldasAdyacentes(Celda celda) {
        ArrayList celdas = new ArrayList();
        for (Sentido sentido : Sentido.values()) {
            int fila = celda.obtenerFila() + sentido.obtenerDesplazamientoHorizontal();
            int columna = celda.obtenerColumna() + sentido.obtenerDesplazamientoVertical();
            if (estaEnTablero(fila, columna)) {
                celdas.add(obtenerCelda(fila, columna));
            }
        }
        return celdas;
    }

    /**
     * Obtiene los grados de libertad de una determinada celda
     *
     * @param celda Celda a ser comprobada
     * @return Grados de libertad de la celda
     */
    public int obtenerGradosDeLibertad(Celda celda) {
        int gradosDeLibertad = 0;
        for (Object celdasAdyacente : obtenerCeldasAdyacentes(celda)) {
            if (((Celda) celdasAdyacente).estaVacia()) {
                gradosDeLibertad++;
            }
        }
        return gradosDeLibertad;
    }

    /**
     * Crea una copia del tablero actual
     *
     * @return Copia
     */
    public Tablero generarCopia() {
        Tablero copia = new Tablero(obtenerNumeroFilas(), obtenerNumeroColumnas());
        for (Object grupo : grupos) {
            ((Grupo) grupo).generarCopiaEnOtroTablero(copia);
        }
        return copia;
    }

    /**
     * Devuelve todos los grupos del jugador
     *
     * @param jugador Jugador a ser consultados
     * @return Lista de grupos que pertenecen al jugador.
     */
    public ArrayList obtenerGruposDelJugador(Jugador jugador) {
        ArrayList gruposDelJugador = new ArrayList();
        for (Object grupo : grupos) {
            if (((Grupo) grupo).obtenerColor() == jugador.obtenerColor()) {
                gruposDelJugador.add(grupo);
            }
        }
        return gruposDelJugador;
    }


    /**
     * Información del objeto en String
     *
     * @return String del objeto
     */
    public String toString() {
        return "Tablero{ tamaño=(" + obtenerNumeroFilas() + "x" + obtenerNumeroColumnas() + "), piedrasBlancas=" +
                obtenerNumeroPiedras(Color.BLANCO) + ", piedrasNegras=" + obtenerNumeroPiedras(Color.NEGRO) + " }";
    }
}
