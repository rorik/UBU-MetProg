package juego.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.util.SentidoTest] Created by Roderick D. on 2017/11/16.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class SentidoTest {
    @Test
    void obtenerDesplazamientoHorizontal() {
        assertEquals(0, Sentido.NORTE.obtenerDesplazamientoHorizontal());
        assertEquals(0, Sentido.SUR.obtenerDesplazamientoHorizontal());
        assertEquals(1, Sentido.ESTE.obtenerDesplazamientoHorizontal());
        assertEquals(-1, Sentido.OESTE.obtenerDesplazamientoHorizontal());
    }

    @Test
    void obtenerDesplazamientoVertical() {
        assertEquals(-1, Sentido.NORTE.obtenerDesplazamientoVertical());
        assertEquals(1, Sentido.SUR.obtenerDesplazamientoVertical());
        assertEquals(0, Sentido.ESTE.obtenerDesplazamientoVertical());
        assertEquals(0, Sentido.OESTE.obtenerDesplazamientoVertical());
    }

}