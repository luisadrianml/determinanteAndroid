/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.algoritmos.determinanterina;

/**
 *
 * @author LuisAdrián
 */
public class Matrices {
    
        public static int[] sumaFila(int[] suma, int[][] matriz) {
        for (int fila=0;fila<suma.length;fila++) {
                for (int column=0;column<suma.length;column++) {
                    suma[fila] = suma[fila] + matriz[fila][column];
                }
            }
        
        return suma;
    }
    
 
    
    public static int[] sumaColumna(int[] suma, int[][] matriz) {
        for (int column=0;column<suma.length;column++) {
                for (int fila=0;fila<suma.length;fila++) {
                    suma[column] = suma[column] + matriz[fila][column];
                }
            }
        return suma;
    }
    
    public static int sumaInternaVector(int[] vector) {
        int suma = 0;
        for (int i=0; i < vector.length; i++) {
            suma = suma + vector[i];
        }
        return suma;
    }
    
    public static int[] productoColumna(int[] producto, int[][] matriz) {
        producto = llenarVector(producto, 1);
        for (int column=0;column<producto.length;column++) {
                for (int fila=0;fila<producto.length;fila++) {
                    producto[column] = producto[column] * matriz[fila][column];
                }
            }
        
        return producto;
    }
    
    public static int[] productoFila(int[] producto, int[][] matriz) {
        producto = llenarVector(producto, 1);
        for (int fila=0;fila<producto.length;fila++) {
                for (int column=0;column<producto.length;column++) {
                    producto[fila] = producto[fila] * matriz[fila][column];
                }
            }
        
        return producto;
    }
    

    public static void imprimirVector(int[] vector) {
        for (int i=0;i<vector.length;i++) {
            System.out.println("| "+vector[i] + " |");
        }
        System.out.println("");
    }
    
    public static double determinante(int[][] matriz) {
    double det;
    
    if(matriz.length==2) {
        det=(matriz[0][0]*matriz[1][1])-(matriz[1][0]*matriz[0][1]);
        return det;
    }
    
    double suma=0;
    
    for(int i=0; i<matriz.length; i++){
        int[][] nm = new int[matriz.length-1][matriz.length-1];
        for(int j=0; j<matriz.length; j++){
            if(j!=i){
                for(int k=1; k<matriz.length; k++){
                    int indice=-1;
                    if(j<i) {
                        indice=j;
                    }
                    else if(j>i) {
                        indice=j-1;
                    }
                nm[indice][k-1]= matriz[j][k];
                }
            }
        }
        if(i%2==0)
        suma+=matriz[i][0] * determinante(nm);
        else
        suma-=matriz[i][0] * determinante(nm);
    }
    return suma;
}public static int[] llenarVector(int[] vector, int contenido) {
            for (int fila=0;fila<vector.length;fila++) {
                vector[fila] = contenido;
            }
        return vector;
    }

public static int[][] cambiarMatriz(int[][] matriz, int operacion) {
        int[][] temp = matriz;
        switch (operacion) {
            case 1: {
                // raiz
                for (int fila=0;fila<matriz.length;fila++) {
                    for (int column=0;column<matriz.length;column++) {
                         matriz[fila][column] = (int) Math.sqrt(temp[fila][column]); 
                    }
                }
                return matriz;
            }
            case 2: {
                // logaritmo natural
                for (int fila=0;fila<matriz.length;fila++) {
                    for (int column=0;column<matriz.length;column++) {
                         matriz[fila][column] = (int) Math.log(temp[fila][column]); 
                    }
                }
                return matriz;
            }
        }
        
        
        return matriz;
    }

    public static int productoInternaVector(int[] vector) {
        int producto = 1;
        for (int i=0;i<vector.length;i++) {
            producto = producto*vector[i];
        }
        return producto;
    }
}
