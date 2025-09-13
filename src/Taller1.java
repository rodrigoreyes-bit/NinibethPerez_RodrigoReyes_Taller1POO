/* Nombre: Ninibeth Pérez Cortés, RUT: 21.787.686-9, Carrera: ICCI | Nombre: Rodrigo Reyes Alfaro, RUT: 22.123.808-7, Carrera: ICCI
 * Nombre: Ninibeth Pérez Cortés, RUT: 21.787.686-9, Carrera: ICCI | Nombre: Rodrigo Reyes Alfaro, RUT: 22.123.808-7, Carrera: ICCI
 * Nombre: Ninibeth Pérez Cortés, RUT: 21.787.686-9, Carrera: ICCI | Nombre: Rodrigo Reyes Alfaro, RUT: 22.123.808-7, Carrera: ICCI
 * Nombre: Ninibeth Pérez Cortés, RUT: 21.787.686-9, Carrera: ICCI | Nombre: Rodrigo Reyes Alfaro, RUT: 22.123.808-7, Carrera: ICCI
 * Nombre: Ninibeth Pérez Cortés, RUT: 21.787.686-9, Carrera: ICCI | Nombre: Rodrigo Reyes Alfaro, RUT: 22.123.808-7, Carrera: ICCI
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;

public class Taller1 {
	
	public static int cantExp() throws IOException {
		//Esta funcion entrega la cantidad de experimentos que hay en el archivo de experimentos.txt
		int cantidad = 0;
		
		File arch = new File("experimentos.txt");
		Scanner lector = new Scanner(arch);
		
		while (lector.hasNextLine()) {
			cantidad++;
			lector.nextLine();
		}
		lector.close();
		return cantidad;
	}
	
	public static int cantMetricas() throws IOException {
		//Entrega la cantidad de metricas
		int cantidad = 0;
		
		File arch = new File("metricas.txt");
		Scanner lector = new Scanner(arch);
		
		while (lector.hasNextLine()) {
			cantidad++;
			lector.nextLine();
		}
		lector.close();
		return cantidad;
	}
	
	public static void agregarExp(String [] listaExperimentos) throws IOException {
		//Este procedimiento agrega los experimentos de arch experimentos.txt a la lista de experimentos
		File arch = new File("experimentos.txt");
		Scanner lector = new Scanner(arch);
		
		int indice = 0;
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String [] partes = linea.split(";");
			String exp = partes[0];
			listaExperimentos[indice] = exp;
			indice++;
		}
		lector.close();
	}

	public static void agregarDescripción(String [] listaDescripción) throws IOException {
		//Este procedimiento agrega los experimentos de arch experimentos.txt a la lista de experimentos
		File arch = new File("experimentos.txt");
		Scanner lector = new Scanner(arch);
		
		int indice = 0;
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String [] partes = linea.split(";");
			String desc = partes[1];
			listaDescripción[indice] = desc;
			indice++;
		}
		lector.close();
	}
	
	public static void matrizDeConfusion (int[][] matriz, String[] listaDeExperimentos, int cantidadexp) throws IOException {
		//Este procedimiento recibe los datos que se requieren para poder armar la matriz de confusion, en base a ellos crea un ciclo que permite
		// que la función (RellenoMatrizDeConfusion) pueda interpretar que datos y experimentos se encuentran para el cálculo
		for(int i = 0; i < cantidadexp; i++) {
			RellenoMatrizdeConfusion(matriz, listaDeExperimentos[i], i);			
		}
	}
	
	public static void RellenoMatrizdeConfusion(int[][] MatrizRellena,String experimento, int identificador) throws FileNotFoundException {
		//En base al indice indicado por matrizDeConfusion, el cual va a tener el esquema expN° abre el archivo predicciones.txt y compara el experimento
		//con los datos arrojados por el archivo para así poder calcular y añadir a la matriz de confusion los datos de cada métrica
		File arch = new File("predicciones.txt");
		Scanner lector = new Scanner(arch);
		
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String [] partes = linea.split(";");
			String idExperimento = partes[0];
			
			if (idExperimento.equals(experimento)) {
				int valorReal = Integer.valueOf(partes[1]);
				int valorPredicho = Integer.valueOf(partes[2]);
				
				if (valorReal == 1 && valorPredicho == 1) { 
					//TP
					MatrizRellena[identificador][0] += 1;
				} else if (valorReal == 0 && valorPredicho == 1) {
					//FP
					MatrizRellena[identificador][1] += 1;
				} else if (valorReal == 0 && valorPredicho == 0) {
					//TN
					MatrizRellena[identificador][2] += 1;
				} else if (valorReal == 1 && valorPredicho == 0) {
					//FN
					MatrizRellena[identificador][3] += 1;
				}
			}
		} lector.close();
	}
	
	public static double accuracy(int [][]matriz,int indentificador) {
		//Esta función calcula el accuracy
		double tp = matriz[indentificador][0];
		double fp = matriz[indentificador][1];
		double tn = matriz[indentificador][2];
		double fn = matriz[indentificador][3];
		double accuracy = (tp + tn) / (tp + fp + tn + fn);
		return accuracy;
	}
	
	public static double precision(int [][] matriz,int indentificador) {
		//Esta función calcula la precision
		double tp = matriz[indentificador][0];
		double fp = matriz[indentificador][1];
		double precision = tp / (tp + fp);
		return precision;
	}
	
	public static double recall(int[][] matriz,int indentificador) {
		//Esta función calcula el recall
		double tp = matriz[indentificador][0];
		double fn = matriz[indentificador][3];
		double recall = tp / (tp + fn) ;
		return recall;
	}
	
	public static double f1Score(double precision, double recall) {
		//Esta función calcula el f1 score
		double f1Score = 2 * (precision * recall) / (precision + recall);
		return f1Score;
	}
	
	public static void RellenarMatrizDeMetricas(int cantidadDeExperimentos, int[][] MatrizConfusión, double[][] MatrizMetrica) throws IOException {
		for(int i = 0; i < cantidadDeExperimentos; i++){
			//Este procedimiento rellena una matriz llamada matriz metricas con los datos de accuracy, precision, recall y f1-score
			//siendo las filas cada experimento y las columnas las métricas
			double accuracy = accuracy(MatrizConfusión,i);
			double precision = precision(MatrizConfusión,i);
			double recall = recall(MatrizConfusión,i);
			double f1Score = f1Score(precision, recall);
			MatrizMetrica[i][0] = accuracy;
			MatrizMetrica[i][1] = precision;
			MatrizMetrica[i][2] = recall;
			MatrizMetrica[i][3] = f1Score;
		}
	}
	
	public static void verMatrizCompleta (int cantidadDeExperimentos, int[][] MatrizConfusión, String[] listaDescripciones, double[][] MatrizMetrica) {
		//Imprime la matriz completa de todas las metricas de los experimentos
		System.out.println("ID | Descripción | TP FP TN FN | Accuracy | Precision | Recall | F1");
		System.out.println("--------------------------------------------------------------------");
		for(int i = 0; i < cantidadDeExperimentos; i++){
			System.out.println("Exp" + (i + 1) + " | " + listaDescripciones[i] + " | " + MatrizConfusión[i][0] + "  " + MatrizConfusión[i][1] + "  " + MatrizConfusión[i][2] + "  " + MatrizConfusión[i][3] + " | " + MatrizMetrica[i][0] + " | " + MatrizMetrica[i][1] + " | " + MatrizMetrica[i][2] + " | " + MatrizMetrica[i][3]);
		}
	}
	
	public static void mejorF1Score (double[][] MatrizMetricas, int cantidadExperimentos) {
		//esta lista imprime el mejor f1-Score
		double mejorScore = 0;
		int mejorExp = 0;
		for (int i = 0;i < cantidadExperimentos; i++) {
			double f1Score = MatrizMetricas[i][3];
			if (f1Score > mejorScore) {
				mejorScore = f1Score;
				mejorExp = i + 1 ;
			}
		}
		System.out.println("--------------------------------------------------------------------" + "\n");
		System.out.println("El mejor F1-Score es el: Exp" + mejorExp + " con " + mejorScore + "\n");
	}
	
	public static void promedioGlobalMetricas (double[][] MatrizMetricas, int CantidadExperimentos) {
		//Este procedimiento calcula e imprime el promedio de cada métrica
		double sumaAccuracy = 0;
		double sumaPrecision = 0;
		double sumaRecall = 0;
		double sumaF1 = 0;
		
		for (int i = 0; i < CantidadExperimentos; i++) {
			sumaAccuracy += MatrizMetricas[i][0];
			sumaPrecision += MatrizMetricas[i][1];
			sumaRecall += MatrizMetricas[i][2];
			sumaF1 += MatrizMetricas[i][3];
		}
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Promedio Accuracy: " + (sumaAccuracy/4));
		System.out.println("Promedio Precision: " + (sumaPrecision/4));
		System.out.println("Promedio Recall: " + (sumaRecall/4));
		System.out.println("Promedio F1-Score: " + (sumaF1/4));
		System.out.println("--------------------------------------------------------------------" + "\n");
	}

	public static void compararExperimentos(int exp1, int exp2, double[][] MatrizMetricas, String [] listaExperimentosRellena, int [][] MatrizDeConfusión) {
		//Compara ambos experimentos a través del indice de cada uno, imprimiendo los resultados de cada uno
		System.out.println("--------------------------------------------------------------------");
		String primerExp = listaExperimentosRellena[exp1];
		String segundoExp = listaExperimentosRellena[exp2];
		
		System.out.println("Comparación " + primerExp + " vs " + segundoExp);
		//Matriz de confusion
		System.out.println("Matriz de confusion:");
		System.out.println(primerExp + ": " + "TP = " + MatrizDeConfusión[exp1][0] + " FP = " + MatrizDeConfusión[exp1][1] + "\n     " + " TN = " + MatrizDeConfusión[exp1][2] + " FN = " + MatrizDeConfusión[exp1][3] + "\n");
		System.out.println(segundoExp + ": " + "TP = " + MatrizDeConfusión[exp2][0] + " FP = " + MatrizDeConfusión[exp2][1] + "\n     " + " TN = " + MatrizDeConfusión[exp2][2] + " FN = " + MatrizDeConfusión[exp2][3] + "\n");

		//Metricas
		System.out.println("Metricas:");
		System.out.println(primerExp + ": Acc = " + MatrizMetricas[exp1][0] + " | P = " + MatrizMetricas[exp1][1] + " | R = " + MatrizMetricas[exp1][2] + " | F1 = " + MatrizMetricas[exp1][3]);
		System.out.println(segundoExp + ": Acc = " + MatrizMetricas[exp2][0] + " | P = " + MatrizMetricas[exp2][1] + " | R = " + MatrizMetricas[exp2][2] + " | F1 = " + MatrizMetricas[exp2][3] + "\n");
		
		double estadisticas1 = (MatrizMetricas[exp1][0] + MatrizMetricas[exp1][1] + MatrizMetricas[exp1][2]);
		double estadisticas2 = (MatrizMetricas[exp2][0] + MatrizMetricas[exp2][1] + MatrizMetricas[exp2][2]);
		double estadisticaf1 = MatrizMetricas[exp1][3];
		double estadisticaf2 = MatrizMetricas[exp2][3];
		
		//comparar y sacar conclusiones
		System.out.println("Comparación a realizar: Exp" + (exp1 + 1) + " Vs Exp" + (exp2 + 1));
		System.out.println("Calculando....");
		System.out.println("Metricas Generales Acumuladas (Accuracy + Precision + Recall): " + "Experimento" + (exp1 + 1) + ": " + estadisticas1 + " / " + "Experimento" + (exp2 +1) + ": " + estadisticas2 );
		System.out.println("Metricas F1-Score : Experimento" + (exp1 + 1) + ": " + estadisticaf1 + " / " + "Experimento" + (exp2 +1) + ": " + estadisticaf2);
		if ((estadisticaf1 > estadisticaf2) || (estadisticas1 > estadisticas2)) {
			System.out.println("El experimento " + (exp1+1) + " es estadisticamente superior." );
			
		} else {
			System.out.println("El experimento " + (exp2+1) + " es estadisticamente superior." );
		}
	}
	
	public static void listaDeExperimentos(String [] listaExperimentos, String [] listaDescripciónRellena) {
		//Este procedimiento imprime la lista de experimentos junto a su descripción 
		System.out.println("--------------------------------------------------------------------");
		int indice = 1;
		for (int i = 0; i < listaExperimentos.length; i++) {
			System.out.println(indice + ") " + listaExperimentos[i] + " | " + listaDescripciónRellena[i]);
			indice++;
		}
		System.out.println("--------------------------------------------------------------------" + "\n");
		}
	
	public static void mostrarMatrizDeUnExp(int[][] MatrizConfusión, int opcion) { 
		//Esta funcion, como dice su nombre, muestra la matriz del experimento deseado segun el indice que se le mande (int opcion)
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Exp" + (opcion + 1) + " : " + "TP = " + MatrizConfusión[opcion][0] + " FP = " + MatrizConfusión[opcion][1] + "\n      " + " TN = " + MatrizConfusión[opcion][2] + " FN = " + MatrizConfusión[opcion][3]);
		System.out.println("--------------------------------------------------------------------");
	}
	
	public static void mostrarMetricasDeUnExp(double[][] MatrizMetricas, int opcion) throws IOException {
		//Esta función muestra las métricas de un solo experimento, a través de la matriz de métricas
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Exp" + (opcion + 1) + ": Accuracy = " + MatrizMetricas[opcion][0] + " | Precision = " + MatrizMetricas[opcion][1] + " | Recall = " + MatrizMetricas[opcion][2] + " | F1-Score = " + MatrizMetricas[opcion][3]);
		System.out.println("--------------------------------------------------------------------");
	}
	
	public static void promedioAccuracy(double[][] MatrizMetricas, int CantidadExperimentos) throws IOException {
		//Este procedimiento calcula el promedio de todos los accuracy de los experimentos
		double PromedioAcurracy = 0;
		for(int i = 0;i< CantidadExperimentos;i+=1) {
			PromedioAcurracy += MatrizMetricas[i][0];
		}
		PromedioAcurracy = PromedioAcurracy / 4;
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Promedio de Accuracy: " + PromedioAcurracy);
		System.out.println("--------------------------------------------------------------------");
	}
	
	public static void RellenarMatrizCSV(int[][] RellenoMatrizCSV,int[][] MatrizConfusión, int Cantidadexp) throws IOException {
		//Este procedimiento rellena una nueva matriz a partir de verificacion_docente_confusiones.csv y además hace una comparación
		//con la matriz de confusión original
		BufferedReader csv = new BufferedReader(new FileReader("verificacion_docente_confusiones.csv"));
        String linea;
        int contador = 0;
        while ((linea = csv.readLine()) != null) {
            String[] datosMatrizCSV = linea.split(",");
            if(contador >=1) {
            	RellenoMatrizCSV[contador-1][0] = Integer.valueOf(datosMatrizCSV[1]);
              	RellenoMatrizCSV[contador-1][1] = Integer.valueOf(datosMatrizCSV[2]);
              	RellenoMatrizCSV[contador-1][2] = Integer.valueOf(datosMatrizCSV[3]);
              	RellenoMatrizCSV[contador-1][3] = Integer.valueOf(datosMatrizCSV[4]);
            } contador++;
        }
        mostrarMatriz(RellenoMatrizCSV, 4,"Matriz CSV:");
        mostrarMatriz(MatrizConfusión, 4, "Matriz Confusión:");
        
        boolean comparacion = true;
        for(int x = 0; x < Cantidadexp; x++) {
        	for(int y = 0; y < 4; y++) {
        		if (RellenoMatrizCSV[x][y] != MatrizConfusión[x][y]) {
        			comparacion = false;
        		
        		
        		}
        	}
        }
        if(comparacion == true) {
        	System.out.println("Las Matrices son identicas");
        }else {
        	System.out.println("Las Matrices son diferentes");
        }
        System.out.println("---------------------------------");
        csv.close();
	}
	
	public static void mostrarMatriz(int[][] Matriz, int CantidadExperimentos, String nombre) throws IOException {
		//Este procedimiento muestra la matriz de experimentos ordenadamente
		String linea;
		System.out.println(nombre);
		for(int x = 0; x < CantidadExperimentos; x++) {
			linea = "| ";
			
			for(int y = 0; y < 4; y++) {
				linea += (Matriz[x][y] + " | ");
			}
			System.out.println(linea);
			}
		System.out.println("---------------------------------");
		}
	
	public static void main(String[] args) throws IOException {
		int CantidadExperimentos = cantExp();
		int CantidadMetricas = cantMetricas();
		String [] listaExperimentosRellena = new String[CantidadExperimentos];
		String [] listaDescripciónRellena = new String[CantidadExperimentos];
		
		agregarExp(listaExperimentosRellena);
		agregarDescripción(listaDescripciónRellena);
		
		int [][] MatrizDeConfusión = new int[CantidadExperimentos][4];
		double[][] MatrizDeMetricas = new double[CantidadExperimentos][CantidadMetricas];
		
		matrizDeConfusion(MatrizDeConfusión, listaExperimentosRellena, CantidadExperimentos);
		RellenarMatrizDeMetricas(CantidadExperimentos, MatrizDeConfusión, MatrizDeMetricas);
		
		//MENU
		int opcion;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("           !!Bienvenido!!");
			System.out.println("-----------MENU PRINCIPAL-----------");
			System.out.println("1) Admin");
			System.out.println("2) Usuario");
			System.out.println("3) Salir");
			System.out.println("Elija una opción: ");
			opcion = Integer.valueOf(scan.nextLine());
			
			if (opcion == 1) {
				menuAdmin();
				int opcionAdmin = Integer.valueOf(scan.nextLine());
				while (opcionAdmin != 6) {
					if (opcionAdmin == 1) {
						verMatrizCompleta(cantExp(), MatrizDeConfusión, listaDescripciónRellena, MatrizDeMetricas);
						System.out.println("--------------------------------------------------------------------");
					} else if (opcionAdmin == 2) {
						mejorF1Score(MatrizDeMetricas, CantidadExperimentos);
						System.out.println("--------------------------------------------------------------------");
						
					} else if (opcionAdmin == 3) {
						promedioGlobalMetricas(MatrizDeMetricas,CantidadExperimentos);
						
					} else if (opcionAdmin == 4) {
						System.out.println("Numero - Experimentos:");
						mostrarExperimentos(listaExperimentosRellena);
						int exp1;
						int exp2;
						do {
							System.out.println("Ingrese número del primer experimento a comparar: ");
							exp1 = Integer.valueOf(scan.nextLine()) - 1;
							System.out.println("Ingrese número del segundo experimento a comparar: ");
							exp2 = Integer.valueOf(scan.nextLine()) - 1;
						}while(exp1 < 0 || exp1 > CantidadExperimentos || exp2 < 0 || exp2 > CantidadExperimentos);
						
						System.out.println(" ");
						compararExperimentos(exp1, exp2, MatrizDeMetricas, listaExperimentosRellena, MatrizDeConfusión);
						System.out.println("--------------------------------------------------------------------");
						
					} else if (opcionAdmin == 5) {
						
						int[][] MatrizCsv = new int[CantidadExperimentos][4];
						RellenarMatrizCSV(MatrizCsv, MatrizDeConfusión, CantidadExperimentos);
					}
					menuAdmin();
					opcionAdmin = Integer.valueOf(scan.nextLine());
				}
			}
			
			if (opcion == 2) {
				menuUsuario();
				int opcionUsuario = Integer.valueOf(scan.nextLine());
				while (opcionUsuario != 5) {
					if (opcionUsuario == 1) {
						listaDeExperimentos(listaExperimentosRellena, listaDescripciónRellena);
						
					} else if (opcionUsuario == 2) {
						mostrarExperimentos(listaExperimentosRellena);
						int numExp;
						do {
							System.out.println("Ingrese el número del experimento deseado: ");
							numExp = Integer.valueOf(scan.nextLine()) - 1;
						}while(numExp < 0 || numExp > CantidadExperimentos);
						mostrarMatrizDeUnExp(MatrizDeConfusión, numExp);

						
					} else if (opcionUsuario == 3) {
						mostrarExperimentos(listaExperimentosRellena);
						int numExp;
						do {
							System.out.println("Ingrese el número del experimento deseado: ");
							numExp = Integer.valueOf(scan.nextLine()) - 1;
						}while(numExp < 0 || numExp > CantidadExperimentos);
						
						mostrarMetricasDeUnExp(MatrizDeMetricas, numExp);
						
					} else if (opcionUsuario == 4) {
						promedioAccuracy(MatrizDeMetricas, CantidadExperimentos);
					}
					menuUsuario();
					opcionUsuario = Integer.valueOf(scan.nextLine());
				}
			}
		} while (opcion != 3);	
		System.out.println("           !!Hasta luego!!");
		scan.close();
	}
	
	public static void mostrarExperimentos(String [] listaExperimentos) {
		//Imprime la lista de experimentos
		int indice = 1;
		for (String experimento : listaExperimentos) {
			System.out.println(indice + ") " + experimento );
			indice++;
		}
	}
	
	public static void menuAdmin() {
		//Imprime el menu del administrador
		System.out.println("1) Ver matriz completa de métricas");
		System.out.println("2) Identificar experimento con mejor F1-Score");
		System.out.println("3) Calcular promedio global de cada métrica");
		System.out.println("4) Comparar dos experimentos lado a lado");
		System.out.println("5) Comparar CSV con matriz de confusión generada");
		System.out.println("6) Volver");
	}
	
	public static void menuUsuario() {
		//Imprime el menu del usuario
		System.out.println("1) Ver lista de experimentos");
		System.out.println("2) Mostrar matriz de confusión de un experimento (TP,FP,TN,FN)");
		System.out.println("3) Ver métricas de un experimento (Accuracy, Precision, Recall, F1)");
		System.out.println("4) Ver promedio de Accuracy de todos los modelos");
		System.out.println("5) Volver");
	}
		
}			
