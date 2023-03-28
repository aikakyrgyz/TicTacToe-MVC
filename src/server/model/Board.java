package src.server.model;

import java.io.PrintWriter;

public class Board implements Constants {
	private char theBoard[][];
	private int markCount; //number of marks on the board

	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	// Returns what mark is at the specific row and column
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	// Checks if the board is full
	public boolean isFull() {
		return markCount == 9;
	}

	//checks and returns true/false if player X wins
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	//checks and returns true/false if player O wins
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1) // neeed to check to LETTER_0
			return true;
		else
			return false;
	}

    // Whenever display is called, the board is send to both players' output sockets
	public void display(PrintWriter output) {
		displayColumnHeaders(output);
		addHyphens(output);
		for (int row = 0; row < 3; row++) {
			addSpaces(output);
			output.print("    row " + row + ' ');

			for (int col = 0; col < 3; col++)
				output.print("|  " + getMark(row, col) + "  ");
			output.println("|");
			addSpaces(output);
			addHyphens(output);
		}
	}

	// Adds the O or X mark on the board
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	//Clears the board
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	// Checks if the current move leads to a win
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	// The header of the board
	void displayColumnHeaders(PrintWriter output) {
		output.print("          ");
		for (int j = 0; j < 3; j++){
			output.print("|col " + j);
		}
		output.println();
	}

	//Adds the boarder on the board
	void addHyphens(PrintWriter output) {
		output.print("          ");
		for (int j = 0; j < 3; j++){
			output.print("+-----");
		}
		output.println("+");
	}

	//Adds spaces to the board
	void addSpaces(PrintWriter output) {
		output.print("          ");

		for (int j = 0; j < 3; j++){
			output.print("|     ");
		}
		output.println("|");
	}
}
