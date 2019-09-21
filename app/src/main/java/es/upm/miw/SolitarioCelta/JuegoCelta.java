package es.upm.miw.SolitarioCelta;

class JuegoCelta {

	static final int TAMANIO = 7;
	static final int HUECO = 0;
    static final int FICHA = 1;
    private static final int NUM_MOVIMIENTOS = 4;
	private int[][] tablero;

    private static final int[][] TABLERO_INICIAL = {	// Posiciones válidas del tablero
            {HUECO, HUECO, FICHA, FICHA, FICHA, HUECO, HUECO},
            {HUECO, HUECO, FICHA, FICHA, FICHA, HUECO, HUECO},
            {FICHA, FICHA, FICHA, FICHA, FICHA, FICHA, FICHA},
            {FICHA, FICHA, FICHA, FICHA, FICHA, FICHA, FICHA},
            {FICHA, FICHA, FICHA, FICHA, FICHA, FICHA, FICHA},
            {HUECO, HUECO, FICHA, FICHA, FICHA, HUECO, HUECO},
            {HUECO, HUECO, FICHA, FICHA, FICHA, HUECO, HUECO}
    };

    private static final int[][] desplazamientos = {
            { 0,  2},   // Dcha
            { 0, -2},   // Izda
            { 2,  0},   // Abajo
            {-2,  0}    // Arriba
    };

    private int iSeleccionada, jSeleccionada;   // coordenadas origen ficha
	private int iSaltada, jSaltada;             // coordenadas ficha sobre la que se hace el movimiento

	// Estados del juego
	private enum Estado {
		ESTADO_SELECCION_FICHA, ESTADO_SELECCION_DESTINO, ESTADO_TERMINADO
	}

	private Estado estadoActual;

    /**
     * Constructor
     * Inicializa el tablero y el estado del juego
     */
    public JuegoCelta() {
		tablero = new int[TAMANIO][TAMANIO];
		this.reiniciar();
	}

    /**
     * Devuelve el contenido de una posición del tablero
	 *
     * @param i fila del tablero
     * @param j columna del tablero
     * @return contenido
     */
	public int obtenerFicha(int i, int j) {
		return tablero[i][j];
	}

    /**
     * @return Número de fichas en el tablero
     */
    public int numeroFichas() {
    	int numFichas = 0;
		for (int i = 0, cont = 0; i < TAMANIO; i++)
			for (int j = 0; j < TAMANIO; j++)
				if (tablero[i][j] == FICHA)
					numFichas++;

		return numFichas;
    }

    /**
     * Determina si el movimiento (i1, j1) a (i2, j2) es aceptable
     * @param i1 fila origen
     * @param j1 columna origen
     * @param i2 fila destino
     * @param j2 columna destino
     * @return valor lógico
     */
    private boolean movimientoAceptable(int i1, int j1, int i2, int j2) {

        if (tablero[i1][j1] != FICHA || tablero[i2][j2] != HUECO)
            return false;

        if ((j1 == j2 && Math.abs(i2 - i1) == 2)
                || (i1 == i2 && Math.abs(j2 - j1) == 2)) {
            iSaltada = (i1 + i2) / 2;
            jSaltada = (j1 + j2) / 2;
            if (tablero[iSaltada][jSaltada] == FICHA)
                return true;
        }

        return false;
    }

    /**
     * Recibe las coordenadas de la posición pulsada y
	 * dependiendo del estado, realiza la acción
	 *
     * @param iDestino coordenada fila
     * @param jDestino coordenada columna
     */
	public void jugar(int iDestino, int jDestino) {
		if (estadoActual == Estado.ESTADO_SELECCION_FICHA
				&& tablero[iDestino][jDestino] == FICHA) {
			iSeleccionada = iDestino;
			jSeleccionada = jDestino;
			estadoActual = Estado.ESTADO_SELECCION_DESTINO;
		} else if (estadoActual == Estado.ESTADO_SELECCION_DESTINO) {
			if (movimientoAceptable(iSeleccionada, jSeleccionada, iDestino, jDestino)) {
				estadoActual = Estado.ESTADO_SELECCION_FICHA;

                // Actualizar tablero
				tablero[iSeleccionada][jSeleccionada] = HUECO;
				tablero[iSaltada][jSaltada]           = HUECO;
				tablero[iDestino][jDestino]           = FICHA;

				if (juegoTerminado())
					estadoActual = Estado.ESTADO_TERMINADO;
			} else { // El movimiento no es aceptable, la última ficha pasa a ser la seleccionada
				iSeleccionada = iDestino;
				jSeleccionada = jDestino;
			}
		}
	}

    /**
     * Determina si el juego ha terminado (no se puede realizar ningún movimiento)
	 *
     * @return valor lógico
     */
    public boolean juegoTerminado() {

        for (int i = 0; i < TAMANIO; i++)
            for (int j = 0; j < TAMANIO; j++)
                if (tablero[i][j] == FICHA) {
                    for (int k = 0; k < NUM_MOVIMIENTOS; k++) {
                        int p = i + desplazamientos[k][0];
                        int q = j + desplazamientos[k][1];
                        if (p >= 0 && p < TAMANIO && q >= 0 && q < TAMANIO
                                && tablero[p][q] == HUECO
                                && TABLERO_INICIAL[p][q] == FICHA)
                            if (movimientoAceptable(i, j, p, q))
                                return false;
                    }
                }

        return true;
    }

	/**
	 * Serializa el tablero, devolviendo una cadena de 7x7 caracteres (dígitos 0 o 1)
	 *
	 * @return tablero serializado
     */
	public String serializaTablero() {
		StringBuilder stb = new StringBuilder(TAMANIO * TAMANIO);
		for (int i = 0; i < TAMANIO; i++)
			for (int j = 0; j < TAMANIO; j++)
				stb.append(tablero[i][j]);
		return stb.toString();
	}

	/**
	 * recupera el estado del tablero a partir de su representación serializada
	 *
	 * @param str representación del tablero
     */
	public void deserializaTablero(String str) {
		for (int i = 0, cont = 0; i < TAMANIO; i++)
			for (int j = 0; j < TAMANIO; j++)
				tablero[i][j] = str.charAt(cont++) - '0';
	}

	/**
	 * Recupera el juego a su estado inicial
	 */
	public void reiniciar() {
		for (int i = 0; i < TAMANIO; i++)
			System.arraycopy(TABLERO_INICIAL[i], 0, tablero[i], 0, TAMANIO);
        tablero[TAMANIO / 2][TAMANIO / 2] = HUECO;   // hueco en posición central

        estadoActual = Estado.ESTADO_SELECCION_FICHA;
	}
}
