package net.scriptronix.snakegame;

import java.util.Scanner;

/**
 * Handles application inputs and notifies the rest of the application about inputs.
 */
public class InputManager {
    public static void main(String[] args) {
        System.out.println("Hello world");
        
        Scanner input = new Scanner(System.in);
        while (true) {            
            if (input.hasNext()) {
                System.out.println("The next input is: " + input.next());
            }
        }
        
    }
}
