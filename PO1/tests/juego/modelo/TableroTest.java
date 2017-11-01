package juego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.modelo.TableroTest] Created by Roderick D. on 2017/10/28.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class TableroTest {
    @Test
    void constructorNull() {
        assertThrows(AssertionError.class, () -> new Tablero(0, 1));
        assertThrows(AssertionError.class, () -> new Tablero(1, 0));
        assertThrows(AssertionError.class, () -> new Tablero(-8, -9));
    }

    @Test
    void colocar() {
        Celda celda = new Celda(0, 0);
        Piedra piedra = new Piedra(Color.NEGRO);
        new Tablero(1, 1).colocar(piedra, celda);
        assertEquals(celda, piedra.obtenerCelda());
        assertEquals(piedra, celda.obtenerPiedra());
    }

    @Test
    void obtenerCelda() {
        Tablero tablero = new Tablero(1, 1);
        assertNotNull(tablero.obtenerCelda(0, 0));
        assertNull(tablero.obtenerCelda(1, 0));
    }

    @Test
    void obtenerCeldaConMismasCoordenadas() {
        Tablero tablero = new Tablero(2, 2);
        assertNotNull(tablero.obtenerCeldaConMismasCoordenadas(new Celda(0, 1)));
        assertNull(tablero.obtenerCeldaConMismasCoordenadas(new Celda(1, 2)));
    }

    @Test
    void estaEnTablero() {
        Tablero tablero = new Tablero(2, 2);
        assertTrue(tablero.estaEnTablero(0, 0));
        assertTrue(tablero.estaEnTablero(1, 1));
        assertFalse(tablero.estaEnTablero(2, 2));
    }

    @Test
    void obtenerNumeroPiedras() {
        Tablero tablero = new Tablero(2, 2);
        assertEquals(0, tablero.obtenerNumeroPiedras(Color.NEGRO));
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0, 0));
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0, 1));
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(1, 0));
        tablero.colocar(new Piedra(Color.BLANCO), tablero.obtenerCelda(1, 1));
        assertEquals(3, tablero.obtenerNumeroPiedras(Color.NEGRO));
        assertEquals(1, tablero.obtenerNumeroPiedras(Color.BLANCO));
    }

    @Test
    void obtenerNumeroFilas() {
        assertEquals(3, new Tablero(3, 1).obtenerNumeroFilas());
    }

    @Test
    void obtenerNumeroColumnas() {
        assertEquals(3, new Tablero(1, 3).obtenerNumeroColumnas());
    }

    @Test
    void estaCompleto() {
        Tablero tablero = new Tablero(1, 2);
        assertFalse(tablero.estaCompleto());
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0, 1));
        assertFalse(tablero.estaCompleto());
        tablero.colocar(new Piedra(Color.BLANCO), tablero.obtenerCelda(0, 0));
        assertTrue(tablero.estaCompleto());
    }

    @Test
    void obtenerCeldasAdyacentes() {
        Tablero tablero = new Tablero(3, 3);
        assertEquals(4, tablero.obtenerCeldasAdyacentes(tablero.obtenerCelda(1, 1)).size());
        assertEquals(3, tablero.obtenerCeldasAdyacentes(tablero.obtenerCelda(1, 0)).size());
        assertEquals(2, tablero.obtenerCeldasAdyacentes(tablero.obtenerCelda(2, 2)).size());
        Piedra[] piedras = new Piedra[]{new Piedra(Color.NEGRO), new Piedra(Color.BLANCO)};
        tablero.colocar(piedras[0], tablero.obtenerCelda(1, 1));
        tablero.colocar(piedras[1], tablero.obtenerCelda(1, 2));
        assertEquals(piedras[1], ((Celda) tablero.obtenerCeldasAdyacentes(piedras[0].obtenerCelda()).get(2)).obtenerPiedra());
        assertNull(((Celda) tablero.obtenerCeldasAdyacentes(piedras[0].obtenerCelda()).get(0)).obtenerPiedra());
        assertNull(((Celda) tablero.obtenerCeldasAdyacentes(piedras[0].obtenerCelda()).get(1)).obtenerPiedra());
        assertNull(((Celda) tablero.obtenerCeldasAdyacentes(piedras[0].obtenerCelda()).get(3)).obtenerPiedra());
    }

    @Test
    void obtenerGradosDeLibertad() {
        Tablero tablero = new Tablero(3, 3);
        assertEquals(4, tablero.obtenerGradosDeLibertad(tablero.obtenerCelda(1, 1)));
        tablero.colocar(new Piedra(Color.BLANCO), tablero.obtenerCelda(1, 0));
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0, 1));
        assertEquals(2, tablero.obtenerGradosDeLibertad(tablero.obtenerCelda(1, 1)));
        assertEquals(0, tablero.obtenerGradosDeLibertad(tablero.obtenerCelda(0, 0)));
    }

    @Test
    void generarCopia() {
        Tablero tablero = new Tablero(2,5);
        Piedra piedra = new Piedra(Color.BLANCO);
        tablero.colocar(piedra, tablero.obtenerCelda(1,3));
        Tablero copia = tablero.generarCopia();
        assertEquals(2, copia.obtenerNumeroFilas());
        assertEquals(5, copia.obtenerNumeroColumnas());
        assertNotEquals(piedra, copia.obtenerCelda(1,3).obtenerPiedra());
        assertEquals(piedra.obtenerColor(), copia.obtenerCelda(1,3).obtenerPiedra().obtenerColor());
        assertNull(copia.obtenerCelda(0,0).obtenerPiedra());
        assertEquals(tablero.obtenerNumeroPiedras(Color.BLANCO), copia.obtenerNumeroPiedras(Color.BLANCO));
    }

    @Test
    void obtenerGruposDelJugador() {
        Tablero tablero = new Tablero(3, 3);
        Jugador jugadorBlanco = new Jugador("b", Color.BLANCO);
        Jugador jugadorNegro = new Jugador("b", Color.NEGRO);
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(0, 0));
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(1, 0));
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(2, 0));
        tablero.colocar(jugadorNegro.generarPiedra(), tablero.obtenerCelda(2, 1));
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(0, 2));
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(2, 2));
        assertEquals(3, tablero.obtenerGruposDelJugador(jugadorBlanco).size());
        assertEquals(1, tablero.obtenerGruposDelJugador(jugadorNegro).size());
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(1, 2));
        assertEquals(2, tablero.obtenerGruposDelJugador(jugadorBlanco).size());
        tablero.colocar(jugadorBlanco.generarPiedra(), tablero.obtenerCelda(0, 1));
        assertEquals(1, tablero.obtenerGruposDelJugador(jugadorBlanco).size());
    }

}