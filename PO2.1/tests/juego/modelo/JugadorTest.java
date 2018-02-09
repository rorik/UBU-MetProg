package juego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.modelo.JugadorTest] Created by Roderick D. on 2017/10/28.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class JugadorTest {
    @Test
    void constructorNull() {
        assertThrows(AssertionError.class, () -> new Jugador("", null));
    }

    @Test
    void obtenerColor() {
        assertEquals(Color.NEGRO, new Jugador("", Color.NEGRO).obtenerColor());
        assertEquals(Color.BLANCO, new Jugador("", Color.BLANCO).obtenerColor());
    }

    @Test
    void obtenerNombre() {
        assertEquals("A !ñ_<áø;'\"\\/", new Jugador("A !ñ_<áø;'\"\\/", Color.BLANCO).obtenerNombre());
    }

    @Test
    void generarPiedra() {
        Jugador jugador = new Jugador("", Color.NEGRO);
        Piedra piedra = jugador.generarPiedra();
        assertNotNull(piedra);
        assertEquals(Color.NEGRO, piedra.obtenerColor());
    }

    @Test
    void testToString() {
        assertEquals("Abc/BLANCO", new Jugador("Abc", Color.BLANCO).toString());
    }

}