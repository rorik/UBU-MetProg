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
        assertEquals(0, Sentido.ARRIBA.obtenerDesplazamientoColumna());
        assertEquals(0, Sentido.ABAJO.obtenerDesplazamientoColumna());
        assertEquals(1, Sentido.DERECHA.obtenerDesplazamientoColumna());
        assertEquals(-1, Sentido.IZQUIERDA.obtenerDesplazamientoColumna());
    }

    @Test
    void obtenerDesplazamientoVertical() {
        assertEquals(-1, Sentido.ARRIBA.obtenerDesplazamientoFila());
        assertEquals(1, Sentido.ABAJO.obtenerDesplazamientoFila());
        assertEquals(0, Sentido.DERECHA.obtenerDesplazamientoFila());
        assertEquals(0, Sentido.IZQUIERDA.obtenerDesplazamientoFila());
    }

}