package es.upm.miw.SolitarioCelta;

import android.arch.lifecycle.ViewModel;

public class SCeltaViewModel extends ViewModel {

    // This ViewModel class will hold all of the data associated with the screen.
    // This separates the code to display the UI, which is implemented in your
    // Activities and Fragments, from your data, which lives in the ViewModel.

    private JuegoCelta _juego;

    public SCeltaViewModel() {
        this._juego = new JuegoCelta();
    }

    /**
     * Recibe las coordenadas de la posición pulsada y
     * dependiendo del estado, realiza la acción
     *
     * @param iDestino coordenada fila
     * @param jDestino coordenada columna
     */
    public void jugar(int iDestino, int jDestino) {
        _juego.jugar(iDestino, jDestino);
    }

    /**
     * @return Número de fichas en el tablero
     */
    public int numeroFichas() {
        return _juego.numeroFichas();
    }

    /**
     * Determina si el juego ha terminado (no se puede realizar ningún movimiento)
     *
     * @return valor lógico
     */
    public boolean juegoTerminado() {
        return _juego.juegoTerminado();
    }

    /**
     * Devuelve el contenido de una posición del tablero
     *
     * @param i fila del tablero
     * @param j columna del tablero
     * @return contenido
     */
    public int obtenerFicha(int i, int j) {
        return _juego.obtenerFicha(i, j);
    }

    /**
     * Serializa el tablero, devolviendo una cadena de 7x7 caracteres (dígitos 0 o 1)
     *
     * @return tablero serializado
     */
    public String serializaTablero() {
        return _juego.serializaTablero();
    }

    /**
     * recupera el estado del tablero a partir de su representación serializada
     *
     * @param str representación del tablero
     */
    public void deserializaTablero(String str) {
        _juego.deserializaTablero(str);
    }

    /**
     * Reinicia el juego a su estado inicial
     */
    public void reiniciar() {
        _juego.reiniciar();
    }
}
