package juego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.modelo.PiedraTest] Created by Roderick D. on 2017/10/28.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class PiedraTest {
    @Test
    void constructorNull() {
        assertThrows(AssertionError.class, () -> new Piedra(null));
    }

    @Test
    void obtenerColor() {
        assertEquals(Color.NEGRO, new Piedra(Color.NEGRO).obtenerColor());
        assertEquals(Color.BLANCO, new Piedra(Color.BLANCO).obtenerColor());
    }

    @Test
    void colocarEn() {
        Piedra piedra = new Piedra(Color.NEGRO);
        Celda celda = new Celda(0, 0);
        piedra.colocarEn(celda);
        assertEquals(celda, piedra.obtenerCelda());
    }

    @Test
    void obtenerCelda() {
        Piedra piedra = new Piedra(Color.BLANCO);
        assertNull(piedra.obtenerCelda());
        piedra.colocarEn(new Celda(0, 0));
        assertNotNull(piedra.obtenerCelda());
    }

    @Test
    void testToString() {
        assertEquals("null/NEGRO", new Piedra(Color.NEGRO).toString());
        Piedra piedra = new Piedra(Color.BLANCO);
        piedra.colocarEn(new Celda(1, 2));
        assertEquals("(1 / 2)/BLANCO", piedra.toString());
    }

}