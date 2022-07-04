package morsetranslator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.StringIndexOutOfBoundsException;

/**
 *
 * @author David-Sanma
 */
public class MorseTranslator {
    public static final String SEP = File.separator;
    public static void main(String[] args) {
        Scanner key = new Scanner(System.in);
        //El primer char es para los espacios, el segundo es para los espacios concatenados en la lectura de ficheros,
        //y el tercero es un salto de línea para la lectura.
        String characters[] = {" # ","|# ","l#","A#.-","B#-...","C#-.-.","D#-..","E#.","F#..-.","G#--.","H#....","I#..","J#.---", 
        "K#-.-","L#.-..","M#--","N#-.","O#---","P#.--.","Q#--.-","R#.-.","S#...","T#-","U#..-","V#...-","W#.--",
        "X#-..-","Y#-.--","Z#--..","1#.----","2#..---","3#...--","4#....-","5#.....","6#-....","7#--...","8#---..",
        "9#----.","0#-----",".#.-.-.-",",#--..--","?#..--..","!#-.-.--","'#.----.","(#-.--.",")#-.--.-",
        "&#.-...",":#---...",";#-.-.-.","/#-..-.","_#..--.-","=#-...-","+#.-.-.","-#-....-","$#...-..-","@#.--.-"};
        String loadPath, savePath = "", dialog;
        int opt = -1;
        while(opt != 1) {
            System.out.println("---------------------------------");
            System.out.println("---------TRADUCTOR MORSE---------");
            System.out.println("---------------------------------");
            System.out.println("1. Salir");
            System.out.println("2. Cifrar un archivo (de *.txt a *.mor)");
            System.out.println("3. Descifrar un archivo (de *.mor a *.txt)");
            System.out.print("Ingrese una opción: ");
            
            try {
                opt = key.nextInt();
                switch(opt) {
                    case 1:
                        System.out.println("_________________________________");
                        System.out.println("Ha cerrado el programa.\n");
                        break;
                    case 2:
                        System.out.println("Ruta del archivo *.txt que desee cifrar: ");
                        System.out.print("V?: ");
                        key.nextLine();
                        loadPath = key.nextLine();
                        if(!MorseTranslator.loadPathExists(loadPath, true)){
                            System.out.println("_________________________________");
                            System.out.println("ERROR: El archivo no existe o no contiene la extensión *.txt\n");
                        }else{
                            System.out.println("Introduzca una ruta en la que desee guardar el archivo *.odt: ");
                            System.out.print("V?: ");
                            savePath = key.nextLine();
                            if(!MorseTranslator.savePathExists(savePath, true)) {
                                System.out.println("_________________________________");
                                System.out.println("ERROR: El archivo no contiene la extensión *.odt\n");
                            }else{ 
                                dialog = MorseTranslator.textToMorseLoadAndSave(loadPath, characters, savePath);
                                if(dialog.equals("0")){
                                    System.out.println("_________________________________");
                                    System.out.println("Se ha grabado correctamente la información en el archivo.\n");
                                }else if(dialog.equals("-1")){
                                    System.out.println("_________________________________");
                                    System.out.println("La información no se ha grabado correctamente.\n");
                                }else{
                                    System.out.println("_________________________________");
                                    System.out.println(dialog);
                                    System.out.println("Debe revisar el *.txt\n");
                                }
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Ruta del archivo *.odt que desee descifrar: ");
                        System.out.print("V?: ");
                        key.nextLine();
                        loadPath = key.nextLine();
                        if(!MorseTranslator.loadPathExists(loadPath, false)){
                            System.out.println("_________________________________");
                            System.out.println("ERROR: El archivo no existe o no contiene la extensión *.odt\n");
                        }else{
                            System.out.println("Introduzca una ruta en la que desee guardar el archivo *.txt: ");
                            System.out.print("V?: ");
                            savePath = key.nextLine();
                            if(!MorseTranslator.savePathExists(savePath, false)) {
                                System.out.println("_________________________________");
                                System.out.println("ERROR: El archivo no contiene la extensión *.txt\n");
                            }else{
                                dialog = MorseTranslator.morseToTextLoadAndSave(loadPath, characters, savePath);
                                if(dialog.equals("0")) {
                                    System.out.println("_________________________________");
                                    System.out.println("Se ha grabado correctamente la información en el archivo.\n");
                                }else if(dialog.equals("-1")){
                                    System.out.println("_________________________________");
                                    System.out.println("La información no se ha grabado correctamente.\n");
                                }else{
                                    System.out.println("_________________________________");
                                    System.out.println(dialog);
                                    System.out.println("Debe revisar el *.odt\n");
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("_________________________________");
                        System.out.println("ERROR: Opción no existente.\n");
                }
            }catch(InputMismatchException ime) {
                System.out.println("\n_________________________________");
                System.out.println("ERROR: El valor introducido no corresponde con el tipo.\n");
                key.nextLine();
            }
        }  
    }
    
    public static boolean loadPathExists(String loadPath, boolean action) {
        boolean pathExists = false;
        File file = new File(loadPath);
        if(action == true) {
            if(file.exists() && loadPath.endsWith(".txt")) {pathExists = true;}
        }else{
            if(file.exists() && loadPath.endsWith(".odt")) {pathExists = true;}
        }
        return pathExists;
    }
    
    public static boolean savePathExists(String savePath, boolean action) {
        boolean pathExists = true;
        int longOfPath = savePath.length();
        if(!savePath.substring(longOfPath - 5, longOfPath).startsWith(".")) {
            try {
                String simplePath = savePath.substring(0, savePath.lastIndexOf(SEP));
                File file = new File(simplePath);
                if(action == true) {
                    if(!savePath.endsWith(".odt")) {
                        pathExists = false;
                    }else if(!file.exists()) {
                        file.mkdirs();
                        pathExists = true;
                    }
                }else{
                    if(!savePath.endsWith(".txt")) {
                        pathExists = false;
                    }else if(!file.exists()) {
                        file.mkdirs();
                        pathExists = true;
                    }
                }
            }catch(StringIndexOutOfBoundsException se) {
                pathExists = false;
            }
        }else{pathExists = false;}
        return pathExists;
    }
    
    public static String textToMorseLoadAndSave(String loadPath, String characters[], String savePath) {
        String ret = "0";
        int col = 0, row = 0;
        char wrongChar = 0;
        try {
            FileReader fReader = new FileReader(loadPath);
            BufferedReader bReader = new BufferedReader(fReader);
            int posOfLine, posOfArray, nrow = 0;
            String translateLine, search;
            String line = bReader.readLine();
            FileWriter fWriter = new FileWriter(savePath);
            BufferedWriter bWriter = new BufferedWriter(fWriter);
            while(line != null) {
                //System.out.println("NROW: " + nrow);
                translateLine = "";
                line = line.toUpperCase(); 
                line = line.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U");
                line = line.replace("À","A").replace("È","E").replace("Ì","I").replace("Ò","O").replace("Ù","U");
                for(posOfLine = 0; posOfLine < line.length(); posOfLine ++) {
                    //System.out.println("POSOFLINE: " + posOfLine);
                    for(posOfArray = 0; posOfArray < characters.length; posOfArray ++) {
                        //System.out.println("ARRAYPOS: " + characters[posOfArray]);
                        if(characters[posOfArray].charAt(0) == line.charAt(posOfLine)) {
                            //Es charAt(0) porque coges la primera posición del String, que corresponde con el caracter principal.
                            //System.out.println("MATCH");
                            // Este if hace que si se encuentra un espacio entre palabra y palabra no le concatene el espacio
                            // extra de entre letra y letra. De no hacerlo tendrá 3 espacios entre palabra y palabra, uno el 
                            // por defecto del *.txt, y luego el entre letra y letra duplicado ya que interpreta el del *.txt
                            // como un char.
                            search = characters[posOfArray].substring(2, characters[posOfArray].length());
                            if(line.charAt(posOfLine) != characters[0].charAt(0)) {
                                translateLine += search + " ";
                                // El espacio del final corresponde con el "largo" que hay entre letra y letra.
                            }else{translateLine += search;}
                            //System.out.println(translateLine);
                            posOfArray = characters.length; // Romper el bucle
                        }else if(posOfArray == characters.length - 1) { // No hace falta poner && y negar la condición principal porque es redundante.
                            bWriter.write("\n\nERROR: Char no admitido en la columna " + (posOfLine + 2) + " y en la fila " + (nrow + 1) + ".\nMirar en el *.txt");
                            bWriter.close();
                            fWriter.close();
                            col = posOfLine + 2;
                            row = nrow + 1;
                            wrongChar = line.charAt(posOfLine);
                            //posOfArray = characters.length;
                        }
                    }
                }
                translateLine += " "; // Espacio entre línea y línea.
                bWriter.write(translateLine + "\n");
                nrow ++;
                line = bReader.readLine();
            }
            bWriter.close();
            fWriter.close();
            bReader.close();
            fReader.close();
        }catch(FileNotFoundException e) {
            ret = "-1"; // Error archivo no encontrado/dañado.
        }catch(IOException ioe) {
            ret = "ERROR: Char no admitido en la columna " + col + " y en la fila " + row + ": " + wrongChar;
            // Error de lectura/escritura.
        }
        return ret;
    }
    
    public static String morseToTextLoadAndSave(String loadPath, String characters[], String savePath) {
        String ret = "0";
        try{
            FileReader fReader = new FileReader(loadPath);
            BufferedReader bReader = new BufferedReader(fReader);
            String translateLine;
            int nrow = 0;
            FileWriter fWriter = new FileWriter(savePath);
            BufferedWriter bWriter = new BufferedWriter(fWriter);
            String morChar, additionalLine = "", space;
            String line = bReader.readLine();
            while(line != null) {
                additionalLine += line.replace("  "," | ") + "l ";
                line = bReader.readLine();
            }
            String lettersOfLine[] = additionalLine.split(" ");
            while(additionalLine != null) {
                //System.out.println("NROW: " + nrow);
                translateLine = "";
                for(int letter = 0; letter < lettersOfLine.length; letter ++) {
                    //System.out.println("LETTER: " + lettersOfLine[letter]);
                    for(int posOfArray = 0; posOfArray < characters.length; posOfArray ++) {
                        //System.out.println("CHARACTER: " + characters[posOfArray]);
                        morChar = characters[posOfArray].substring(2, characters[posOfArray].length());
                        space = characters[posOfArray].charAt(0) + ""; // El caracter del espacio no sigue el formato morse, sigue el formato de texto plano.
                        if(lettersOfLine[letter].equals(morChar) || lettersOfLine[letter].equals(space)) {
                            //System.out.println("MATCH");
                            translateLine += characters[posOfArray].replace("|"," ").replace("l","\n").charAt(0);
                            //System.out.println(translateLine);
                            posOfArray = characters.length;
                        }else if(posOfArray == characters.length - 1 && !lettersOfLine[letter].equals(morChar)) {
                            //bWriter.write("\n\nERROR: Char no admitido en la columna " + (letter + 2) + " y en la fila " + (nrow + 1));
                            bWriter.write("\n\nERROR: Error de formato.");
                            bWriter.close();
                            fWriter.close();
                        }
                    }
                }
                bWriter.write(translateLine);
                nrow ++;
                additionalLine = bReader.readLine();
            }
            bWriter.close();
            fWriter.close();
            bReader.close();
            fReader.close();        
        }catch(FileNotFoundException e) {
            ret = "-1";
        }catch(IOException ioe) {
            ret = "ERROR: Error de formato";
        }
        return ret;
    }
}
