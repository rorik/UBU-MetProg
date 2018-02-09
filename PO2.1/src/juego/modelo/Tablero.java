package juego.modelo;

import juego.util.CoordenadasIncorrectasException;
import juego.util.Sentido;

import java.util.ArrayList;
import java.util.List;

/**
 * Tablero de juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 2.1
 */
public class Tablero {
    private final List<List<Celda>> celdas = new ArrayList<>();
    private final List<Grupo> grupos = new ArrayList<>();

    private int piedrasCapturadasNegras = 0;
    private int piedrasCapturadasBlancas = 0;

    /**
     * Constructor, itera por cada Celda y la inicializa.
     *
     * @param filas    número de filas
     * @param columnas número columnas
     */
    public Tablero(int filas, int columnas) {
        assert filas > 0 && columnas > 0;
        for (int i = 0; i < filas; i++) {
            List<Celda> fila = new ArrayList<>();
            for (int j = 0; j < columnas; j++) {
                fila.add(new Celda(i, j));
            }
            celdas.add(fila);
        }
    }

    /**
     * Enlaza una piedra a una columna.
     *
     * @param piedra Piedra a colocar.
     * @param celda  Celda donde va a ser colocada.
     * @throws CoordenadasIncorrectasException en caso de que se coloque fuera del tablero.
     */
    public void colocar(Piedra piedra, Celda celda) throws CoordenadasIncorrectasException {
        celda = obtenerCeldaConMismasCoordenadas(celda);
        List<Grupo> gruposEliminados = new ArrayList<>();
        List<Grupo> gruposAnterioresBlanco = obtenerGruposDelColor(Color.BLANCO);
        List<Grupo> gruposAnterioresNegro = obtenerGruposDelColor(Color.NEGRO);
        piedra.colocarEn(celda);
        celda.establecerPiedra(piedra);
        Grupo nuevo = new Grupo(celda, this);
        for (Celda adyacente : obtenerCeldasAdyacentes(celda)) {
            if (adyacente.obtenerColorDePiedra() == celda.obtenerColorDePiedra()) {
                for (Grupo grupo : grupos) {
                    if (grupo.contiene(adyacente)) {
                        nuevo.añadirCeldas(grupo);
                        gruposEliminados.add(grupo);
                    }
                }
            }
        }
        grupos.removeAll(gruposEliminados);
        grupos.add(nuevo);
        if (piedra.obtenerColor() == Color.BLANCO) {
            gruposAnterioresBlanco.add(nuevo);
            gruposAnterioresBlanco.removeAll(gruposEliminados);
        } else {
            gruposAnterioresNegro.add(nuevo);
            gruposAnterioresNegro.removeAll(gruposEliminados);
        }
        eliminarCapturas(piedra.obtenerColor(), gruposAnterioresBlanco, gruposAnterioresNegro);
    }

    /**
     * Elimina todas las capturas que hayan ocurrido, según el color del jugador que realice la jugada.
     *
     * @param colorConPrioridad Color del la piedra colocada.
     * @param gruposAnterioresBlanco Grupos de color blanco que había antes de colocar.
     * @param gruposAnterioresNegro Grupos de color negro que había antes de colocar.
     */
    private void eliminarCapturas(Color colorConPrioridad, List<Grupo> gruposAnterioresBlanco, List<Grupo> gruposAnterioresNegro){
        Color colorSinPrioridad;
        List<Grupo> grupoConPrioridad;
        List<Grupo> grupoSinPrioridad;
        gruposAnterioresBlanco.removeAll(obtenerGruposDelColor(Color.BLANCO));
        gruposAnterioresNegro.removeAll(obtenerGruposDelColor(Color.NEGRO));
        colorSinPrioridad = colorConPrioridad == Color.BLANCO ? Color.NEGRO : Color.BLANCO;
        if (colorConPrioridad == Color.NEGRO) {
            grupoConPrioridad = gruposAnterioresNegro;
            grupoSinPrioridad = gruposAnterioresBlanco;
        }
        else {
            grupoConPrioridad = gruposAnterioresBlanco;
            grupoSinPrioridad = gruposAnterioresNegro;
        }
        if (eliminarColor(grupoSinPrioridad, colorSinPrioridad) == 0 && ! estaCompleto()) {
            eliminarColor(grupoConPrioridad, colorConPrioridad);
        }
    }

