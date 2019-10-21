package rooms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                for(int k=0; k<2; k++) {
                    for (int l=0; l<habitaciones; l++) {
                        recorrido[k][l] = false;
                    }
                }
                pasos[i][j] = this.cambiar_habitacion(i, j, matriz, recorrido);
            }
        }
        System.out.println(Arrays.deepToString(pasos));
        
        int max_primer_piso = Arrays.stream(pasos[0]).max().getAsInt();
        int max_segundo_piso = Arrays.stream(pasos[1]).max().getAsInt();
        int cuartos_visitados = (max_primer_piso >= max_segundo_piso) ? max_primer_piso : max_segundo_piso;

        try {
            this.guardar_respuesta(cuartos_visitados);
        } catch (IOException ex) {
            Logger.getLogger(archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int cambiar_habitacion(int piso, int habitacion, int[][] matriz, boolean[][] recorrido) {
        recorrido[piso][habitacion] = true;
//        System.out.println(Arrays.deepToString(recorrido));
        int[] direccion = new int[4];
        // derecha
        direccion[0] = (habitacion+1 < recorrido[piso].length && !recorrido[piso][habitacion+1]) ? direccion[0] = this.cambiar_habitacion(piso, habitacion+1, matriz, recorrido) : 0;
        // arriba
        direccion[1] = (piso == 0 && !recorrido[piso+1][habitacion] && matriz[piso][habitacion] == 1) ? this.cambiar_habitacion(piso+1, habitacion, matriz, recorrido) : 0;
        // izquierda
        direccion[2] = (habitacion-1 >= 0 && !recorrido[piso][habitacion-1]) ? this.cambiar_habitacion(piso, habitacion-1, matriz, recorrido) : 0;
        // abajo
        direccion[3] = (piso-1 >= 0 && !recorrido[piso-1][habitacion] && matriz[piso][habitacion] == 1) ? this.cambiar_habitacion(piso-1, habitacion, matriz, recorrido) : 0;
        
        return 1 + Arrays.stream(direccion).max().getAsInt();
    }
    
    public void guardar_respuesta(int resultado) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
        writer.append(String.valueOf(resultado));
        writer.newLine();
        writer.close();
    }
}
