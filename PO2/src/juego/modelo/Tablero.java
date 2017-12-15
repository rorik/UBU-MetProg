package juego.modelo;

import juego.util.CoordenadasIncorrectasException;
import juego.util.Sentido;

import java.util.ArrayList;
import java.util.List;

/**
 * Tablero de juego.
 *
 * @author <A HREF="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz</A>
 * @version 1.0
 */
public class Tablero {
    private final List<List<Celda>> celdas;
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
        celdas = new ArrayList<>();
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
     * @param piedra Piedra
     * @param celda  Celda
     */
    public void colocar(Piedra piedra, Celda celda) throws CoordenadasIncorrectasException {
        List<Grupo> copiaGruposNegro = obtenerGruposDelColor(Color.NEGRO);
        List<Grupo> copiaGruposBlanco = obtenerGruposDelColor(Color.BLANCO);
        Celda celdaEquivalente = obtenerCeldaConMismasCoordenadas(celda);
        celdaEquivalente.establecerPiedra(piedra);
        piedra.colocarEn(celdaEquivalente);
        fusionarGruposAdyacentes(celdaEquivalente, piedra.obtenerColor() == Color.NEGRO ? copiaGruposNegro : copiaGruposBlanco);
        comprobarConquistas(copiaGruposNegro, copiaGruposBlanco, piedra.obtenerColor());

    }

    /**
     * Comprueba si se ha realizado una conquista en el tablero,
     * esta acción no ocurre en el caso de que solo exista un grupo en el tablero,
     * es decir, que todas las celdas estén ocupadas por un solo jugador.
     *
     * @param gruposNegro Grupos del jugador negro vivos antes de colocar la piedra.
     * @param gruposBlanco Grupos del jugador blanco vivos antes de colocar la piedra.
     * @param colorJugador Color del jugador que realiza la jugada.
     */
    private void comprobarConquistas(List<Grupo> gruposNegro, List<Grupo> gruposBlanco, Color colorJugador) {
        piedrasCapturadasNegras = 0;
        piedrasCapturadasBlancas = 0;
        if (!estaCompleto() || gruposNegro.size() * gruposBlanco.size() > 0) {
            gruposNegro.removeAll(obtenerGruposDelColor(Color.NEGRO));
            gruposBlanco.removeAll(obtenerGruposDelColor(Color.BLANCO));
            if (!(colorJugador == Color.NEGRO && gruposBlanco.size() > 0)) {
                for (Grupo muerto : gruposNegro) {
                    muerto.eliminarPiedras();
                    piedrasCapturadasNegras += muerto.obtenerTamaño();
                }
            }
            if (!(colorJugador == Color.BLANCO && gruposNegro.size() > 0)) {
                for (Grupo muerto : gruposBlanco) {
                    muerto.eliminarPiedras();
                    piedrasCapturadasBlancas += muerto.obtenerTamaño();
                }
            }
        }
    }

    /**
     * Añade una celda a un nuevo grupo y comprueba si hay grupos adyacentes,
     * en caso de que haya, agrupa todos en uno solo.
     *
     * @param celda Celda a meter en grupo y fusionar.
     * @param gruposCopia Lista con los grupos del mismo color que el nuevo grupo
     *                    que estaban vivos antes de realizar la jugada.
     * @return Devuelve <code>null</code> en caso de fusionarse con otro grupo,
     * en caso contrario, devuelve el grupo recién creado.
     */
    private Grupo fusionarGruposAdyacentes(Celda celda, List<Grupo> gruposCopia) {
        Grupo nuevoGrupo = new Grupo(celda, this);
        List<Grupo> gruposAdyacentes = obtenerGruposAdyacentes(celda);
        if (gruposAdyacentes.size() > 0) {
            gruposAdyacentes.get(0).añadirCeldas(nuevoGrupo);
            for (int i = 1; i < gruposAdyacentes.size(); i++) {
                gruposAdyacentes.get(0).añadirCeldas(gruposAdyacentes.get(i));
                grupos.remove(gruposAdyacentes.get(i));
                gruposCopia.remove(gruposAdyacentes.get(i));
            }
            return null;
        }
        grupos.add(nuevoGrupo);
        gruposCopia.add(nuevoGrupo);
        return nuevoGrupo;
    }

    /**
     * Obtiene los grupos del mismo jugador que contienen
     * celdas adyacentes a la celda dada.
     *
     * @param celda Celda a la que obtener grupos adyacentes.
     * @return Lista de grupos adyacentes del mismo color.
     */
    private List<Grupo> obtenerGruposAdyacentes(Celda celda) {
        List<Celda> adyacentesDelMismoColor = obtenerCeldasAdyacentesDelMismoColor(celda);
        List<Grupo> gruposAdyacentes = new ArrayList<>();
        for (Celda celdaAdyacente : adyacentesDelMismoColor) {
            for (Grupo grupo : grupos) {
                if (grupo.contiene(celdaAdyacente) && !gruposAdyacentes.contains(grupo)) {
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
    private List<Celda> obtenerCeldasAdyacentesDelMismoColor(Celda celda) {
        List<Celda> adyacentesDelMismoColor = new ArrayList<>();
        for (Celda celdaAdyacente : obtenerCeldasAdyacentes(celda)) {
            if (!celdaAdyacente.estaVacia() && celdaAdyacente.obtenerColorDePiedra() == celda.obtenerColorDePiedra()) {
                adyacentesDelMismoColor.add(celdaAdyacente);
            }
        }
        return adyacentesDelMismoColor;
    }

    /**
     * Obtiene la celda que se encuentra en una determinada posición.
     *
     * @param fila    Fila de la celda.
     * @param columna Columna de la celda.
     * @return devuelve la celda que se encuentre en la posición (fila, columna), o
     * <code>null</code> en caso de que se salga de los límites.
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
     * @return devuelve la celda que se encuentre en la posición (fila, columna), o
     * <code>null</code> en caso de que se salga de los límites.
     */
    public Celda obtenerCeldaConMismasCoordenadas(Celda celda) throws CoordenadasIncorrectasException {
        if (estaEnTablero(celda)) {
            return obtenerCelda(celda.obtenerFila(), celda.obtenerColumna());
        }
        return null;
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
     */
    public List<Celda> obtenerCeldasAdyacentes(Celda celda) throws CoordenadasIncorrectasException {
        List<Celda> celdas = new ArrayList<>();
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
     * Obtiene los grados de libertad de una determinada celda.
     *
     * @param celda Celda a ser comprobada.
     * @return Grados de libertad de la celda.
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

    public int obtenerNumeroPiedrasCapturadas(Color color) {
        return (color == Color.NEGRO) ? piedrasCapturadasNegras : piedrasCapturadasBlancas;
    }
}
