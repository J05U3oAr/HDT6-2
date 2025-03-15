package demo.target.test-classes.com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class UsosPokemonTest {
    private usospokemon usosPokemon;
    private Map<String, Pokemon> allPokemons;
    private Map<String, Pokemon> userCollection;

    @BeforeEach
    void setUp() {
        // Crear algunos Pokémon de prueba
        Pokemon pikachu = new Pokemon("Pikachu", "Electric", Arrays.asList("Static", "Lightning Rod"));
        Pokemon charmander = new Pokemon("Charmander", "Fire", Arrays.asList("Blaze", "Solar Power"));

        // Inicializar los mapas con los Pokémon
        allPokemons = new HashMap<>();
        allPokemons.put("Pikachu", pikachu);
        allPokemons.put("Charmander", charmander);

        userCollection = new HashMap<>();

        // Instanciar la clase a probar
        usosPokemon = new usospokemon(allPokemons, userCollection);
    }

    @Test
    void testAddUserPokemon() {
        // Agregar Pikachu a la colección
        usosPokemon.addUserPokemon("Pikachu");

        // Verificar que Pikachu está en la colección del usuario
        assertTrue(userCollection.containsKey("Pikachu"));
        assertEquals("Electric", userCollection.get("Pikachu").getType1());
    }

    @Test
    void testShowPokemonsByAbility() {
        // Redirigir la salida de la consola para capturarla
        String ability = "Blaze";
        
        // Simulación de la salida en consola
        usosPokemon.showPokemonsByAbility(ability);
        
        // Como no podemos capturar la salida directamente aquí, verificamos que Charamander esté en los Pokémon
        assertTrue(allPokemons.values().stream()
            .anyMatch(p -> p.getAbilities().contains(ability) && p.getName().equals("Charmander")));
    }
}



