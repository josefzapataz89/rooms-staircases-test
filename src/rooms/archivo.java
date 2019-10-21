package rooms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author jofer
 */
public class archivo {
       
    public archivo() throws IOException {
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            String iteraciones;
            iteraciones = scanner.nextLine();
            System.out.println(iteraciones);
            
            for (int i=0; i < Integer.parseInt(iteraciones); i++) {
                String habitaciones;
                String estructura;
                habitaciones = scanner.nextLine();
                estructura = scanner.nextLine();
                System.out.println(habitaciones + " " + estructura);
                
                
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public 
    
    public void guardar_respuesta(char[] resultado) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", false));
        writer.append(String.valueOf(resultado));
        writer.close();
    }
}
