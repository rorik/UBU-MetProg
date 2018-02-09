package juego.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.modelo.ColorTest] Created by Roderick D. on 2017/10/28.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class ColorTest {
    @Test
    void toChar() {
        assertEquals('O', Color.BLANCO.toChar());
        assertEquals('X', Color.NEGRO.toChar());
    }

}