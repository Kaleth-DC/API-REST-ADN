package com.jkdc.adn.app.object;

import org.springframework.stereotype.Component;
import org.json.*;

@Component
public class ADN {
	
	private final int Lengthfillmin = 4;
	//private final byte[] Caracteres = {65,84,67,71};//(A,T,C,G)
	
	//mutaciones
	private boolean horizontal = false;
	private boolean vertical = false;
	private boolean oblicua = false;
	
	//funcion mutation
	public boolean hasMutation(String[] dna){
		
		
		//resultado de el analisis
		boolean respuesta = false;
		
		//matriz donde se desplegaran los datos
		byte[][] matriz = new byte[dna.length][];
		
		//vandera de verificacion de cadena
		boolean validez = true;
		
		//resultados de mutacion en los analisis
		this.horizontal = false;
		this.vertical = false;
		this.oblicua = false;
		
		//ancho y largo de la matriz
		int ancho = 0;
		int largo = dna.length;
		
		//puntero para recorrer la matriz
		int p = 0;
		
		
		//Descomponer y validar la matriz
		for(String Hcadena:dna){
			matriz[p] = Hcadena.getBytes();
			System.out.println(Hcadena);
			
			//validar el tamaño de la matriz
			if(ancho == 0){
				ancho = Hcadena.length();
			}
				
			if(ancho != Hcadena.length()){
				validez = false;
			}
			
			//validar caracteres de la matriz
			for(byte letra:matriz[p]){										//Descomponer cada fila del arregloen una cadena de bytes para compararlos
				if(!(letra==65 || letra==84 || letra==67 || letra==71)){ 	//Si algun caracter es diferente diferente al ASCII CODE(65,84,67,71) = {A,T,C,G)};
					validez = false;										//no sera valido el arreglo ingresado
					//no cumple con la condicion para tener una mutacion
				}
			}
			p++;															//mover puntero
		}
		
		//si es valida la cadena para tener mutacion
		if(validez){
			//buscar mutaciones en horizontal
			horizontal = CheckHorizontal(matriz,ancho,largo);
			
			//buscar mutaciones en vertical
			vertical = CheckVertical(matriz,ancho,largo);
			
			//buscar mutaciones en oblicua
			oblicua = CheckOblicua(matriz,ancho,largo);
			
			System.out.println("validez : "+validez);
			System.out.println("Horizontal : "+horizontal);
			System.out.println("Vertical : "+vertical);
			System.out.println("oblicua : "+oblicua);
			
			//verificacion de analisis
			if(horizontal || vertical || oblicua){
				respuesta = true;
			}
		}else{ 													//si no es valida la cadena para tener mutacion
			respuesta = false;
		}
		return respuesta;
	}
	
	public boolean CheckHorizontal(byte[][]matriz,int ancho, int largo){
		boolean r = false;
		
		int Ti = (ancho-this.Lengthfillmin)+1; 					// Total de iteraciones
		
		for(int y=0;y<largo;y++){
				
			if(Ti>=1){
				for(int x=0;x<Ti;x++){
					
					if(matriz[y][x]==matriz[y][x+1] 
						&& matriz[y][x+1]==matriz[y][x+2] 
						&& matriz[y][x+2]==matriz[y][x+3]){ 	//Si existen 4 valores secuenciales iguales
						
						r = true;								//Existe una mutacion Horizontal
						break;									//fin alalisis
					}
				}
			}else{
				r = false;										//no existe una mutacion horizontal
			}
		}
		return r;
	}
	
	public boolean CheckVertical(byte[][]matriz,int ancho, int largo){
		boolean r = false;
		
		int Ti = (largo-this.Lengthfillmin)+1; // Total de iteraciones
		
		for(int x=0;x<ancho;x++){
				
			if(Ti>=1){
				for(int y=0;y<Ti;y++){
					
					if(matriz[y][x]==matriz[y+1][x] 
						&& matriz[y+1][x]==matriz[y+2][x] 
						&& matriz[y+2][x]==matriz[y+3][x]){ 	//Si existen 4 valores secuenciales iguales
						
						r = true;											//Existe una mutacion Horizontal
						break;
					}
				}
			}else{
				r = false;					//no existe una mutacion horizontal
			}
		}
		return r;
	}
	
