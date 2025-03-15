import java.util.*;
import java.util.stream.Collectors;

public class usospokemon {
    private Map<String, Pokemon> allPokemons;
    private Map<String, Pokemon> userCollection;

    public usospokemon(Map<String, Pokemon> allPokemons, Map<String, Pokemon> userCollection) {
        this.allPokemons = allPokemons;
        this.userCollection = userCollection;
    }

    /**
     * Añade un Pokemon a la colección del usuario
     */
    public void addUserPokemon(String name) {
        // Intentar encontrar el Pokémon sin importar mayúsculas/minúsculas
        String normalizedName = name.trim();
        Pokemon pokemon = findPokemon(normalizedName);
        
        if (pokemon == null) {
            System.out.println("Error: Pokémon '" + normalizedName + "' no encontrado.");
            return;
        }
        
        String originalName = pokemon.getName();
        
        // Verificar si ya existe en la colección del usuario
        if (isInUserCollection(pokemon)) {
            System.out.println("Error: '" + originalName + "' ya está en tu colección.");
            return;
        }
        
        // Añadir a la colección del usuario
        userCollection.put(originalName, pokemon);
        System.out.println("'" + originalName + "' ha sido agregado a tu colección.");
    }
    
    /**
     * Busca un Pokémon sin importar mayúsculas/minúsculas
     */
    private Pokemon findPokemon(String name) {
        // Buscar por nombre exacto primero
        if (allPokemons.containsKey(name)) {
            return allPokemons.get(name);
        }
        
        // Luego buscar por nombre en minúsculas
        String lowerName = name.toLowerCase();
        if (allPokemons.containsKey(lowerName)) {
            return allPokemons.get(lowerName);
        }
        
        return null;
    }
    
    /**
     * Verifica si un Pokémon ya está en la colección del usuario
     */
    private boolean isInUserCollection(Pokemon pokemon) {
        String name = pokemon.getName();
        String lowerName = name.toLowerCase();
        
        return userCollection.containsKey(name) || userCollection.containsKey(lowerName);
    }
    
    /**
     * Muestra los datos de un Pokémon específico
     */
    public void showPokemonData(String name) {
        Pokemon pokemon = findPokemon(name.trim());
        
        if (pokemon != null) {
            System.out.println(pokemon.datospokemon());
        } else {
            System.out.println("Error: Pokémon '" + name + "' no encontrado.");
        }
    }
    
    /**
     * Muestra la colección del usuario ordenada por Tipo 1
     */
    public void showUserCollectionByType1() {
        if (userCollection.isEmpty()) {
            System.out.println("Tu colección está vacía.");
            return;
        }
        
        // Evitamos duplicados y ordenamos por tipo1
        userCollection.values().stream()
            .distinct()  // Elimina duplicados
            .sorted(Comparator.comparing(Pokemon::getType1))
            .forEach(pokemon -> System.out.println(pokemon.getName() + " - " + pokemon.getType1()));
    }
    
    /**
     * Muestra todos los Pokémon ordenados por Tipo 1
     */
    @SuppressWarnings("unused")
    public void showAllByType1() {
        // Eliminar duplicados (nombres en minúsculas) y ordenar por tipo1
        allPokemons.values().stream()
            .collect(Collectors.toMap(
                Pokemon::getName,  // Clave: nombre original
                pokemon -> pokemon,  // Valor: objeto Pokemon
                (existing, replacement) -> existing  // En caso de colisión, mantener el existente
            ))
            .values().stream()
            .sorted(Comparator.comparing(Pokemon::getType1))
            .forEach(pokemon -> System.out.println(pokemon.getName() + " - " + pokemon.getType1()));
    }
    
    /**
     * Muestra Pokémon que tienen una habilidad específica
     */
    public void showPokemonsByAbility(String ability) {
        if (ability == null || ability.trim().isEmpty()) {
            System.out.println("Debes ingresar una habilidad válida.");
            return;
        }
        
        String normalizedAbility = ability.trim().toLowerCase();
        Set<String> displayedNames = new HashSet<>();  // Para evitar duplicados
        boolean found = false;
        
        // Buscar Pokémon con la habilidad especificada
        for (Pokemon pokemon : allPokemons.values()) {
            if (pokemon.getAbilities() != null) {
                for (String pokemonAbility : pokemon.getAbilities()) {
                    if (pokemonAbility.toLowerCase().contains(normalizedAbility) && 
                        !displayedNames.contains(pokemon.getName())) {
                        System.out.println(pokemon.getName());
                        displayedNames.add(pokemon.getName());
                        found = true;
                        break;  // Evitar mostrar el mismo Pokémon varias veces
                    }
                }
            }
        }
        
        if (!found) {
            System.out.println("No se encontraron Pokémon con la habilidad: " + ability);
        }
    }
}