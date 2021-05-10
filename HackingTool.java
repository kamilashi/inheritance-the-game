/** 
* the method resposible for the logical hacking quiz
*/
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class HackingTool implements Serializable
{
    // instance variables - replace the example below with your own
    private int trueCode;
    private int permittedAttempts = 5;
    
    public HackingTool()
    {
        this.trueCode = getRandomNumberInRange(100,999);
        System.out.println("the generated true code: " + trueCode + " here for testing");
    }
    
    public boolean hack()
    {
        for (int i = 0; i < permittedAttempts; i++){
                //Scanner input = new Scanner(System.in);
                String inputGuess = JOptionPane.showInputDialog("Try to guess a 3-digit code:");
                int guessCode = Integer.parseInt(inputGuess);
                //int guessCode = input.nextInt();
                if(tryGuess(guessCode)){
                    System.out.println("You've managed to open the lock");
                    return true; 
                }
        }
        return false;
    }
    
    public boolean tryGuess(int guessCode) //this is where the magic happens
    {   
        boolean isHacked = false;
        int bulls = 0;                //represent • and -
        int cows=0;
        
        HashMap<String,Integer> bullsCows=new HashMap<String,Integer>();
        String trueCodeString = Integer.toString(trueCode);
        String guessCodeString = Integer.toString(guessCode);
        for (int i = 0; i < trueCodeString.length(); i++){   
            for (int j = 0; j < guessCodeString.length(); j++){   
                if (trueCodeString.charAt(i) == guessCodeString.charAt(j)){
                    if(i==j){ 
                        bulls++; 
                    }
                    else{
                        cows++;
                    }
                }
            }
        }
        
        if(bulls == 3){
            isHacked = true;
            return isHacked;
        }
        
        bullsCows.put("•",bulls);
        bullsCows.put("-",cows);
        printTable(guessCode, bulls, cows);
        
        return isHacked;
    }
    
    public void printTable(int guessCode,int bulls, int cows)
    {
        System.out.printf("%nyour guess: %d%n•: %d%n-: %d%n", guessCode, bulls, cows);
        
    }
    
    private static int getRandomNumberInRange(int min, int max) 
    {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }
}
