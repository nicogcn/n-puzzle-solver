package com.upc.edu.nea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 
 * @author NicolasAldanaAngulo
 *
 *Class that launch the application
 */
public class PuzzleApp {
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Prueba uno
		/*int[] a = {0, 1, 3};
		int[] b = {4, 2, 5};
		int[] c = {7, 8, 6};*/
		
		//Prueba dos
		/*int[] a = {1, 2, 3};
		int[] b = {4, 5, 6};
		int[] c = {7, 8, 0};*/
		
		//Prueba tres
		/*int[] a = {7, 3, 0};
		int[] b = {4, 6, 8};
		int[] c = {5, 1, 2};*/
		
		//prueba 4
		/*int[] a = {4, 5, 1};
		int[] b = {3, 0, 8};
		int[] c = {6, 7, 2};*/
		
		//prueba 4
		int[] a = {0, 7, 6};
		int[] b = {4, 5, 2};
		int[] c = {1, 3, 8};

		int[][] block = {a, b, c};
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				System.out.print(block[i][j]);
			}
			System.out.println();
		}
		
		int[][] blocks = getBlocksFromFile();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				System.out.print(blocks[i][j]);
			}
			System.out.println();
		}
		Puzzle puzzle = new Puzzle(blocks);
		puzzle.printPuzzle();
		PuzzleSolver solver = new PuzzleSolver(puzzle);
		solver.solve();	

	}


	private static int[][] getBlocksFromFile(){
		String aux = "";
		String line = "";
		int[][] matrix = null;
		try{			
			JFileChooser fileChooser = new JFileChooser("src/resources");			
			fileChooser.showOpenDialog(fileChooser);			
			File file=fileChooser.getSelectedFile();

			if(file!=null){     
				FileReader archivos=new FileReader(file);
				BufferedReader lee=new BufferedReader(archivos);			
				while((aux=lee.readLine())!=null){					
					line += aux + ",";
				}				
				String[] lines = line.split(",");				
				String[] numbers = null;
				
				int[][] mtrx = new int[lines.length][lines.length];
				for (int i = 0; i < mtrx.length; i++) {
					numbers = lines[i].split(" ");
					for (int j = 0; j < mtrx.length; j++) {
						mtrx[i][j] = Integer.parseInt(numbers[j].trim());
					}					
				}
				matrix = mtrx;
				lee.close();
			}
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null , ex + "" + "\nNo se ha encontrado el archivo", "¡ADVERTENCIA!", JOptionPane.WARNING_MESSAGE);
		}
		return matrix;
	}
}
