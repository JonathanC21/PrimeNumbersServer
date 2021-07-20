package com.valencia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;
import java.sql.Timestamp;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The main class for the Server application. Takes an integer input from the client and tells the client whether or not the integer is a prime number
 */
public class Server extends Application{
	
	DataOutputStream output = null;
	DataInputStream input = null;
	
	/**
	 * The main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		
		launch(args);
		System.exit(0);	
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Primary Numbers Server");
		
		TextArea outputArea = new TextArea();
		outputArea.setEditable(false);
		
		Scene scene = new Scene(outputArea,480,360);
		stage.setScene(scene);
		stage.show();
		
		new Thread(()->{
			try {
				
				ServerSocket server = new ServerSocket(8000);
				outputArea.appendText(formatText("Prime Numbers Server started"));
				
				Socket socket = server.accept();
				outputArea.appendText(formatText("Client connected"));
				
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
				
				while (true) {
					
					int inputNumber = input.readInt();
					
					output.writeBoolean(checkIfPrime(inputNumber));
					String string = "Number from client is " + inputNumber;
					
					outputArea.appendText(formatText(string));
				}
			}
			catch(IOException ioE){
				
				outputArea.appendText(formatText(ioE.getMessage()));
			}
		}).start();
	}
	
	/**
	 * Helper method to format text outputted to the the outputArea element of the application. Adds a timestamp at the beginning of the string and skips a line at the end
	 * 
	 * @param text The string to format
	 * @return Returns the formatted string
	 */
	public static String formatText(String text) {
		
		StringBuilder sb = new StringBuilder(text);
		
		sb.insert(0, "[" + new Timestamp(System.currentTimeMillis()) + "]: ");
		sb.append("\n");
		
		return sb.toString();
	}
	
	/**
	 * Checks whether an integer is a prime number or not
	 * 
	 * @param number The integer to check
	 * @return Returns whether the number is prime or not as a boolean
	 */
	public static Boolean checkIfPrime(int number) {
		
		if(number <= 1) {
			
			return false;
		}
		
        for (int i = 2; i < number; i++) {
        	
            if (number % i == 0)
            	return false;
        }
		
		return true;
	}
}
