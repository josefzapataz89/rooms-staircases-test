package rooms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

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
            
            for (int i=0; i < Integer.parseInt(iteraciones); i++) {
                String habitaciones;
                String estructura;
                habitaciones = scanner.nextLine();
                estructura = scanner.nextLine();
                
                if (Integer.parseInt(habitaciones) == estructura.length()) {
                    System.out.println(habitaciones + " " + estructura);
                    this.matrices(Integer.parseInt(habitaciones), estructura);
                }
                else {
                    System.out.println("error en estructura");
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void matrices(int habitaciones, String estructura) {
        int[][] pasos;
        pasos = new int[2][habitaciones];
        
        int[][] matriz;
        matriz = new int[2][habitaciones];
        
        boolean[][] recorrido;
        recorrido = new boolean[2][habitaciones];
        
        for(int i=0; i<2; i++) {
            for (int j=0; j<habitaciones; j++) {
                pasos[i][j] = 0;
                matriz[i][j] = Integer.parseInt(String.valueOf(estructura.charAt(j)));
                recorrido[i][j] = false;
            }
        }

        this.caminar(habitaciones, pasos, matriz, recorrido);
    }
    
    public void caminar(int habitaciones, int[][] pasos, int[][] matriz, boolean[][] recorrido) {
        for(int i=0; i<2; i++) {
            for (int j=0; j<habitaciones; j++) {
                pasos[i][j] = this.cambiar_habitacion(i, j, matriz, recorrido);
                System.out.println("desde piso " + i + " hab " + j + " visita " + pasos[i][j]);
            }
        } 
    }
    
    public int cambiar_habitacion(int piso, int habitacion, int[][] matriz, boolean[][] recorrido) {
        recorrido[piso][habitacion] = true;
        int[] direccion = new int[4];
        direccion[0] = 0;
        direccion[1] = 0;
        direccion[2] = 0;
        direccion[3] = 0;

        // arriba
        if (piso == 0 && !recorrido[piso+1][habitacion] && matriz[piso][habitacion] == 1) {
            recorrido[piso+1][habitacion] = true;
            direccion[1] = this.cambiar_habitacion(piso+1, habitacion, matriz, recorrido);
        }
        // abajo
        if (piso-1 >= 0 && !recorrido[piso-1][habitacion] && matriz[piso][habitacion] == 1) {
            recorrido[piso-1][habitacion] = true;
            direccion[3] = this.cambiar_habitacion(piso-1, habitacion, matriz, recorrido);
        }

        // derecha
        if (habitacion+1 < recorrido[piso].length && !recorrido[piso][habitacion+1]) {
            recorrido[piso][habitacion+1] = true;
            direccion[0] = this.cambiar_habitacion(piso, habitacion+1, matriz, recorrido);
        }
        // izquierda
        if (habitacion-1 >= 0 && !recorrido[piso][habitacion-1]) {
            recorrido[piso][habitacion-1] = true;
            direccion[2] = this.cambiar_habitacion(piso, habitacion-1, matriz, recorrido);
        }
        
        return 1 + Arrays.stream(direccion).max().getAsInt();
    }
    
    public void guardar_respuesta(char[] resultado) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", false));
        writer.append(String.valueOf(resultado));
        writer.close();
    }
}