    /**
     * Elimina todas las piedras de una lista de grupos.
     *
     * @param gruposEliminados Lista de grupos a vaciar.
     * @param color Color de las piedras.
     * @return Número de piedras eliminadas.
     */
    private int eliminarColor(List<Grupo> gruposEliminados, Color color) {
        int eliminadas = 0;
        for (Grupo grupo : gruposEliminados) {
            eliminadas += grupo.obtenerTamaño();
            grupo.eliminarPiedras();
            grupos.remove(grupo);
        }
        if (color == Color.BLANCO) {
            piedrasCapturadasBlancas += eliminadas;
        }
        else {
            piedrasCapturadasNegras += eliminadas;
        }
        return eliminadas;
    }

    /**
     * Obtiene la celda que se encuentra en una determinada posición.
     *
     * @param fila    Fila de la celda.
     * @param columna Columna de la celda.
     * @return devuelve la celda que se encuentre en la posición (fila, columna).
     * @throws CoordenadasIncorrectasException en caso de que no exista celda con esas coordenadas.
     */
    public Celda obtenerCelda(int fila, int columna) throws CoordenadasIncorrectasException {
        if (!estaEnTablero(fila, columna)) {
            throw new CoordenadasIncorrectasException("No existe una celda con coordenadas " + fila + ", " + columna);
        }
        return celdas.get(fila).get(columna);
    }

    /**
     * Obtiene la celda que se tenga las mismas coordenadas que otra celda.
     *
     * @param celda celda de la cual conocemos las coordenadas.
     * @return devuelve la celda que se encuentre en la posición (fila, columna).
     * @throws CoordenadasIncorrectasException en caso de que no exista celda con esas coordenadas.
     */
    public Celda obtenerCeldaConMismasCoordenadas(Celda celda) throws CoordenadasIncorrectasException {
        return obtenerCelda(celda.obtenerFila(), celda.obtenerColumna());
    }

    /**
     * Calcula si una posición está dentro del tablero.
     *
     * @param celda Celda a ser comprobada.
     * @return <code>true</code> en caso de que se encuentre dentro,
     * <code>false</code> en el caso contrario.
     */
    public boolean estaEnTablero(Celda celda) {
        return estaEnTablero(celda.obtenerFila(), celda.obtenerColumna());
    }

    /**
     * Calcula si una posición está dentro del tablero.
     *
     * @param fila Fila de la posición a ser comprobada,
     *             debe ser mayor o igual a 0 y menor que el número de filas.
     * @param columna Columna de la posición a ser comprobada,
     *             debe ser mayor o igual a 0 y menor que el número de filas.
     * @return <code>true</code> en caso de que se encuentre dentro,
     * <code>false</code> en el caso contrario.
     */
    private boolean estaEnTablero(int fila, int columna) {
        return fila >= 0 && fila < obtenerNumeroFilas() && columna >= 0 && columna < obtenerNumeroColumnas();
    }

    /**
     * Recorre toda el tablero y cuenta el número de piedras de un determinado color.
     *
     * @param color Color de las piedras a buscar.
     * @return número de piedras encontradas.
     */
    public int obtenerNumeroPiedras(Color color) {
        int cuenta = 0;
        for (int i = 0; i < obtenerNumeroFilas(); i++) {
            for (int j = 0; j < obtenerNumeroColumnas(); j++) {
                if (!obtenerCelda(i, j).estaVacia() && obtenerCelda(i, j).obtenerColorDePiedra() == color) {
                    cuenta++;
                }
            }
        }
        return cuenta;
    }

    /**
     * Devuelve el número de filas.
     *
     * @return número de filas.
     */
    public int obtenerNumeroFilas() {
        return this.celdas.size();
    }

    /**
     * Devuelve el número de columnas.
     *
     * @return número de columnas.
     */
    public int obtenerNumeroColumnas() {
        return this.celdas.get(0).size();
    }

