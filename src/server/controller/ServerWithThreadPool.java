package src.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.*;

import src.server.model.Game;

public class ServerWithThreadPool {
    
    private Socket player1, player2;
	private ServerSocket serverSocket;
	private BufferedReader input1, input2;
	private PrintWriter output1, output2;
	
	private ExecutorService pool;

	public ServerWithThreadPool(int port) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void runServer () {
		try {
			while (true) {
				player1 = serverSocket.accept();
				input1 = new BufferedReader (new InputStreamReader (player1.getInputStream()));
				output1 = new PrintWriter((player1.getOutputStream()), true);
				System.out.println("Player 1 Connected!");

				player2 = serverSocket.accept();
				input2 = new BufferedReader (new InputStreamReader (player2.getInputStream()));
				output2 = new PrintWriter((player2.getOutputStream()), true);
				System.out.println("Player 2 Connected!");

				// Paramaters are input and output sockets for the 2 players
				Game currentGame = new Game(input1, input2, output1, output2);
				System.out.println("Game Started!");
				pool.execute(currentGame);
			}
			
		}catch (IOException e) {}
		pool.shutdown();
		
		try {
			input1.close();
			input2.close();
			output1.close();
			output2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main (String [] args){
		ServerWithThreadPool myServer = new ServerWithThreadPool (9898);
		myServer.runServer();
	}
}