	public boolean CheckOblicua(byte[][] matriz,int ancho, int largo){
		boolean r = false;
		
		int min = 4;
		int TaiX;
		int TaiY;
		int N;
		
		int DA;
		
		int SL;
		int SA;
		
		
		int[][] pbeta;
		int[][] palfa;
		
		
		TaiY = (largo-min);
		TaiX = Math.abs(largo-(ancho+TaiY));
		N = TaiY+TaiX+1;
		
		pbeta = new int[N][2];
		palfa = new int[N][2];
		
		DA = N-1;
		
		//P
		if(largo==ancho){
			SL = DA/2;
			SA = DA/2;
			
			pbeta[0][0] = 0;
			pbeta[0][1] = 0;
			
			palfa[0][0] = ancho-1;
			palfa[0][1] = 0;
			
		}else if(largo>ancho){
			
			SL = largo-min;
			SA = DA-SL;
			
			pbeta[0][0] = 0;
			pbeta[0][1] = 0;
			
			palfa[0][0] = ancho-1;
			palfa[0][1] = 0;
			
			
		}else{
			SA = ancho-min;
			SL = DA-SA;
			
			pbeta[0][0] = 0;
			pbeta[0][1] = 0;
			
			palfa[0][0] = ancho-1;
			palfa[0][1] = 0;
			
		}
		
		int px=1;
		int py=1;
		
		for(int z=0;z<SA;z++){
			pbeta[z+1][0] = px;
			pbeta[z+1][1] = 0;
			
			palfa[z+1][0] = ancho-px-1;
			palfa[z+1][1] = 0;
			
			px++;
		}
		
		for(int z=SA;z<SA+SL;z++){
			pbeta[z+1][0] = 0;
			pbeta[z+1][1] = py;
			
			palfa[z+1][0] = ancho-1;
			palfa[z+1][1] = py;
			
			py++;
		}
		
		System.out.println("--------------------------");
		
		for(int i=0;i<N;i++){
			System.out.println("Xbeta : "+pbeta[i][0]+" : Ybeta : "+pbeta[i][1]);
			
			int poblicua = 1;
			
			int pix = pbeta[i][0];
			int piy = pbeta[i][1];
			
			int coincidencias = 1;
			byte caracterAnterior = matriz[piy][pix];
			
			System.out.printf("%c",caracterAnterior);
			
			boolean o=false;
			//recorrido del trazo oblicuo
			while(true){
				
				pix = pbeta[i][0]+poblicua;
				piy = pbeta[i][1]+poblicua;
				
				if(pix<ancho && piy<largo){
					System.out.printf("%c",matriz[piy][pix]);
					if(matriz[piy][pix]==caracterAnterior){
						coincidencias++;
					}else{
						coincidencias=1;
					}
					
					caracterAnterior=matriz[piy][pix];
					
					if(coincidencias==this.Lengthfillmin){
						r=true;
						o=true;
					}
				}else{
					break;
				}
				
				
				poblicua++;
			}
			System.out.print(" > "+o);
			System.out.println("");
		}
		System.out.println("--------------------------");
		for(int i=0;i<N;i++){
			System.out.println("Xalfa : "+palfa[i][0]+" : Yalfa : "+palfa[i][1]);
			
			int poblicua = 1;
			
			int pix = palfa[i][0];
			int piy = palfa[i][1];
			
			int coincidencias = 1;
			byte caracterAnterior = matriz[piy][pix];
			
			System.out.printf("%c",caracterAnterior);
			
			boolean o = false;
			//recorrido del trazo oblicuo
			while(true){
				
				pix = palfa[i][0]-poblicua;
				piy = palfa[i][1]+poblicua;
				
				if(pix>=0 && piy<largo){
					System.out.printf("%c",matriz[piy][pix]);
					if(matriz[piy][pix]==caracterAnterior){
						coincidencias++;
					}else{
						coincidencias=1;
					}
					
					caracterAnterior=matriz[piy][pix];
					
					if(coincidencias==this.Lengthfillmin){
						r=true;
						o=true;
					}
				}else{
					break;
				}
				
				
				poblicua++;
			}
			System.out.print(" > "+o);
			System.out.println("");
		}
		return r;
	}
	
}
