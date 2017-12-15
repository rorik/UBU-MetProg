package juego.control;

import juego.modelo.Color;
import juego.modelo.Piedra;
import juego.modelo.Tablero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.control.ArbitroTest] Created by Roderick D. on 2017/10/31.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class ArbitroBasicoTest {
    @Test
    void registrarJugadoresEnOrden() {
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(new Tablero(1,1));
        arbitro.registrarJugadoresEnOrden("jugador1");
        arbitro.registrarJugadoresEnOrden("jugador2");
        assertEquals("jugador1", arbitro.obtenerJugadorConTurno().obtenerNombre());
        assertEquals(Color.NEGRO, arbitro.obtenerJugadorConTurno().obtenerColor());
        assertEquals("jugador2", arbitro.obtenerJugadorSinTurno().obtenerNombre());
        assertEquals(Color.BLANCO, arbitro.obtenerJugadorSinTurno().obtenerColor());
        arbitro.registrarJugadoresEnOrden("jugador3");
        assertEquals("jugador1", arbitro.obtenerJugadorConTurno().obtenerNombre());
        assertEquals("jugador2", arbitro.obtenerJugadorSinTurno().obtenerNombre());
}

    @Test
    void cambiarTurno() {
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(new Tablero(1,1));
        arbitro.registrarJugadoresEnOrden("jugador");
        assertNull(arbitro.obtenerJugadorSinTurno());
        arbitro.cambiarTurno();
        assertEquals("jugador", arbitro.obtenerJugadorSinTurno().obtenerNombre());
        assertNull(arbitro.obtenerJugadorConTurno());
    }

    @Test
    void obtenerTablero() {
        Tablero tablero = new Tablero(1,1);
        assertEquals(tablero, new ArbitroAtariGoBasico(tablero).obtenerTablero());
    }

    @Test
    void estaAcabado() {
        Tablero tablero = new Tablero(1,2);
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(tablero);
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        assertFalse(arbitro.estaAcabado());
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0,0));
        assertFalse(arbitro.estaAcabado());
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0,1));
        assertTrue(arbitro.estaAcabado());
    }

    @Test
    void obtenerGanador() {
        Tablero tablero = new Tablero(3,2);
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(tablero);
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(0,1));
        arbitro.jugar(tablero.obtenerCelda(1,0));
        assertFalse(arbitro.estaAcabado());
        arbitro.jugar(tablero.obtenerCelda(1,1));
        arbitro.jugar(tablero.obtenerCelda(2,1));
        assertTrue(arbitro.estaAcabado());
        assertEquals("a", arbitro.obtenerGanador().obtenerNombre());
    }

    @Test
    void jugar() {
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(new Tablero(2,2));
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        assertEquals("b", arbitro.obtenerJugadorSinTurno().obtenerNombre());
        arbitro.jugar(arbitro.obtenerTablero().obtenerCelda(0,0));
        assertEquals("a", arbitro.obtenerJugadorSinTurno().obtenerNombre());
        assertNotNull(arbitro.obtenerTablero().obtenerCelda(0,0).obtenerPiedra());
    }

    @Test
    void esMovimientoLegal() {
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(new Tablero(2,2));
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        arbitro.jugar(arbitro.obtenerTablero().obtenerCelda(1,0));
        assertFalse(arbitro.esMovimientoLegal(arbitro.obtenerTablero().obtenerCelda(1,0)));
        assertTrue(arbitro.esMovimientoLegal(arbitro.obtenerTablero().obtenerCelda(0,1)));
    }

    @Test
    void esMovimientoLegalSuicida1() {
        Tablero tablero = new Tablero(2,3);
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(tablero);
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(0,0));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(1,1));
        arbitro.jugar(tablero.obtenerCelda(0,2));
        assertFalse(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,1)));
        arbitro.cambiarTurno();
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,1)));
    }

    @Test
    void esMovimientoLegalSuicida2() {
        Tablero tablero = new Tablero(2,4);
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(tablero);
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(0,0));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(1,1));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(1,2));
        arbitro.jugar(tablero.obtenerCelda(0,3));
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,1)));
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,2)));
        arbitro.cambiarTurno();
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,1)));
        arbitro.cambiarTurno();
        arbitro.jugar(tablero.obtenerCelda(0, 1));
        arbitro.cambiarTurno();
        assertFalse(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,2)));
    }

    @Test
    void esMovimientoLegalSuicida3() {
        Tablero tablero = new Tablero(4,5);
        ArbitroAtariGo arbitro = new ArbitroAtariGoBasico(tablero);
        arbitro.registrarJugadoresEnOrden("a");
        arbitro.registrarJugadoresEnOrden("b");
        arbitro.cambiarTurno();
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(0,2));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(1,1));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(1,3));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(2,0));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(2,3));
        tablero.colocar(arbitro.obtenerJugadorConTurno().generarPiedra(), tablero.obtenerCelda(3,1));
        arbitro.jugar(tablero.obtenerCelda(3,2));
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(0,3)));
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(1,2)));
        arbitro.jugar(tablero.obtenerCelda(2, 2));
        arbitro.jugar(tablero.obtenerCelda(1, 2));
        assertFalse(arbitro.esMovimientoLegal(tablero.obtenerCelda(2,1)));
        arbitro.cambiarTurno();
        assertTrue(arbitro.esMovimientoLegal(tablero.obtenerCelda(2,1)));
    }
}