package src.server.model;

import java.io.*;

class Referee {

	private Player xPlayer;
	private Player oPlayer;
	private Board board;

	public Referee() {
		
	}

	//This will start the game
	void runTheGame() throws IOException {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		System.out.println("\nReferee started the game...\n");
		board.display(oPlayer.output);
		board.display(xPlayer.output);
		xPlayer.play();
		System.out.println("\nReferee ended the game ...");
	}
	
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}

	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}