package com.upc.edu.nea;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * 
 * @author NicolásAldanaAngulo
 *
 *Class that solves a n-puzzle
 */
public class PuzzleSolver {
	
	private Move initialMove;
	private PriorityQueue<Move> pq = new PriorityQueue<>(new Comparator<Move>() {

		/**
		 * Comparador para organizar la lista de prioridad
		 * Devuelve un positivo si es mayor la prioridad de o1 que la de o2
		 */
		@Override
		public int compare(Move o1, Move o2) {			
			return o1.getPriority() - o2.getPriority();
		}
	});
	
	/**
	 * Constructor
	 * @param puzzle
	 */
	public PuzzleSolver(Puzzle puzzle) {		
		this.initialMove = new Move(puzzle);
		pq.add(this.initialMove);		
	}
	
	/**
	 * Procedure that solve a n-puzzle using A* Algorithm
	 */
	public void solve() {
		while(true) {
			//System.out.println("me quedo");
			//COMIENZO
			//OBTENGO EL MOVIMIENTO CON MENOR PRIORIDAD
			//ENTRE MENOR PRIORIDAD MAS CERCA DEL OBJETIVO
			Move move = pq.poll();			
			if(move.getPuzzle().isSolved()) {
				if (move.getLastMove() != null) {
					printSolution(move);					
				} else {
					System.out.println("Entro puzzle solucionado");
				}
				return;// Si saque el solucionado ROMPO
			}

			//Obtengo los estados que siguen
			Map<String, Puzzle> states = move.getPuzzle().nextStates();
			//Expando los nodos guardando cada uno en la cola de prioridad
			for (Map.Entry<String, Puzzle> state : states.entrySet()) {
				//Confirmo que no sea el movimiento inicial y que el nuevo movimiento no sea igual que el anterior
				if (move.getLastMove() != null) {
					if (!move.getLastMove().getPuzzle().equals(state.getValue())) {						
						pq.add(new Move(state.getValue(), move, state.getKey()));//AÑADO					
					}
				}else {//Si es el primer movimiento solamente añado
					pq.add(new Move(state.getValue(), move, state.getKey()));//AÑADO					
				}
			}
		}		
	}
	
	/**
	 * Print solution in console
	 * @param m
	 */
	private void printSolution(Move m) {
		Move move = m;
		int numberOfMovements = move.getMakedMoves();
		boolean viewBoards = !true, viewMove = !true, viewNumberOfMovements = true, viewCompleteNames = !true;

		if (viewNumberOfMovements) System.out.println("MAKED MOVES: " +  numberOfMovements);
		String solution = viewCompleteNames? move.getDirectionMove() : reduceName(move.getDirectionMove());		
		while(move.getLastMove() != null) {
			if(viewBoards) System.out.println();
			solution = move.getLastMove().getDirectionMove() == null ? 
					solution :
						viewCompleteNames ? 
								move.getLastMove().getDirectionMove() + " - " + solution 
								: reduceName(move.getLastMove().getDirectionMove()) + " - " + solution;
			if(viewBoards) move.getPuzzle().printPuzzle();			
			if(viewMove) System.out.println(move.getDirectionMove());
			move = move.getLastMove();
		}
		System.out.println();
		System.out.println("SOLUCION: ");
		System.out.println(solution);
	}

	/**
	 * Reduce the name of a move to a single uppercase letter
	 * @param name
	 * @return letter that represent a move name 
	 */
	private String reduceName(String name) {
		switch (name) {
		case "UP":
			return "U";
		case "DOWN":			
			return "D";
		case "LEFT":			
			return "L";
		case "RIGHT":
			return "R";		
		}
		return "";
	}
}
