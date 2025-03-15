import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UsosPokemonTest {

    private Map<String, Pokemon> allPokemons;
    private Map<String, Pokemon> userCollection;
    private usospokemon usospokemon;
    
    // Para capturar la salida de System.out
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @BeforeEach
    void setUp() {
        // Inicializar los mapas
        allPokemons = new HashMap<>();
        userCollection = new HashMap<>();
        
        // Agregar algunos Pokémon de prueba
        Pokemon bulbasaur = new Pokemon("Bulbasaur", 1, "Grass", "Poison", "Seed Pokemon", 
                                      0.7, 6.9, Arrays.asList("Overgrow", "Chlorophyll"), 1, false);
        Pokemon charmander = new Pokemon("Charmander", 4, "Fire", null, "Lizard Pokemon", 
                                      0.6, 8.5, Arrays.asList("Blaze", "Solar Power"), 1, false);
        Pokemon squirtle = new Pokemon("Squirtle", 7, "Water", null, "Tiny Turtle Pokemon", 
                                    0.5, 9.0, Arrays.asList("Torrent", "Rain Dish"), 1, false);
        Pokemon pikachu = new Pokemon("Pikachu", 25, "Electric", null, "Mouse Pokemon", 
                                   0.4, 6.0, Arrays.asList("Static", "Lightning Rod"), 1, false);
        
        // Agregar al mapa con nombres originales y en minúsculas
        allPokemons.put("Bulbasaur", bulbasaur);
        allPokemons.put("bulbasaur", bulbasaur);
        allPokemons.put("Charmander", charmander);
        allPokemons.put("charmander", charmander);
        allPokemons.put("Squirtle", squirtle);
        allPokemons.put("squirtle", squirtle);
        allPokemons.put("Pikachu", pikachu);
        allPokemons.put("pikachu", pikachu);
        
        // Crear la instancia de usospokemon
        usospokemon = new usospokemon(allPokemons, userCollection);
        
        // Redirigir System.out para pruebas
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    void testAddUserPokemon() {
        // Agregar un Pokémon con nombre exacto
        usospokemon.addUserPokemon("Bulbasaur");
        assertTrue(userCollection.containsKey("Bulbasaur"), "El Pokémon debería estar en la colección");
        
        // Limpiar el contenido de salida
        outContent.reset();
        
        // Agregar un Pokémon con nombre en minúsculas
        usospokemon.addUserPokemon("charmander");
        assertTrue(userCollection.containsKey("Charmander"), "El Pokémon debería estar en la colección");
        
        // Intentar agregar un Pokémon que ya está en la colección
        outContent.reset();
        usospokemon.addUserPokemon("Bulbasaur");
        assertTrue(outContent.toString().contains("ya está en tu colección"), 
                  "Debería mostrar mensaje de que ya está en la colección");
        
        // Intentar agregar un Pokémon que no existe
        outContent.reset();
        usospokemon.addUserPokemon("MewTwo");
        assertTrue(outContent.toString().contains("no encontrado"), 
                  "Debería mostrar mensaje de Pokémon no encontrado");
        
        // Verificar el tamaño final de la colección
        assertEquals(2, userCollection.size(), "La colección debería tener 2 Pokémon");
    }
    
    @Test
    void testShowPokemonsByAbility() {
        // Buscar una habilidad que tienen varios Pokémon
        outContent.reset();
        usospokemon.showPokemonsByAbility("Static");
        String output = outContent.toString();
        
        // Verificar que se muestre Pikachu
        assertTrue(output.contains("Pikachu"), "Debería mostrar Pikachu que tiene la habilidad Static");
        
        // Buscar una habilidad que solo tiene un Pokémon
        outContent.reset();
        usospokemon.showPokemonsByAbility("Overgrow");
        output = outContent.toString();
        
        // Verificar que se muestre Bulbasaur y no otros
        assertTrue(output.contains("Bulbasaur"), "Debería mostrar Bulbasaur que tiene la habilidad Overgrow");
        assertFalse(output.contains("Charmander"), "No debería mostrar Charmander");
        assertFalse(output.contains("Squirtle"), "No debería mostrar Squirtle");
        
        // Buscar una habilidad que no existe
        outContent.reset();
        usospokemon.showPokemonsByAbility("FakeAbility");
        output = outContent.toString();
        
        // Verificar que se muestre el mensaje de no encontrado
        assertTrue(output.contains("No se encontraron Pokémon"), 
                  "Debería mostrar mensaje de que no se encontraron Pokémon");
        
        // Verificar búsqueda con parte del nombre de la habilidad
        outContent.reset();
        usospokemon.showPokemonsByAbility("Power");
        output = outContent.toString();
        
        // Verificar que se muestre Charmander (Solar Power)
        assertTrue(output.contains("Charmander"), 
                  "Debería mostrar Charmander que tiene Solar Power en sus habilidades");
    }

    // Restaurar System.out después de todas las pruebas
    @org.junit.jupiter.api.AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}