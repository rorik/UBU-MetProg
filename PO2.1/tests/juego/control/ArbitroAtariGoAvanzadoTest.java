package juego.control;

import juego.modelo.Celda;
import juego.modelo.Tablero;
import juego.textui.JuegoTest;
import juego.util.CoordenadasIncorrectasException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.control.ArbitroAtariGoAvanzadoTest] Created by Roderick D. on 2017/12/15.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class ArbitroAtariGoAvanzadoTest {

    @Test
    void juegoCota2() {
        assertTrue(jugada1(2).estaAcabado());
    }

    @Test
    void juegoCota3() {
        ArbitroAtariGoAvanzado arbitro = jugada1(3);
        jugada2(arbitro);
        assertTrue(arbitro.estaAcabado());
    }

    @Test
    void juegoCota8() {
        ArbitroAtariGoAvanzado arbitro = jugada1(8);
        jugada2(arbitro);
        assertFalse(arbitro.estaAcabado());
    }

    @Test
    void ko() {
        ArbitroAtariGoAvanzado arbitro = new ArbitroAtariGoAvanzado(new Tablero(9, 9), 3);
        arbitro.registrarJugadoresEnOrden("Alice");
        arbitro.registrarJugadoresEnOrden("Bob");
        assertJugada(arbitro, 3,3);
        assertJugada(arbitro,3,6);
        assertJugada(arbitro,4,4);
        assertJugada(arbitro,4,5);
        assertJugada(arbitro,2,4);
        assertJugada(arbitro,2,5);
        assertJugada(arbitro,3,5);
        assertJugada(arbitro,3,4);
        assertJugada(arbitro,3,5, false);
        assertJugada(arbitro,5,4);
        assertJugada(arbitro,5,5);
        assertJugada(arbitro,3,5);
        assertJugada(arbitro,3,4, false);
    }


    private ArbitroAtariGoAvanzado jugada1(int cota) {
        ArbitroAtariGoAvanzado arbitro = new ArbitroAtariGoAvanzado(new Tablero(9,9), cota);
        arbitro.registrarJugadoresEnOrden("Alice");
        arbitro.registrarJugadoresEnOrden("Bob");
        assertJugada(arbitro,0,1);
        assertJugada(arbitro,0,2);
        assertJugada(arbitro,1,0);
        assertJugada(arbitro,1,1);
        assertJugada(arbitro,3,0);
        assertJugada(arbitro,2,0);
        assertJugada(arbitro, 0, 0, false);
        assertJugada(arbitro,1,2);
        assertJugada(arbitro, 0, 0);
        return arbitro;
    }

    private void jugada2(ArbitroAtariGo arbitro) {
        assertFalse(arbitro.estaAcabado());
        assertJugada(arbitro,0,3);
        assertJugada(arbitro,0,1);
        assertJugada(arbitro,8,8);
        assertJugada(arbitro,1,0);
        assertJugada(arbitro,2,1);
    }

    private void assertJugada(ArbitroAtariGo arbitro, int fila, int columna, boolean assertion) {
        assertEquals(assertion, jugarSiValido(arbitro, fila, columna));
    }

    private void assertJugada(ArbitroAtariGo arbitro, int fila, int columna) {
        assertJugada(arbitro, fila, columna, true);
    }

    private boolean jugarSiValido(ArbitroAtariGo arbitro, int fila, int columna) {
        Celda celda = arbitro.obtenerTablero().obtenerCelda(fila, columna);
        if (arbitro.esMovimientoLegal(celda)) {
            arbitro.jugar(celda);
            return true;
        }
        return false;
    }
}