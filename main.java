import java.util.*;
import java.io.File;

public class main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Crear los mapas directamente en lugar de usar MapFactory
        Map<String, Pokemon> allPokemons = new HashMap<>();
        Map<String, Pokemon> userCollection = new HashMap<>();

        // Ruta al archivo CSV
        String filePath = "Datos.csv";
        File file = new File(filePath);
        
        // Verificar si el archivo existe
        if (!file.exists()) {
            System.err.println("ERROR: El archivo '" + filePath + "' no existe.");
            System.err.println("Ruta absoluta esperada: " + file.getAbsolutePath());
            System.out.println("\nPor favor, verifica la ruta del archivo CSV e intenta de nuevo.");
            return;
        }
        
        System.out.println("Cargando datos de Pokémon desde: " + filePath);
        leercsv.loadPokemonData(filePath, allPokemons);
        
        if (allPokemons.isEmpty()) {
            System.err.println("No se pudo cargar ningún Pokémon. El programa no puede continuar.");
            return;
        }
        
        System.out.println("Pokémon cargados en el sistema: " + allPokemons.size());
        System.out.println("Primeros 5 Pokémon disponibles:");
        int count = 0;
        for (String key : allPokemons.keySet()) {
            if (count < 5) {
                System.out.println("- '" + key + "'");
                count++;
            } else {
                break;
            }
        }

        usospokemon usospokemon = new usospokemon(allPokemons, userCollection);

        // Variable para controlar el bucle del menú
        boolean running = true;
        
        while (running) {
            System.out.println("\nSelecciona una opción:");
            System.out.println("1. Agregar Pokémon a la colección del usuario");
            System.out.println("2. Mostrar datos de un Pokémon");
            System.out.println("3. Mostrar la colección de Pokémon del usuario ordenada por tipo1");
            System.out.println("4. Mostrar todos los Pokémon existentes ordenados por tipo1");
            System.out.println("5. Mostrar Pokémon por habilidad");
            System.out.println("6. Salir");
            
            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingresa un número válido.");
                scanner.nextLine();  // Limpiar el buffer
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("Ingresa el nombre del Pokémon a agregar:");
                    String nameToAdd = scanner.nextLine();
                    usospokemon.addUserPokemon(nameToAdd);
                    break;
                case 2:
                    System.out.println("Ingresa el nombre del Pokémon para mostrar sus datos:");
                    String nameToShow = scanner.nextLine();
                    usospokemon.showPokemonData(nameToShow);
                    break;
                case 3:
                    System.out.println("Colección de Pokémon del usuario ordenada por tipo1:");
                    usospokemon.showUserCollectionByType1();
                    break;
                case 4:
                    System.out.println("Todos los Pokémon existentes ordenados por tipo1:");
                    usospokemon.showAllByType1();
                    break;
                case 5:
                    System.out.println("Ingresa la habilidad para mostrar los Pokémon que la poseen:");
                    String abilityToShow = scanner.nextLine();
                    usospokemon.showPokemonsByAbility(abilityToShow);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    running = false;  // Cambiamos el valor para salir del bucle
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
        
        scanner.close();  // Cerramos el scanner al final del programa
    }
}