    /**
     * Comprueba si toda el tablero está lleno de piedras.
     *
     * @return <code>true</code> si esta completo, <code>false</code> en caso contrario.
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
     * Obtiene el grupo de celdas adyacentes.
     *
     * @param celda Celda a la que se obtendrá sus adyacentes.
     * @return Grupo de celdas adyacentes.
     * @throws CoordenadasIncorrectasException en caso de que no exista celda con esas coordenadas.
     */
    public List<Celda> obtenerCeldasAdyacentes(Celda celda) throws CoordenadasIncorrectasException {
        celda = obtenerCeldaConMismasCoordenadas(celda);
        List<Celda> celdas = new ArrayList<>();
        for (Sentido sentido : Sentido.values()) {
            int fila = celda.obtenerFila() + sentido.obtenerDesplazamientoFila();
            int columna = celda.obtenerColumna() + sentido.obtenerDesplazamientoColumna();
            if (estaEnTablero(fila, columna)) {
                celdas.add(obtenerCelda(fila, columna));
            }
        }
        return celdas;
    }

    /**
     * Obtiene los grados de libertad de una determinada celda.
     *
     * @param celda Celda a ser comprobada.
     * @return Grados de libertad de la celda.
     * @throws CoordenadasIncorrectasException en caso de que no exista celda con esas coordenadas.
     */
    public int obtenerGradosDeLibertad(Celda celda) throws CoordenadasIncorrectasException {
        int gradosDeLibertad = 0;
        for (Celda celdasAdyacente : obtenerCeldasAdyacentes(celda)) {
            if (celdasAdyacente.estaVacia()) {
                gradosDeLibertad++;
            }
        }
        return gradosDeLibertad;
    }

    /**
     * Crea una copia del tablero actual.
     *
     * @return Copia.
     */
    public Tablero generarCopia() {
        Tablero copia = new Tablero(obtenerNumeroFilas(), obtenerNumeroColumnas());
        copia.piedrasCapturadasNegras = this.piedrasCapturadasNegras;
        copia.piedrasCapturadasBlancas = this.piedrasCapturadasBlancas;
        for (Grupo grupo : grupos) {
            if (grupo.estaVivo()) {
                grupo.generarCopiaEnOtroTablero(copia);
            }
        }
        return copia;
    }

    /**
     * Devuelve todos los grupos del jugador.
     *
     * @param jugador Jugador a ser consultados.
     * @return Lista de grupos que pertenecen al jugador.
     */
    public ArrayList<Grupo> obtenerGruposDelJugador(Jugador jugador) {
        return obtenerGruposDelColor(jugador.obtenerColor());
    }

    /**
     * Devuelve todos los grupos que tengan un determinado color.
     *
     * @param color Color del grupo.
     * @return Lista de grupos con ese color.
     */
    private ArrayList<Grupo> obtenerGruposDelColor(Color color) {
        ArrayList<Grupo> gruposVivos = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.obtenerColor() == color) {
                gruposVivos.add(grupo);
            }
        }
        return gruposVivos;
    }

    /**
     * Información del objeto en String.
     *
     * @return String del objeto.
     */
    public String toString() {
        return "Tablero{ tamaño=(" + obtenerNumeroFilas() + "x" + obtenerNumeroColumnas() + "), piedrasBlancas=" +
                obtenerNumeroPiedras(Color.BLANCO) + ", piedrasNegras=" + obtenerNumeroPiedras(Color.NEGRO) + " }";
    }

    /**
     * Obtiene la cantidad de piedras capturadas de un determinado color
     * a causa de la piedra colocada.
     *
     * @param color Color a comprobar.
     * @return Numero de piedras de ese color que han sido conquistadas.
     */
    public int obtenerNumeroPiedrasCapturadas(Color color) {
        return (color == Color.NEGRO) ? piedrasCapturadasNegras : piedrasCapturadasBlancas;
    }

    /**
     * Calcula si este tablero es igual a otro o no.
     *
     * @param tablero Tableroç} a comparar.
     * @return <code>true</code> si son iguales, <code>false</code> en caso contrario.
     */
    public boolean esIgual(Tablero tablero) {
        if (obtenerNumeroFilas() != tablero.obtenerNumeroFilas()
                || obtenerNumeroColumnas() != tablero.obtenerNumeroColumnas()) {
            return false;
        }
        for (int i = 0; i < obtenerNumeroFilas(); i++) {
            for (int j = 0; j < obtenerNumeroColumnas(); j++) {
                if (obtenerCelda(i, j).obtenerColorDePiedra() != tablero.obtenerCelda(i, j).obtenerColorDePiedra()) {
                    return false;
                }
            }
        }
        return true;
    }
}
