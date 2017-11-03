package juego.textui;

import juego.control.ArbitroAtariGo;
import juego.modelo.Celda;
import juego.modelo.Tablero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.textui.JuegoTest] Created by Roderick D. on 2017/10/29.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class JuegoTest {
    @Test
    void tripleLock1() {
        ArbitroAtariGo arbitro = tripleLock9x9();
        assertJugada(arbitro, 0,1);
        assertJugada(arbitro, 8,0, false);
        assertJugada(arbitro, 8,5);
        assertTrue(arbitro.estaAcabado());
        assertEquals("Alice", arbitro.obtenerGanador().obtenerNombre());
    }

    @Test
    void tripleLock2() {
        ArbitroAtariGo arbitro = tripleLock9x9();
        assertJugada(arbitro, 0,1);
    }

    @Test
    void innerLock() {
        ArbitroAtariGo arbitro = innerLock9x9();
        assertJugada(arbitro, 5,3, false);
        assertJugada(arbitro, 1,1);
        printTablero(arbitro);
        assertJugada(arbitro, 5,3);
        assertJugada(arbitro, 1,6);
        assertTrue(arbitro.estaAcabado());
        assertEquals("Bob", arbitro.obtenerGanador().obtenerNombre());
    }

    private ArbitroAtariGo tripleLock9x9() {
        ArbitroAtariGo arbitro = new ArbitroAtariGo(new Tablero(9,9));
        arbitro.registrarJugadoresEnOrden("Alice");
        arbitro.registrarJugadoresEnOrden("Bob");
        assertJugada(arbitro, 4, 0);
        assertJugada(arbitro, 5, 0);
        assertJugada(arbitro, 6, 0);
        assertJugada(arbitro, 5, 1);
        assertJugada(arbitro, 4, 1);
        assertJugada(arbitro, 6, 1);
        assertJugada(arbitro, 7, 0);
        assertJugada(arbitro, 6, 2);
        assertJugada(arbitro, 7, 1);
        assertJugada(arbitro, 7, 2);
        assertJugada(arbitro, 8, 1);
        assertJugada(arbitro, 7, 3);
        assertJugada(arbitro, 8, 2);
        assertJugada(arbitro, 8, 3);
        assertJugada(arbitro, 5, 2);
        assertJugada(arbitro, 8, 4);
        assertJugada(arbitro, 6, 3);
        assertJugada(arbitro, 0, 0);
        assertJugada(arbitro, 7, 4);
        assertFalse(arbitro.estaAcabado());
        return arbitro;
    }

    private ArbitroAtariGo innerLock9x9() {
        ArbitroAtariGo arbitro = new ArbitroAtariGo(new Tablero(9,9));
        arbitro.registrarJugadoresEnOrden("Alice");
        arbitro.registrarJugadoresEnOrden("Bob");
        assertJugada(arbitro, 4,3);
        assertJugada(arbitro, 3,3);
        assertJugada(arbitro, 4,2);
        assertJugada(arbitro, 3,2);
        assertJugada(arbitro, 5,2);
        assertJugada(arbitro, 4,1);
        assertJugada(arbitro, 6,3);
        assertJugada(arbitro, 5,1);
        assertJugada(arbitro, 5,4);
        assertJugada(arbitro, 6,2);
        assertJugada(arbitro, 4,4);
        assertJugada(arbitro, 3,4);
        assertJugada(arbitro, 1,2);
        assertJugada(arbitro, 4,5);
        assertJugada(arbitro, 1,3);
        assertJugada(arbitro, 5,5);
        assertJugada(arbitro, 1,4);
        assertJugada(arbitro, 6,4);
        assertJugada(arbitro, 1,5);
        assertJugada(arbitro, 7,3);
        assertFalse(arbitro.estaAcabado());
        return arbitro;
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

    private void printTablero(ArbitroAtariGo arbitro) {
        String salida = "";
        for (int i = 0; i < arbitro.obtenerTablero().obtenerNumeroFilas(); i++) {
            for (int j = 0; j < arbitro.obtenerTablero().obtenerNumeroColumnas(); j++) {
                Celda celda = arbitro.obtenerTablero().obtenerCelda(i, j);
                if (celda.estaVacia())
                    salida = salida.concat("-");
                else
                    salida = salida.concat(String.valueOf(celda.obtenerPiedra().obtenerColor().toChar()));
            }
            System.out.println(salida);
            salida = "";
        }
        System.out.println(":: " + arbitro.obtenerJugadorConTurno().obtenerColor().toChar() + " ::");
    }
}