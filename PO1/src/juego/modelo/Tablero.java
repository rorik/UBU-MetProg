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
    private Celda[][] matriz;
    private final ArrayList grupos = new ArrayList();

    /**
     * Constructor, itera por cada Celda y la inicializa.
     *
     * @param filas    número de filas
     * @param columnas número columnas
     */
    public Tablero(int filas, int columnas) {
        assert filas > 0 && columnas > 0;
        matriz = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                matriz[i][j] = new Celda(i, j);
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
        Grupo nuevoGrupo = new Grupo(celda, this);
        grupos.add(nuevoGrupo);
        ArrayList adyacentesDelMismoColor = new ArrayList();
        for (Object celdaAdyacente : obtenerCeldasAdyacentes(celda)) {
            if (!((Celda) celdaAdyacente).estaVacia()
                    && ((Celda) celdaAdyacente).obtenerColorDePiedra() == celda.obtenerColorDePiedra())
                adyacentesDelMismoColor.add(celdaAdyacente);
        }
        ArrayList gruposAdyacentes = new ArrayList();
        for (Object celdaAdyacente : adyacentesDelMismoColor) {
            for (Object grupo : grupos) {
                if (((Grupo) grupo).obtenerColor() == ((Celda) celdaAdyacente).obtenerColorDePiedra() &&
                        ((Grupo) grupo).contiene((Celda) celdaAdyacente) &&
                        !gruposAdyacentes.contains(grupo))
                    gruposAdyacentes.add(grupo);
            }
        }
        if (gruposAdyacentes.size() > 0) {
            ((Grupo) gruposAdyacentes.get(0)).añadirCeldas(nuevoGrupo);
            grupos.remove(nuevoGrupo);
            for (int i = 1; i < gruposAdyacentes.size(); i++) {
                ((Grupo) gruposAdyacentes.get(0)).añadirCeldas((Grupo) gruposAdyacentes.get(i));
                grupos.remove(gruposAdyacentes.get(i));
            }
        }
        //grupos.stream().filter(Grupo::estaVivo).iterator().forEachRemaining(grupo -> System.out.println(grupo.obtenerId() + " (" + grupo.obtenerColor().toChar() + "): " + grupo.obtenerTamaño()));
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
        if (estaEnTablero(fila, columna))
            return matriz[fila][columna];
        return null;
    }

    /**
     * Obtiene la celda que se tenga las mismas coordenadas que otra celda.
     *
     * @param celda celda de la cual conocemos las coordenadas
     * @return devuelve la celda que se encuentre en la posición (fila, columna), o
     * null en caso de que se salga de los límites
     */
    public Celda obtenerCeldaConMismasCoordenadas(Celda celda) {
        if (estaEnTablero(celda.obtenerFila(), celda.obtenerColumna()))
            return matriz[celda.obtenerFila()][celda.obtenerColumna()];
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
        for (int i = 0; i < obtenerNumeroFilas(); i++)
            for (int j = 0; j < obtenerNumeroColumnas(); j++)
                if (!obtenerCelda(i, j).estaVacia() && matriz[i][j].obtenerPiedra().obtenerColor() == color)
                    cuenta++;
        return cuenta;
    }

    /**
     * Devuelve el número de filas.
     *
     * @return número de filas
     */
    public int obtenerNumeroFilas() {
        return this.matriz.length;
    }

    /**
     * Devuelve el número de columnas.
     *
     * @return número de columnas
     */
    public int obtenerNumeroColumnas() {
        return this.matriz[0].length;
    }

    /**
     * Comprueba si toda el tablero está lleno de piedras.
     *
     * @return verdadero si esta completo, falso en caso contrario
     */
    public boolean estaCompleto() {
        for (int i = 0; i < obtenerNumeroFilas(); i++)
            for (int j = 0; j < obtenerNumeroColumnas(); j++)
                if (obtenerCelda(i, j).estaVacia())
                    return false;
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
            if (estaEnTablero(fila, columna)) celdas.add(obtenerCelda(fila, columna));
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
            if (((Celda) celdasAdyacente).estaVacia())
                gradosDeLibertad++;
        }
        return gradosDeLibertad;
    }

    /**
     * Crea una copia del tablero actual
     *
     * @return Copia
     */
    public Tablero generarCopia() {
        Tablero tablero = new Tablero(obtenerNumeroFilas(), obtenerNumeroColumnas());
        for (Object grupo : grupos)
            ((Grupo) grupo).generarCopiaEnOtroTablero(tablero);
        return tablero;
        // TODO @BUG @PR=7 @FWMBR method generarCopia() doesn't duplicate groups.
    }

    /**
     * Devuelve todos los grupos del jugador
     *
     * @param jugador Jugador a ser consultados
     * @return Lista de listas de celdas que pertenecen a un mismo grupo.
     */
    public ArrayList obtenerGruposDelJugador(Jugador jugador) {
        ArrayList gruposDelJugador = new ArrayList();
        for (Object grupo : grupos)
            if (((Grupo) grupo).obtenerColor() == jugador.obtenerColor())
                gruposDelJugador.add(grupo);
        return gruposDelJugador;
    }


    /**
     * Información del objeto en String
     *
     * @return String del objeto
     */
    public String toString() {
        String salida = "Tablero: \n";
        for (int i = 0; i < obtenerNumeroFilas(); i++) {
            for (int j = 0; j < obtenerNumeroColumnas(); j++) {
                Celda celda = obtenerCelda(i, j);
                if (celda.estaVacia())
                    salida = salida.concat("-");
                else
                    salida = salida.concat(String.valueOf(celda.obtenerPiedra().obtenerColor().toChar()));
            }
            salida = salida.concat("\n");
        }
        return salida;
    }
}
