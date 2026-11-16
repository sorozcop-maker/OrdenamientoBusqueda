import java.util.Scanner;

public class Main {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        int n = leerEntero("Digite la cantidad de palabras: ");

        String[] palabras = new String[n];
        llenarArreglo(palabras);

        menuOrdenamiento(palabras);
        menuBusqueda(palabras);
    }

    // Repite la pregunta hasta que el usuario digite un número entero positivo.
    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(entrada.nextLine().trim());
                if (valor > 0) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // Se ignora y se vuelve a preguntar.
            }
            System.out.println("Debe digitar un número entero mayor que 0.");
        }
    }

    // Pide al usuario cada palabra y la guarda en el arreglo.
    static void llenarArreglo(String[] palabras) {
        for (int i = 0; i < palabras.length; i++) {
            System.out.print("Palabra " + (i + 1) + ": ");
            palabras[i] = entrada.nextLine().trim();
        }
    }

    // Imprime el contenido actual del arreglo.
    static void mostrarArreglo(String[] palabras) {
        System.out.println(String.join(", ", palabras));
    }

    // Muestra el menú y ejecuta el algoritmo elegido.
    // Si la opción no es válida, vuelve a preguntar.
    static void menuOrdenamiento(String[] palabras) {
        while (true) {
            System.out.println("\nElija el algoritmo de ordenamiento:");
            System.out.println("1. Selección");
            System.out.println("2. Inserción");
            System.out.println("3. Burbuja");
            System.out.println("4. Mezcla");
            System.out.println("5. Rápido");
            int opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1: ordenamientoSeleccion(palabras); break;
                case 2: ordenamientoInsercion(palabras); break;
                case 3: ordenamientoBurbuja(palabras); break;
                case 4: ordenamientoMezcla(palabras); break;
                case 5: ordenamientoRapido(palabras); break;
                default:
                    System.out.println("Opción no válida.");
                    continue;
            }

            System.out.print("Arreglo ordenado: ");
            mostrarArreglo(palabras);
            return;
        }
    }

    // Permite buscar palabras en el arreglo ya ordenado, tantas veces como se quiera.
    static void menuBusqueda(String[] palabras) {
        while (true) {
            System.out.print("\n¿Desea buscar una palabra? (s/n): ");
            String respuesta = entrada.nextLine().trim().toLowerCase();

            if (!respuesta.equals("s")) {
                System.out.println("Programa finalizado.");
                return;
            }

            System.out.print("Palabra a buscar: ");
            String buscada = entrada.nextLine().trim();

            int posicion = busquedaBinaria(palabras, buscada);

            if (posicion == -1) {
                System.out.println("La palabra no se encontró en el arreglo.");
            } else {
                System.out.println("La palabra se encontró en la casilla: " + posicion);
            }
        }
    }

    // BÚSQUEDA BINARIA
    // Requiere que el arreglo esté ordenado. Devuelve la casilla donde está la
    // palabra, o -1 si no se encuentra.
    // Complejidad de tiempo: O(log n) en el peor caso y el esperado, porque en
    // cada paso se descarta la mitad de las casillas. En el mejor caso es O(1)
    // (la palabra está justo en el pivote inicial).
    // Complejidad de espacio: O(1), solo usa unas pocas variables.
    static int busquedaBinaria(String[] palabras, String buscada) {
        // 1. Se define el intervalo de búsqueda: todo el arreglo.
        int limiteMenor = 0;
        int limiteMayor = palabras.length - 1;

        // 2. Mientras el intervalo tenga casillas, se sigue buscando.
        while (limiteMenor <= limiteMayor) {

            // 3. Se elige el pivote en el centro del intervalo.
            int pivote = (limiteMenor + limiteMayor) / 2;

            // 4. Se compara la palabra buscada con la del pivote.
            int comparacion = buscada.compareTo(palabras[pivote]);

            if (comparacion == 0) {
                // 5a. Son iguales: se encontró la palabra.
                return pivote;
            } else if (comparacion < 0) {
                // 5b. La buscada es menor: se descarta la mitad derecha.
                limiteMayor = pivote - 1;
            } else {
                // 5c. La buscada es mayor: se descarta la mitad izquierda.
                limiteMenor = pivote + 1;
            }
        }

        // 6. Los límites se cruzaron: la palabra no está en el arreglo.
        return -1;
    }

    // ORDENAMIENTO POR SELECCIÓN
    // Complejidad de tiempo: O(n^2) en mejor, peor y caso esperado, porque
    // siempre recorre el resto del arreglo para buscar el mínimo.
    static void ordenamientoSeleccion(String[] palabras) {
        int n = palabras.length;

        // Cada pasada deja ordenada la casilla i. Se necesitan n - 1 pasadas.
        for (int i = 0; i < n - 1; i++) {
            int menor = i; // Se asume que el mínimo está en la casilla actual.

            // Se recorre la porción restante buscando la palabra menor.
            for (int j = i + 1; j < n; j++) {
                if (palabras[j].compareTo(palabras[menor]) < 0) {
                    menor = j;
                }
            }

            // Se intercambia la casilla actual con la del mínimo encontrado.
            String temp = palabras[i];
            palabras[i] = palabras[menor];
            palabras[menor] = temp;
        }
    }

    // ORDENAMIENTO POR INSERCIÓN
    // Complejidad de tiempo: O(n^2) en el peor caso y el esperado;
    // O(n) en el mejor caso (arreglo ya ordenado).
    static void ordenamientoInsercion(String[] palabras) {
        // La porción izquierda (0 a i - 1) siempre está ordenada.
        for (int i = 1; i < palabras.length; i++) {
            String actual = palabras[i]; // Palabra que se va a insertar.
            int j = i - 1;

            // Se desplazan a la derecha las palabras mayores que la actual,
            // de las casillas mayores a las menores para no sobrescribir datos.
            while (j >= 0 && palabras[j].compareTo(actual) > 0) {
                palabras[j + 1] = palabras[j];
                j--;
            }

            // Se inserta la palabra en el hueco que quedó libre.
            palabras[j + 1] = actual;
        }
    }

    // ORDENAMIENTO POR BURBUJA
    // Complejidad de tiempo: O(n^2) en el peor caso y el esperado;
    // O(n) en el mejor caso gracias a la bandera "huboCambio".
    static void ordenamientoBurbuja(String[] palabras) {
        int n = palabras.length;

        // Cada pasada deja la palabra mayor de la porción en su posición final.
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false;

            // Se comparan parejas adyacentes de la porción aún no ordenada.
            for (int j = 0; j < n - 1 - i; j++) {
                if (palabras[j].compareTo(palabras[j + 1]) > 0) {
                    String temp = palabras[j];
                    palabras[j] = palabras[j + 1];
                    palabras[j + 1] = temp;
                    huboCambio = true;
                }
            }

            // Si en una pasada no hubo cambios, el arreglo ya está ordenado.
            if (!huboCambio) {
                break;
            }
        }
    }

    // ORDENAMIENTO POR MEZCLA (MERGE SORT)
    // Idea: dividir el arreglo a la mitad, ordenar cada mitad y mezclarlas.
    // Complejidad de tiempo: O(n log n) en mejor, peor y caso esperado.
    // Complejidad de espacio: O(n), por los arreglos temporales al mezclar.
    static void ordenamientoMezcla(String[] palabras) {
        if (palabras.length < 2) {
            return;
        }
        mezclaRecursiva(palabras, 0, palabras.length - 1);
    }

    static void mezclaRecursiva(String[] palabras, int inicio, int fin) {
        // Caso base: una sola casilla ya está ordenada.
        if (inicio >= fin) {
            return;
        }

        // 1. Se divide el rango en dos mitades.
        int medio = (inicio + fin) / 2;

        // 2. Se ordena cada mitad de forma recursiva.
        mezclaRecursiva(palabras, inicio, medio);
        mezclaRecursiva(palabras, medio + 1, fin);

        // 3. Se mezclan las dos mitades ya ordenadas.
        combinar(palabras, inicio, medio, fin);
    }

    // Mezcla dos mitades ordenadas: [inicio, medio] y [medio + 1, fin].
    static void combinar(String[] palabras, int inicio, int medio, int fin) {
        String[] temp = new String[fin - inicio + 1];
        int i = inicio;     // Posición actual en la mitad izquierda.
        int j = medio + 1;  // Posición actual en la mitad derecha.
        int k = 0;          // Posición actual en el arreglo temporal.

        // Se toma siempre la menor de las dos palabras al frente de cada mitad.
        while (i <= medio && j <= fin) {
            if (palabras[i].compareTo(palabras[j]) <= 0) {
                temp[k++] = palabras[i++];
            } else {
                temp[k++] = palabras[j++];
            }
        }

        // Se copian las palabras que sobraron de la mitad que no se terminó.
        while (i <= medio) {
            temp[k++] = palabras[i++];
        }
        while (j <= fin) {
            temp[k++] = palabras[j++];
        }

        // Se copia el resultado ordenado de vuelta al arreglo original.
        for (k = 0; k < temp.length; k++) {
            palabras[inicio + k] = temp[k];
        }
    }

    // ORDENAMIENTO RÁPIDO (QUICKSORT)
    // Idea: elegir un pivote, dejar a su izquierda las palabras menores y a su
    // derecha las mayores, y repetir el proceso en cada lado.
    // Complejidad de tiempo: O(n log n) en el mejor caso y el esperado;
    // O(n^2) en el peor caso (por ejemplo, arreglo ya ordenado con este pivote).
    // Complejidad de espacio: O(log n) esperado, por las llamadas recursivas.
    static void ordenamientoRapido(String[] palabras) {
        rapidoRecursivo(palabras, 0, palabras.length - 1);
    }

    static void rapidoRecursivo(String[] palabras, int inicio, int fin) {
        // Caso base: con una casilla o ninguna no hay nada que ordenar.
        if (inicio >= fin) {
            return;
        }

        // 1. Se particiona y se obtiene la posición final del pivote.
        int posicionPivote = particionar(palabras, inicio, fin);

        // 2. Se ordenan recursivamente los lados izquierdo y derecho del pivote.
        rapidoRecursivo(palabras, inicio, posicionPivote - 1);
        rapidoRecursivo(palabras, posicionPivote + 1, fin);
    }

    // Usa la última casilla como pivote y deja las menores a su izquierda.
    static int particionar(String[] palabras, int inicio, int fin) {
        String pivote = palabras[fin];
        int i = inicio - 1; // Última posición de la zona de palabras menores.

        for (int j = inicio; j < fin; j++) {
            if (palabras[j].compareTo(pivote) < 0) {
                i++;
                String temp = palabras[i];
                palabras[i] = palabras[j];
                palabras[j] = temp;
            }
        }

        // Se coloca el pivote en su posición final, justo después de las menores.
        String temp = palabras[i + 1];
        palabras[i + 1] = palabras[fin];
        palabras[fin] = temp;

        return i + 1;
    }
}