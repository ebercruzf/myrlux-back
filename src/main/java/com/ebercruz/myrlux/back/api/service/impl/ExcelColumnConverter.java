package com.ebercruz.myrlux.back.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Conversor de números a referencias de columnas de Excel y viceversa.
 * Utiliza Pattern Matching de Java 21 para manejar diferentes tipos de columnas.
 *
 * @author ebercruz.com
 * @version 1.0
 * @since 2024-10-21
 *
 * Entorno de desarrollo:
 * - IDE: IntelliJ IDEA 2024.1.4 (Community Edition)
 * - JDK: Java 21
 *
 * Ejemplo de uso:
 * <pre>
 *     1 -> A
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB
 * </pre>
 *
 * @see ExcelColumn
 * @see SingleColumn
 * @see MultiColumn
 */
public class ExcelColumnConverter {

    // Interfaz sellada que define el contrato para los tipos de columnas
    // Usa 'permits' para restringir las implementaciones posibles a SingleColumn y MultiColumn
    // Ventajas de usar Interfaz sellada (sealed), sealed interface con permits:
    // 1. Seguridad: Solo SingleColumn y MultiColumn pueden implementar esta interfaz
    // 2. Pattern Matching: Permite switch expressions sin default case -> más seguro
    // 3. Mantenibilidad: El compilador señala todos los lugares que necesitan actualización
    // 4. Control: Previene implementaciones inesperadas, a diferencia de interfaces normales
    //
    // Ejemplo: switch(columna) {
    //    case SingleColumn(char c) -> // proceso para columna simple
    //    case MultiColumn(String s) -> // proceso para columna múltiple
    // }
    public sealed interface ExcelColumn permits ExcelColumnConverter.SingleColumn, ExcelColumnConverter.MultiColumn {
        // Método factory que utiliza pattern matching para crear el tipo apropiado de columna
        static ExcelColumn of(String column) {
            if (column == null) {
                throw new IllegalArgumentException("La columna no puede ser null");
            }
            if (column.isEmpty()) {
                throw new IllegalArgumentException("La columna no puede estar vacía");
            }
            return column.length() == 1
                    ? new SingleColumn(column.charAt(0))
                    : new MultiColumn(column);
        }
    }

    // Record para representar columnas simples (A-Z)
    // Utiliza validación en el constructor compacto del record
    public static record SingleColumn(char letter) implements ExcelColumn {
        public SingleColumn {
            if (letter < 'A' || letter > 'Z') {
                throw new IllegalArgumentException("Letra inválida: " + letter);
            }
        }
    }

    // Record para representar columnas múltiples (AA-ZZ, AAA-ZZZ, etc.)
    // Utiliza expresiones regulares para validar el formato
    public static record MultiColumn(String letters) implements ExcelColumn {
        public MultiColumn {
            if (letters == null || !letters.matches("[A-Z]+")) {
                throw new IllegalArgumentException("Formato de columna inválido: " + letters);
            }
        }
    }

    // Array con las letras del alfabeto para las conversiones
    private static final char[] LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * Convierte un número a su representación en columna Excel usando recursión
     * Ejemplo: 27 -> AA (primero calcula el cociente y luego añade el residuo)
     */
    public static String numeroAColumnaExcel(int numero) {
        if (numero < 1) {
            throw new IllegalArgumentException("El número debe ser positivo");
        }

        if (numero <= LETRAS.length) {
            return String.valueOf(LETRAS[numero - 1]);
        }

        int decremented = numero - 1;
        int cociente = decremented / LETRAS.length;
        int residuo = decremented % LETRAS.length;
        return numeroAColumnaExcel(cociente) + LETRAS[residuo];
    }

    /**
     * Convierte una columna Excel a su número correspondiente
     * Utiliza pattern matching con switch expressions de Java 21
     * Ejemplo: AA -> 27 (procesa cada carácter multiplicando por 26)
     */
    public static int columnaExcelANumero(String columna) {
        // Pattern matching en el switch para manejar diferentes tipos de columnas
        return switch (ExcelColumn.of(columna)) {
            case SingleColumn(char c) -> c - 'A' + 1;
            case MultiColumn(String letters) -> {
                int resultado = 0;
                for (char c : letters.toCharArray()) {
                    resultado = resultado * 26 + (c - 'A' + 1);
                }
                yield resultado;
            }
        };
    }

    /**
     * Genera una lista de todas las columnas hasta el límite especificado
     */
    public static List<String> generarColumnas(int limite) {
        List<String> columnas = new ArrayList<>();
        for (int i = 1; i <= limite; i++) {
            columnas.add(numeroAColumnaExcel(i));
        }
        return columnas;
    }

    /**
     * Muestra la información detallada de las columnas generadas
     * Incluye estadísticas sobre tipos de columnas
     */
    public static void mostrarInformacionColumnas(List<String> columnas) {
        System.out.println("\nInformación de columnas:");
        System.out.println("------------------------");

        // Muestra cada columna con su posición y tipo
        for (int i = 0; i < columnas.size(); i++) {
            String columna = columnas.get(i);
            int numero = i + 1;
            String tipo = columna.length() == 1 ? "Simple" : "Múltiple";

            System.out.printf("Posición %3d: %3s (Tipo: %s)%n",
                    numero, columna, tipo);
        }

        // Cálculo y muestra de estadísticas usando streams
        long columnasSimples = columnas.stream().filter(c -> c.length() == 1).count();
        long columnasMultiples = columnas.size() - columnasSimples;

        System.out.println("\nResumen:");
        System.out.println("--------");
        System.out.printf("Total de columnas: %d%n", columnas.size());
        System.out.printf("Columnas simples (A-Z): %d%n", columnasSimples);
        System.out.printf("Columnas múltiples (AA+): %d%n", columnasMultiples);
    }

    /**
     * Punto de entrada principal que maneja la interacción con el usuario
     * Incluye manejo de errores y bucle principal de ejecución
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nConversor de Columnas Excel");
                System.out.println("-------------------------");
                System.out.print("Introduce un número (o 0 para salir): ");

                int numero = scanner.nextInt();

                if (numero == 0) {
                    System.out.println("¡Hasta luego!");
                    break;
                }

                if (numero < 0) {
                    System.out.println("Por favor, introduce un número positivo.");
                    continue;
                }

                // Procesa y muestra los resultados
                List<String> columnas = generarColumnas(numero);
                mostrarInformacionColumnas(columnas);

                // Información adicional para números mayores que 26
                if (numero > 26) {
                    System.out.println("\nInformación adicional:");
                    System.out.println("--------------------");
                    System.out.println("Has excedido el alfabeto simple (A-Z)");
                    System.out.printf("Combinaciones adicionales necesarias: %d%n",
                            numero - 26);
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error de entrada. Por favor, introduce un número válido.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }

        scanner.close();
    }
}