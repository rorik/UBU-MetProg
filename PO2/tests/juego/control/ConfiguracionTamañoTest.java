package juego.control;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [tests.juego.control.ConfiguracionTamañoTest] Created by Roderick D. on 2017/10/28.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class ConfiguracionTamañoTest {
    @Test
    void obtenerTamañoMaximo() {
        assertEquals(19, ConfiguracionAtariGo.obtenerTamañoMaximo());
    }

    @Test
    void esTamañoValido() {
        assertTrue(ConfiguracionAtariGo.esTamañoValido(13));
        assertFalse(ConfiguracionAtariGo.esTamañoValido(14));
    }

    @Test
    void testToString() {
        assertEquals(" 9, 13, 19.", ConfiguracionAtariGo.generarAyuda());
    }

}