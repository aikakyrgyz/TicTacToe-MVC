package src.client.view;
import java.util.Scanner;

public class ClientView  {

    //Outputs the responses to the terminal
    public void showResponses(String response)
    {
        System.out.print(response);
    }

    //Ask the user for input and returns the input
    public String askForInput()
    {
        Scanner scanner = new Scanner(System.in); // get user move from user
		String input = scanner.nextLine();
        return input;
    }
    
}