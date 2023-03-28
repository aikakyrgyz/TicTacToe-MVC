package src.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import src.client.view.ClientView;

public class ClientController {
    private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ClientView myClientView;

	public ClientController (String serverName, int portNumber) {
		myClientView = new ClientView();
		try {
			aSocket = new Socket (serverName, portNumber);
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);
			System.out.println("Connected Successfully!");
			boolean gameOver = false;
			
			try {
				String response = "";

				// Keeps on listening till client gets a response that contains the string OVER
				while(!gameOver) {
					myClientView.showResponses("\n");
					response = socketIn.readLine();
					if(response.contains("what") || response.contains("name"))
					{
						myClientView.showResponses(response);
						String userInput  = myClientView.askForInput();
						socketOut.println(userInput); // send username move to the server
					}
					else {
						myClientView.showResponses(response);
						if(response.contains("OVER")){
							myClientView.showResponses("\n");
							break;
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			} finally {
				socketIn.close();
				socketOut.close();
			}
			} catch(IOException ex) {
				ex.getStackTrace();
			}
		}

	public static void main (String [] args) throws IOException{
		ClientController myClient = new ClientController ("localhost", 9898);
	}
}