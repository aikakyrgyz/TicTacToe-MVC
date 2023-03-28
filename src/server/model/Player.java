package src.server.model;

import java.io.*;

class Player implements Constants {
    private BufferedReader input;
    public PrintWriter output;

	private String name;
	private Board board;
	private char mark;
	Player opponent;

	public Player(String nameA, char markA, BufferedReader input, PrintWriter output) {
		name = nameA;
		mark = markA;
        this.input = input;
		this.output = output;
	}

	public void setOpponent(Player other) {
		opponent = other;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String name() {
		return name;
	}

	public char mark() {
		return mark;
	}

	// This is the players move and keeps on playing till someone wins or board is full
	public void play() throws IOException {
	
		board.clear();

		while ((board.xWins() == false) && (board.oWins() == false) && (board.isFull() == false)) {
			board.display(output);
			opponent.output.println("Please wait, it is "+ name() + "'s turn");
			makeMove();
			board.display(output);
			if (board.xWins() == false && board.isFull() == false){
				board.display(opponent.output);
				opponent.makeMove();
				board.display(opponent.output);
			}
		}

        // When the game is over, sends the following to both players.
		String gameOverPlayer1 = "";
		String gameOverPlayer2 = "";
		gameOverPlayer1 += "THE GAME IS OVER: ";
		gameOverPlayer2 += "THE GAME IS OVER: ";

		if (board.xWins() != false) {
			gameOverPlayer1 += name() + " is the winner!";
			gameOverPlayer2 += name() + " is the winner!";
        }
		else if (board.oWins() != false) {
			gameOverPlayer1 += opponent.name() + " is the winner!";
			gameOverPlayer2 += 	opponent.name() + " is the winner!";
        }
		else {
			gameOverPlayer1 += "the game is a tie.";
			gameOverPlayer2 += "the game is a tie.";
        }

		output.println(gameOverPlayer1);
		opponent.output.println(gameOverPlayer2);
	}

	// Asks the player the specific row and column to make the move
	private void makeMove() throws IOException {
		String regex = "[0-9]+"; 
		output.println(name + ", what row should your next " + mark() + " be placed in? ");
		int row, col;
		String rStr, cStr;

		rStr = input.readLine();

		while(!rStr.matches(regex)){
			output.println(name + ", what row should your next " + mark() + " be placed in? ");
			rStr = input.readLine();
		}
			
		row =  Integer.parseInt(rStr);

		output.flush();
		output.println(name + ", what column should your next " + mark() + " be placed in? ");

		cStr = input.readLine();
		
		while(!cStr.matches(regex)){
			output.println(name + ", what column should your next " + mark() + " be placed in? ");
			cStr = input.readLine();
		}
		
		col = Integer.parseInt(cStr);

		// Checks if valid input and keeps on asking the inputs till it is valid
		while (true) {
            boolean acceptableInput = isAcceptableUserInput(row, col, rStr, cStr); 
			if (acceptableInput == true) break;

			output.println("Please enter the row again: ");
			rStr = input.readLine();
			row = Integer.parseInt(rStr);
			output.println("Please enter the column again: ");
			cStr = input.readLine();
			col = Integer.parseInt(cStr);
		}
		
		// Once valid move is inputed, the mark is added to the board
		board.addMark(row, col, mark());
	}
	

	// Checks if the inputed row and column number is a valid move or space
    private boolean isAcceptableUserInput(int row, int col, String rStr, String cStr) {
		boolean acceptableInput = true;
		if (rStr == null || cStr == null) {
			output.println("Sorry, " + name()
					+ ", I couldn't understand your input. ");
			acceptableInput = false;
		} else if (row < 0 || row >= 3 || col < 0 || col >= 3) {
			output.println("Sorry, " + name
					+ ", but there is no square with coordinates (row="
					+ row + ", col=" + col + "). ");
			acceptableInput = false;
		} else if (board.getMark(row, col) != SPACE_CHAR) {
			output.println("Sorry, " + name
					+ ", but the square with coordinates (row=" + row
					+ ", col=" + col + ") is marked. ");
			acceptableInput = false;
		}
		
		return acceptableInput;
	}
}