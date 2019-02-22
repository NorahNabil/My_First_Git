package game.packman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Maze {//Class to initialise the maze's components

	ArrayList<String> lines;
//	int row, column;
	int rows, columns; 
	int width, height;
	public Position packmanPos;
	public Position ghostPos , redscater , bluescater , pinkscater , orngscater;
	public ArrayList<Position> pills, powerPills; 
	
	public Maze(int m) {
		// load the lines
		try {
			pills = new ArrayList<Position>();
			powerPills = new ArrayList<Position>();
			lines = new ArrayList<String>();
			Scanner s = new Scanner(new File("mazes/"+m));//read the file m (0 in the start) that contains the initial design of the maze. 
			int r = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				lines.add(line);
				if (line.contains("4")) {//whenever you see number 4 in the file count it as a ghost. 
					ghostPos = new Position(r, line.indexOf('4'));
				}
				if (line.contains("5")) {//whenever you see number 5 in the file count it as a packman. 
					packmanPos = new Position(r, line.indexOf('5'));
				}
				if (line.contains("9")) {
					bluescater = new Position(r , line.indexOf('9'));
				}
				if (line.contains("6")) {
					orngscater = new Position(r , line.indexOf('6'));
				}
				if (line.contains("8")) {
					redscater = new Position(r , line.indexOf('8'));
				}
				if (line.contains("7")) {
					pinkscater = new Position(r , line.indexOf('7'));
		     	}
				for (int i=0; i<line.length(); i++) {
					if (line.charAt(i) == '2') {
						pills.add(new Position(r, i));
					} else if (line.charAt(i) == '3') {
						powerPills.add(new Position(r, i));
					}
				}
				r++;
			}
			s.close();
			
			rows = lines.size();
			columns = lines.get(0).length();
			
			width = columns*2;
			height = rows*2;
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public char charAt(int r, int c) {
		return lines.get(r).charAt(c);
	}

	public char[][] getCells() {
		char[][] cells = new char[rows][columns];
		for (int r=0; r<rows; r++) {
			System.arraycopy(lines.get(r).toCharArray(), 0, cells[r], 0, columns);
//			for (int c=0; c<columns; c++) {
//			}
		}
		return cells;
	}

}
