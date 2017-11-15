package com.upc.edu.nea;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Nicolás Eduardo Aldana
 * 
 * Class that represent a generic n-puzzle 
 */

public class Puzzle {
	private int[][] blocks;	
	
	/**
	 * Constructor
	 * @param blocks
	 */
	public Puzzle(int[][] blocks) {
		this.blocks = blocks;
	}
	
	/**
	 * Print the puzzle board in console
	 */
	public void printPuzzle(){
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks[i].length; j++) {
				System.out.print(this.blocks[i][j] == 0 ? "  " : this.blocks[i][j] + " ");
			}
			System.out.println();
		}		
	}	
	
	/**
	 * Visit the neighbors of this state puzzle 
	 * @return A map with a move key and state value
	 */
	public Map<String, Puzzle> nextStates(){		
		String[] space = spaceBlock().split(",");
		int spaceRow = Integer.parseInt(space[0]);
		int spaceCol = Integer.parseInt(space[1]);
		
		Map<String, Puzzle> statesMap = new HashMap<>();		
		
		//Muevo la blanca para arriba
		if (spaceRow > 0) {			
			statesMap.put("UP", new Puzzle(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
		}
		//Muevo la blanca para abajo
		if (spaceRow < this.blocks.length - 1) {			
			statesMap.put("DOWN", new Puzzle(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
		}
		//Muevo la blanca para la izquierda
		if (spaceCol > 0) {			
			statesMap.put("LEFT", new Puzzle(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
		}
		//Muevo la blanca para la derecha
		if (spaceCol < this.blocks[0].length - 1) {			
			statesMap.put("RIGHT", new Puzzle(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));
		}
		
		return statesMap;
	}
	
	/**
	 * Check if the puzzle is solved
	 * @return true if the puzzle is solved
	 */
	public boolean isSolved() {
		int count = 1;
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks[i].length; j++) {
				if (i == (this.blocks.length - 1) && j == (this.blocks[i].length - 1)) {
					return this.blocks[i][j] == 0;
				} else {
					if(this.blocks[i][j] != count) return false;
				}
				count ++;
			}
		}
		return true;
	}
	
	/**
	 * UNUSED
	 * 
	 * Print the values of manhattan distance calculation in console
	 */
	public void testDistance() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				System.out.println("index: " + i + "," + j + " value: " + blocks[i][j] + " rowDistance: " + rowValue(blocks[i][j]));
				System.out.println("index: " + i + "," + j + " value: " + blocks[i][j] + " colDistance: " + colValue(blocks[i][j]));
				System.out.println("MANHATTAN: " + (Math.abs(i - rowValue(blocks[i][j])) + Math.abs(j - colValue(blocks[i][j]))));
			}
		}
	}
	
	/**
	 * http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
	 * 
	 * Observando el arbol del juego se selecciona la prioridad de cada nodo utilizando la funcion manhattan
	 * definida en el documento - sera nuestra funcion de prioridad -
	 * se calcula sumando la distancia horizontal y vertical de un bloque a su posicion objetivo. 
	 * 
	 * A tener en cuenta: uno de los objetivos de optimizacion es precalcular este dato para evitar gastos innecesarios
	 * en memoria.
	 * 
	 * retorno el valor manhattan del tablero.
	 */
	
	public int manhattan() {
		int m = 0;
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks[i].length; j++) {
				m += distance(i, j);
			}
		}
		return m;
	}
	
	/**
	 * Check if this puzzle is equal to anothe puzzle
	 * @param p Puzzle to check 
	 * @return true if the puzzle are equal
	 */
	public boolean equals(Puzzle p) {
		if(p == null || p.blocks.length != this.blocks.length) return false;
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks.length; j++) {
				if(this.blocks[i][j] != p.blocks[i][j]) return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the coordinate of the space block in the matrix puzzle
	 * @return String coordinate 
	 */
	private String spaceBlock(){
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks[i].length; j++) {
				if (this.blocks[i][j] == 0) return i + "," + j; 
			}			
		}
		return null;
	}
	
	/**
	 * Copy the blocks of this puzzle
	 * @return copy of the blocks
	 */
	private int[][] copyBlocks(){
		int[][] copy = new int[this.blocks.length][this.blocks[0].length];
		for (int i = 0; i < this.blocks.length; i++) {
			System.arraycopy(this.blocks[i], 0, copy[i], 0, this.blocks.length);
		}
		return copy;
	}
	
	/**
	 * Used to exchange the blank block with another possible block
	 * @param row1 row of the blank block
	 * @param col1 col of the blank block
	 * @param row2 row of the possible block to exchange	
	 * @param col2 cow of the possible block to exchange
	 * @return Blocks with swapped blank block
	 */
	private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copyBlocks();
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }
	/**
	 * https://stackoverflow.com/questions/8224470/calculating-manhattan-distance
	 * 
	 * https://es.wikipedia.org/wiki/Geometr%C3%ADa_del_taxista
	 * 
	 * Obtengo la distancia entre la ubicacion esperada y la ubicacion real.
	 */
	
	private int distance(int i_row, int j_col) {
		/**
		 *  la distancia entre dos puntos es la suma de las diferencias (absolutas) de sus coordenadas
		 *  
		 *  El valor absoluto de la resta entra la posicion en la que deberia estar contra la posicion en la que esta
		 */
		
		//No tengo en cuenta el cero
		return this.blocks[i_row][j_col] == 0 ? 0 : (Math.abs(i_row - rowValue(this.blocks[i_row][j_col])) + Math.abs(j_col - colValue(blocks[i_row][j_col])));
	}
	/**
	 * devuelve en cual fila tengo que estar ubicado 
	 */
	private int rowValue(int i) {
		//Como el cero no va en la primera le resto 1
		return (i-1) / this.blocks.length;
	}
	/**
	 * Devuelve en cual columna tengo que estar ubicado
	 */
	private int colValue(int j) {
		//Como el cero no va en la primera le resto 1
		return (j-1) % this.blocks.length;
	}


}
