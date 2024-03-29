package juego.modelo;

import juego.textui.JuegoTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [juego.modelo.GrupoTest] Created by Roderick D. on 2017/10/31.
 *
 * @author Roderick D.
 * rorik.me
 * github.com/rorik
 */
class GrupoTest {
    @Test
    void obtenerId() {
        Tablero tablero = new Tablero(1,2);
        assertThrows(AssertionError.class, () -> new Grupo(tablero.obtenerCelda(0,0), tablero));
        tablero.obtenerCelda(0,0).establecerPiedra(new Piedra(Color.NEGRO));
        tablero.obtenerCelda(0,1).establecerPiedra(new Piedra(Color.NEGRO));
        Grupo grupo = new Grupo(tablero.obtenerCelda(0,0), tablero);
        assertEquals(grupo.obtenerId()+1, new Grupo(tablero.obtenerCelda(0,1), tablero).obtenerId());
    }

    @Test
    void obtenerColor() {
        Tablero tablero = new Tablero(1,3);
        Jugador jugador = new Jugador("", Color.BLANCO);
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,0));
        Grupo grupo = tablero.obtenerGruposDelJugador(jugador).get(0);
        assertEquals(jugador.obtenerColor(), grupo.obtenerColor());
        tablero.colocar(new Piedra(Color.NEGRO), tablero.obtenerCelda(0,1));
        assertEquals(Color.NEGRO, new Grupo(tablero.obtenerCelda(0,1), tablero).obtenerColor());
    }

    @Test
    void estaVivo() {
        Tablero tablero = new Tablero(1,3);
        Jugador jugador1 = new Jugador("", Color.BLANCO);
        Jugador jugador2 = new Jugador("", Color.NEGRO);
        tablero.colocar(jugador1.generarPiedra(), tablero.obtenerCelda(0,0));
        assertTrue(tablero.obtenerGruposDelJugador(jugador1).get(0).estaVivo());
        tablero.colocar(jugador2.generarPiedra(), tablero.obtenerCelda(0,1));
        assertEquals(0, tablero.obtenerGruposDelJugador(jugador1).size());
        assertTrue(tablero.obtenerGruposDelJugador(jugador2).get(0).estaVivo());
    }

    @Test
    void obtenerTamaño() {
        Tablero tablero = new Tablero(3,2);
        Jugador jugador = new Jugador("", Color.BLANCO);
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,1));
        Grupo grupo = tablero.obtenerGruposDelJugador(jugador).get(0);
        assertEquals(1, grupo.obtenerTamaño());
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(1,0));
        assertEquals(1, grupo.obtenerTamaño());
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(1,1));
        grupo = tablero.obtenerGruposDelJugador(jugador).get(0);
        assertEquals(3, grupo.obtenerTamaño());
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,0));
        grupo = tablero.obtenerGruposDelJugador(jugador).get(0);
        assertEquals(4, grupo.obtenerTamaño());
    }

    @Test
    void contiene() {
        Tablero tablero = new Tablero(2,1);
        Jugador jugador = new Jugador("", Color.NEGRO);
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,0));
        Grupo grupo = tablero.obtenerGruposDelJugador(jugador).get(0);
        assertTrue(grupo.contiene(tablero.obtenerCelda(0,0)));
        assertFalse(grupo.contiene(tablero.obtenerCelda(1,0)));
    }

    @Test
    void añadirCeldas() {
        Tablero tablero = new Tablero(2,2);
        Jugador jugador = new Jugador("", Color.BLANCO);
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,0));
        Grupo grupo1 = tablero.obtenerGruposDelJugador(jugador).get(0);
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(1,1));
        Grupo grupo2 = tablero.obtenerGruposDelJugador(jugador).get(1);
        assertEquals(1, grupo1.obtenerTamaño());
        grupo1.añadirCeldas(grupo2);
        assertEquals(2, grupo1.obtenerTamaño());
        assertTrue(grupo1.contiene(tablero.obtenerCelda(1,1)));
    }

    @Test
    void eliminarPiedras() {
        Tablero tablero = new Tablero(2,3);
        Jugador jugador = new Jugador("", Color.NEGRO);
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,0));
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(0,1));
        tablero.colocar(new Piedra(Color.BLANCO), tablero.obtenerCelda(1,1));
        tablero.colocar(jugador.generarPiedra(), tablero.obtenerCelda(1,2));
        tablero.obtenerGruposDelJugador(jugador).get(0).eliminarPiedras();
        assertNull(tablero.obtenerCelda(0,0).obtenerPiedra());
        assertNull(tablero.obtenerCelda(0,1).obtenerPiedra());
        assertNotNull(tablero.obtenerCelda(1,1).obtenerPiedra());
        assertNotNull(tablero.obtenerCelda(1,2).obtenerPiedra());
    }

    @Test
    void generarCopiaEnOtroTablero() {
        Tablero tablero1 = new Tablero(2,2);
        Tablero tablero2 = new Tablero(2,2);
        Jugador jugador = new Jugador("", Color.BLANCO);
        tablero1.colocar(jugador.generarPiedra(), tablero1.obtenerCelda(0,0));
        tablero1.colocar(jugador.generarPiedra(), tablero1.obtenerCelda(0,1));
        tablero1.colocar(jugador.generarPiedra(), tablero1.obtenerCelda(1,0));
        Grupo grupo = tablero1.obtenerGruposDelJugador(jugador).get(0);
        grupo.generarCopiaEnOtroTablero(tablero2);
        assertNotNull(tablero2.obtenerCelda(0,0).obtenerPiedra());
        assertNotNull(tablero2.obtenerCelda(0,1).obtenerPiedra());
        assertNotNull(tablero2.obtenerCelda(1,0).obtenerPiedra());
        assertNull(tablero2.obtenerCelda(1,1).obtenerPiedra());
    }

}