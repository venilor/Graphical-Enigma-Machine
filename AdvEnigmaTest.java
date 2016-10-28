package pkg.pex2;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * Complete Enigma machine with 5 interchangeable rotors and plugboard settings
 * @author Cody Brown
 */
public class AdvEnigmaTest {
    public static void main(String[] args){        
        //Prompt to encode or decode (from PH2 Solution by Dean Bushey)
            Object[] options = {"Encode", "Decode"};
            int response = JOptionPane.showOptionDialog(null, "Do you want to encode or decode?",
                            null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, null);

            //Get the input file from the user
            JFileChooser inputFile = new JFileChooser();
            inputFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter text = new FileNameExtensionFilter("Text", "txt", "text");
            inputFile.setFileFilter(text);
            inputFile.showOpenDialog(null);
            File fileI = inputFile.getSelectedFile();
            String infile = fileI.getAbsolutePath();

            //Set the output file for the user (from PH2 Solution by Dean Bushey)
            String outfile = inputFile.getCurrentDirectory().toString();
            if (response == 0) outfile += ("\\ENCODED_" + fileI.getName());
            else outfile = outfile += ("\\DECODED_" + fileI.getName());
	    
            // Gets the rotor settings for the day
            String in = getRotor("Inner");
            String mid = getRotor("Middle");
            String out = getRotor("Outer");
            
            
            // Declares AdvEnigma object
	    AdvEnigma coder = new AdvEnigma(in, mid, out);
            
            // Asks for the plugboard settings
            coder.setPlug();
            
            // Reads the file and encodes or decodes it, if file is not found
            // then it returns "File not found"
	    try {
	        FileReader inReader = new FileReader(infile);
	        PrintStream outWriter = new PrintStream(outfile);
	        while (inReader.ready()) {
                    char ch = (char)inReader.read();
                    // Checks if the character is a letter or if it is a hashtag
                    if(Character.isLetter(ch) || ch == '#'){
                        if (response == 0) {
                            outWriter.print( coder.encode(ch));
                        }
                        else if (response == 1) {
                            outWriter.print( coder.decode(ch));
                        }
                    }
                    // Checks if the character is a new line/space/tab or other
                    // symbol then it returns the character
                    else if(Character.isWhitespace(ch) || !Character.isLetter(ch))
                        outWriter.print(ch);
	        }
	        inReader.close();
	        outWriter.close();
	    }
	    catch (java.io.IOException e) {
	        System.out.println("FILE NOT FOUND");
	    }
            
            JOptionPane.showMessageDialog(null, "The file has been saved under the same folder with the prefix:"
				+ "\nENCODED_ or DECODED_ respectively");
    }
    
    // Method that gets the rotor settings from the user
    public static String getRotor(String in){
        String rotor ="";
        Object[] options = {"1","2","3","4","5"};
        int choice = JOptionPane.showOptionDialog(null, "Select the "+in+
                " rotor string:\n1 = #GNUAHOVBIPWCJQXDKRYELSZFMT\n" +
                "2 = #EJOTYCHMRWAFKPUZDINSXBGLQV\n" +
                "3 = #BDFHJLNPRTVXZACEGIKMOQSUWY\n" +
                "4 = #KPHDEACVTWQMYNLXSURZOJFBGI\n" +
                "5 = #NDYGLQICVEZRPTAOXWBMJSUHKF", "Select Rotor", 
                JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                options[4]);
        
        switch(choice){
            case 0:
                rotor = "#GNUAHOVBIPWCJQXDKRYELSZFMT";
                break;
            case 1:
                rotor = "#EJOTYCHMRWAFKPUZDINSXBGLQV";
                break;
            case 2:
                rotor = "#BDFHJLNPRTVXZACEGIKMOQSUWY";
                break;
            case 3:
                rotor = "#KPHDEACVTWQMYNLXSURZOJFBGI";
                break;
            case 4:
                rotor = "#NDYGLQICVEZRPTAOXWBMJSUHKF";
                break;
            case -1:
                System.exit(choice);
        }
        return rotor;
    }
}


