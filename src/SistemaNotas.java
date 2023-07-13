import java.util.Arrays;
import java.util.Scanner;
public class SistemaNotas {
    private static String[] nombresEstudiantes;
    private static String[] secciones;
    private static int[] grados;
    private static String[][] areas;
    private static String[][] competencias;
    private static double[][][] notas;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de estudiantes:");
        int cantidadEstudiantes = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        nombresEstudiantes = new String[cantidadEstudiantes];
        secciones = new String[cantidadEstudiantes];
        grados = new int[cantidadEstudiantes];
        areas = new String[cantidadEstudiantes][];
        competencias = new String[cantidadEstudiantes][];
        notas = new double[cantidadEstudiantes][][];

        // Pedir datos de entrada para cada estudiante
        for (int i = 0; i < cantidadEstudiantes; i++) {
            System.out.println("Ingrese el nombre del estudiante #" + (i + 1) + ":");
            nombresEstudiantes[i] = scanner.nextLine();

            System.out.println("Ingrese la sección del estudiante #" + (i + 1) + ":");
            secciones[i] = scanner.nextLine();

            System.out.println("Ingrese el grado del estudiante #" + (i + 1) + ":");
            grados[i] = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            System.out.println("Ingrese el área de curso para el estudiante #" + (i + 1) + ":");
            String areaString = scanner.nextLine();
            areas[i] = areaString.split(",");

            System.out.println("Ingrese las competencias del curso para el estudiante #" + (i + 1) + ":");
            String competenciasString = scanner.nextLine();
            competencias[i] = competenciasString.split(",");

            notas[i] = new double[competencias[i].length][5];

            // Pedir las notas para cada competencia
            for (int j = 0; j < competencias[i].length; j++) {
                System.out.println("Ingrese las 5 notas para la competencia " + competencias[i][j] + " del estudiante #" + (i + 1) + ":");
                for (int k = 0; k < 5; k++) {
                    notas[i][j][k] = scanner.nextDouble();
                }
                scanner.nextLine(); // Limpiar el buffer
            }
        }

        // Mostrar el menú
        int opcion;
        do {
            System.out.println();
            System.out.println("====== Menú ======");
            System.out.println("1. Mostrar reporte de notas");
            System.out.println("2. Comparar notas y obtener primer puesto, tercio superior y quinto superior");
            System.out.println("3. Salir");
            System.out.println("==================");
            System.out.println();
            System.out.println("Seleccione una opción:");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    mostrarReporteNotas();
                    break;
                case 2:
                    compararNotas();
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 3);

        scanner.close();
    }

    public static void mostrarReporteNotas() {
        System.out.println("======= Reporte de Notas =======");

        for (int i = 0; i < nombresEstudiantes.length; i++) {
            System.out.println();
            System.out.println("Estudiante: " + nombresEstudiantes[i]);
            System.out.println("Sección: " + secciones[i]);
            System.out.println("Grado: " + grados[i]);

            System.out.println("Competencias:");
            for (int j = 0; j < competencias[i].length; j++) {
                System.out.println((j + 1) + ". " + competencias[i][j] + ": " + calcularPromedio(notas[i][j]) + " (" + convertirNota(calcularPromedio(notas[i][j])) + ")");
            }
        }
    }

    public static void compararNotas() {
        System.out.println("======= Comparación de Notas =======");

        // Calcular promedio final de cada estudiante
        double[] promediosFinales = new double[nombresEstudiantes.length];
        for (int i = 0; i < nombresEstudiantes.length; i++) {
            double promedioTotal = 0;
            for (int j = 0; j < competencias[i].length; j++) {
                promedioTotal += calcularPromedio(notas[i][j]);
            }
            promediosFinales[i] = promedioTotal / competencias[i].length;
        }

        // Obtener el primer puesto
        int indicePrimerPuesto = obtenerIndicePrimerPuesto(promediosFinales);

        // Obtener el tercio superior
        int[] indicesTercioSuperior = obtenerIndicesTercioSuperior(promediosFinales);

        // Obtener el quinto superior
        int[] indicesQuintoSuperior = obtenerIndicesQuintoSuperior(promediosFinales);

        // Imprimir resultados
        System.out.println("Primer Puesto:");
        System.out.println("Estudiante: " + nombresEstudiantes[indicePrimerPuesto]);
        System.out.println("Promedio Final: " + promediosFinales[indicePrimerPuesto] + " (" + convertirNota(promediosFinales[indicePrimerPuesto]) + ")");

        System.out.println();

        System.out.println("Tercio Superior:");
        for (int indice : indicesTercioSuperior) {
            System.out.println("Estudiante: " + nombresEstudiantes[indice]);
            System.out.println("Promedio Final: " + promediosFinales[indice] + " (" + convertirNota(promediosFinales[indice]) + ")");
        }

        System.out.println();

        System.out.println("Quinto Superior:");
        for (int indice : indicesQuintoSuperior) {
            System.out.println("Estudiante: " + nombresEstudiantes[indice]);
            System.out.println("Promedio Final: " + promediosFinales[indice] + " (" + convertirNota(promediosFinales[indice]) + ")");
        }
    }


    public static double calcularPromedio(double[] notas) {
        double sum = 0;
        for (double nota : notas) {
            sum += nota;
        }
        return sum / notas.length;
    }

    public static String convertirNota(double nota) {
        if (nota >= 18 && nota <= 20) {
            return "AD (Logro destacado)";
        } else if (nota >= 14 && nota <= 17) {
            return "A (Logro esperado)";
        } else if (nota >= 11 && nota <= 13) {
            return "B (En proceso)";
        } else {
            return "C (En inicio)";
        }
    }

    public static int obtenerIndicePrimerPuesto(double[] promediosFinales){
        int indicePrimerPuesto = 0;
        double promedioMaximo = promediosFinales[0];
        for (int i = 1; i < promediosFinales.length; i++) {
            if (promediosFinales[i] > promedioMaximo) {
                promedioMaximo = promediosFinales[i];
                indicePrimerPuesto = i;
            }
        }
        return indicePrimerPuesto;
    }

    public static int[] obtenerIndicesTercioSuperior(double[] promediosFinales) {
        int tercioSuperior = promediosFinales.length / 3;
        int[] indicesTercioSuperior = new int[tercioSuperior];
        double[] copiaPromedios = promediosFinales.clone();
        Arrays.sort(copiaPromedios);
        for (int i = promediosFinales.length - 1; i >= promediosFinales.length - tercioSuperior; i--) {
            double promedio = copiaPromedios[i];
            for (int j = 0; j < promediosFinales.length; j++) {
                if (promediosFinales[j] == promedio) {
                    indicesTercioSuperior[promediosFinales.length - 1 - i] = j;
                    break;
                }
            }
        }
        return indicesTercioSuperior;
    }

    public static int[] obtenerIndicesQuintoSuperior(double[] promediosFinales) {
        int quintoSuperior = promediosFinales.length / 5;
        int[] indicesQuintoSuperior = new int[quintoSuperior];
        double[] copiaPromedios = promediosFinales.clone();
        Arrays.sort(copiaPromedios);
        for (int i = promediosFinales.length - 1; i >= promediosFinales.length - quintoSuperior; i--) {
            double promedio = copiaPromedios[i];
            for (int j = 0; j < promediosFinales.length; j++) {
                if (promediosFinales[j] == promedio) {
                    indicesQuintoSuperior[promediosFinales.length - 1 - i] = j;
                    break;
                }
            }
        }
        return indicesQuintoSuperior;
    }

}