import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class leercsv {

    public static void loadPokemonData(String filePath, Map<String, Pokemon> pokemonMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            int count = 0;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;  // Saltar la línea de encabezado
                    continue;
                }
                
                // Dividir la línea en campos, manejando casos especiales
                String[] fields = parseCsvLine(line);
                
                if (fields.length < 10) {
                    System.err.println("Línea incorrecta en el CSV (campos insuficientes): " + line);
                    continue;
                }
                
                try {
                    // Procesamos los campos con manejo de errores mejorado
                    String name = fields[0].trim();
                    
                    if (name.isEmpty()) {
                        System.err.println("Nombre de Pokémon vacío en línea: " + line);
                        continue;
                    }
                    
                    int pokedexNumber;
                    try {
                        pokedexNumber = Integer.parseInt(fields[1].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Error en número Pokédex, ignorando línea: " + line);
                        continue;
                    }
                    
                    String type1 = fields[2].trim();
                    if (type1.isEmpty()) {
                        type1 = "Unknown"; // Valor por defecto
                    }
                    
                    String type2 = fields[3].trim();
                    if (type2.isEmpty()) {
                        type2 = null;
                    }
                    
                    String classification = fields[4].trim();
                    
                    double height;
                    try {
                        height = Double.parseDouble(fields[5].trim());
                    } catch (NumberFormatException e) {
                        height = 0.0; // Valor por defecto
                    }
                    
                    double weight;
                    try {
                        weight = Double.parseDouble(fields[6].trim());
                    } catch (NumberFormatException e) {
                        weight = 0.0; // Valor por defecto
                    }
                    
                    // Procesar habilidades
                    String abilitiesString = fields[7].trim();
                    List<String> abilities;
                    
                    // Eliminar comillas si las hay
                    if (abilitiesString.startsWith("\"") && abilitiesString.endsWith("\"")) {
                        abilitiesString = abilitiesString.substring(1, abilitiesString.length() - 1);
                    }
                    
                    // Separar por comas
                    abilities = new ArrayList<>(Arrays.asList(abilitiesString.split("\\s*,\\s*")));
                    
                    int generation;
                    try {
                        generation = Integer.parseInt(fields[8].trim());
                    } catch (NumberFormatException e) {
                        generation = 1; // Valor por defecto
                    }
                    
                    boolean isLegendary = fields[9].trim().equalsIgnoreCase("Yes");

                    // Crear el Pokémon
                    Pokemon pokemon = new Pokemon(name, pokedexNumber, type1, type2, classification, 
                                                 height, weight, abilities, generation, isLegendary);
                    
                    // Guardamos el Pokémon con su nombre original
                    pokemonMap.put(name, pokemon);
                    
                    // También con su nombre en minúsculas para buscar sin sensibilidad a mayúsculas
                    String lowerCaseName = name.toLowerCase();
                    if (!name.equals(lowerCaseName)) {
                        pokemonMap.put(lowerCaseName, pokemon);
                    }
                    
                    count++;
                    
                    // Mostrar los primeros 5 para debug
                    if (count <= 5) {
                        System.out.println("Cargado: '" + name + "'");
                    }
                    
                } catch (Exception e) {
                    System.err.println("Error procesando línea: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }
            
            System.out.println("Total de Pokémon cargados: " + count);
            System.out.println("Tamaño del mapa: " + pokemonMap.size());
            
            if (pokemonMap.isEmpty()) {
                System.err.println("¡ADVERTENCIA! No se cargó ningún Pokémon. Verifica la ruta y formato del archivo: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            System.err.println("Ruta del archivo: " + filePath);
            e.printStackTrace();
        }
    }
    
    /**
     * Divide una línea CSV manejando comillas correctamente
     */
    private static String[] parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        
        // No olvidar el último token
        tokens.add(sb.toString());
        
        return tokens.toArray(new String[0]);
    }
}