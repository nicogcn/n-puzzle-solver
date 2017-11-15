package com.upc.edu.nea;

/**
 * 
 * @author NicolásAldanaAngulo
 *
 *Class that represent a move node
 */
public class Move {
	
	private Move lastMove;
	private Puzzle puzzle;
	private String directionMove;
	private int makedMoves;
	private int priority;
	
	/**
	 * Constructor of an initial move	
	 * @param puzzle
	 */
	public Move(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.lastMove = null;
		this.directionMove = null;
		this.makedMoves = 0;
		this.priority = puzzle.manhattan();
	}
	/**
	 * Constructor of a children move
	 * @param puzzle
	 * @param lastMove
	 * @param directionMove
	 */
	public Move(Puzzle puzzle, Move lastMove, String directionMove) {
		this.puzzle = puzzle;
		this.lastMove = lastMove;
		this.directionMove = directionMove;
		this.makedMoves = lastMove.getMakedMoves() + 1;
		this.priority = this.makedMoves + puzzle.manhattan();
	}
	
	/**
	 * GETTERS & SETTERS
	 * 
	 */	
	
	public Move getLastMove() {
		return lastMove;
	}
	public Puzzle getPuzzle() {
		return puzzle;
	}
	public String getDirectionMove() {
		return directionMove;
	}
	public int getMakedMoves() {
		return makedMoves;
	}
	public int getPriority() {
		return priority;
	}
}
