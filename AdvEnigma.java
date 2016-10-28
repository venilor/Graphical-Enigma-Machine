package pkg.pex2;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Complete Enigma machine with 5 interchangeable rotors and plugboard settings
 * @author Cody Brown
 */

public class AdvEnigma {
    
    private static String Outer;
    private static String Middle;
    private static String Inner;
    private static ArrayList<Character> plug1 = new ArrayList<>(10);
    private static ArrayList<Character> plug2 = new ArrayList<>(10);
    
    // Constructor that initializes the inner, middle, and outer rotors
    public AdvEnigma(String in, String mid, String out){
        Inner = in;
        Middle = mid;
        Outer = out;
    }
    
    // Method that encrypts the character that it receives 
    public char encode(char ch){
            char newCh = checkPlug(Character.toUpperCase(ch));
            int index = Inner.indexOf(newCh);
            char out = Outer.charAt(index);
            index = Middle.indexOf(out);
            out = Outer.charAt(index);
            rotate();
            out = checkPlug(out);
            if(Character.isUpperCase(ch))
                return out;
            else
                return Character.toLowerCase(out);

    }
    
    // Rotates the inner, middle, and outer rotor in a lock-step
    public void rotate(){
       Inner = Inner.charAt(Inner.length()-1) + Inner.substring(0, (Inner.length() - 1));
       if(Inner.charAt(0) == '#'){
            Middle = Middle.charAt(Middle.length()-1) + Middle.substring(0, (Middle.length() - 1));
            if(Middle.charAt(0) == '#')
                Outer = Outer.charAt(Outer.length()-1) + Outer.substring(0, (Outer.length() - 1));
       }
    }
    
    // Asks the user for the plugboard setting and adds the characters to two
    // arrayLists
    public void setPlug(){
        String plug = "";
        while(plug.length() != 29){
                plug = JOptionPane.showInputDialog("Enter the plugboard setting"
                        + " for day:");
                if (plug.length() != 29) JOptionPane.showMessageDialog(null, 
                        "Incorrect plugboard length.");
        }
                
        for(int i = 0; i < plug.length(); i++){
            if(i == 0 || i % 3 == 0){
                plug1.add(plug.charAt(i));
            }
            if(i == 1 || i % 3 == 1){
                plug2.add(plug.charAt(i));
            }
        }
    }
    
    // Checks if the character is part of the plugboard settings and if it is
    // then it transcodes it
    public char checkPlug(char ch){
        if(plug1.contains(ch))
            return plug2.get(plug1.indexOf(ch));
        else if(plug2.contains(ch))
            return plug1.get(plug2.indexOf(ch));
        else
            return ch;
    }
    
    // Method that decrypts the character that it receives
    public char decode(char ch){
            char newCh = checkPlug(Character.toUpperCase(ch));
            int index = Outer.indexOf(newCh);
            char out = Middle.charAt(index);
            index = Outer.indexOf(out);
            out = Inner.charAt(index);
            rotate();
            out = checkPlug(out);
            if(Character.isUpperCase(ch))
                return out;
            else
                return Character.toLowerCase(out);
    }
}
