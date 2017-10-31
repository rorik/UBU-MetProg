package juego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.modelo.CeldaTest] Created by Roderick D. on 2017/10/28.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class CeldaTest {
    @Test
    void constructorNegativo() {
        assertThrows(AssertionError.class, () -> new Celda(-1, 1));
        assertThrows(AssertionError.class, () -> new Celda(1, -1));
        assertThrows(AssertionError.class, () -> new Celda(-8, -9));
    }

    @Test
    void obtenerPiedra() {
        Celda celda = new Celda(10, 10);
        Piedra piedra = new Piedra(Color.NEGRO);
        assertNotEquals(piedra, celda.obtenerPiedra());
        celda.establecerPiedra(piedra);
        assertEquals(piedra, celda.obtenerPiedra());
    }

    @Test
    void obtenerColorDePiedra() {
        Celda celda = new Celda(10, 10);
        celda.establecerPiedra(new Piedra(Color.BLANCO));
        assertEquals(Color.BLANCO, celda.obtenerPiedra().obtenerColor());
        celda.establecerPiedra(new Piedra(Color.NEGRO));
        assertEquals(Color.NEGRO, celda.obtenerPiedra().obtenerColor());
    }

    @Test
    void obtenerColorDePiedraNull() {
        Celda celda = new Celda(10, 10);
        assertThrows(AssertionError.class, celda::obtenerColorDePiedra);
    }

    @Test
    void establecerPiedra() {
        Celda celda = new Celda(6, 0);
        assertNull(celda.obtenerPiedra());
        celda.establecerPiedra(new Piedra(Color.BLANCO));
        assertNotNull(celda.obtenerPiedra());
    }

    @Test
    void estaVacia() {
        Celda celda = new Celda(4, 4);
        assertTrue(celda.estaVacia());
        celda.establecerPiedra(new Piedra(Color.NEGRO));
        assertFalse(celda.estaVacia());
    }

    @Test
    void obtenerFila() {
        assertEquals(3, new Celda(3, 6).obtenerFila());
    }

    @Test
    void obtenerColumna() {
        assertEquals(6, new Celda(3, 6).obtenerColumna());
    }

    @Test
    void tieneIgualesCoordenadas() {
        Celda celda1 = new Celda(0, 0);
        Celda celda2 = new Celda(1, 2);
        Celda celda3 = new Celda(1, 2);
        assertFalse(celda1.tieneIgualesCoordenadas(celda2));
        assertFalse(celda1.tieneIgualesCoordenadas(celda3));
        assertFalse(celda2.tieneIgualesCoordenadas(celda1));
        assertFalse(celda3.tieneIgualesCoordenadas(celda1));
        assertTrue(celda2.tieneIgualesCoordenadas(celda3));
        assertTrue(celda3.tieneIgualesCoordenadas(celda2));
    }

    @Test
    void eliminarPiedra() {
        Celda celda = new Celda(0, 0);
        celda.establecerPiedra(new Piedra(Color.BLANCO));
        assertNotNull(celda.obtenerPiedra());
        celda.eliminarPiedra();
        assertNull(celda.obtenerPiedra());
    }

    @Test
    void testToString() {
        assertEquals("(3 / 5)", new Celda(3, 5).toString());
    }

}