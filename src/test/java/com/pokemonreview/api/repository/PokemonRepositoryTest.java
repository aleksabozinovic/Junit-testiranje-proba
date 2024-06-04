package com.pokemonreview.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pokemonreview.api.models.Pokemon;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavePokemon(){
        Pokemon pokemon = Pokemon.builder().name("picacu").type("electric").build();
        
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        
        assertNotNull(savedPokemon);
        assertTrue(savedPokemon.getId() > 0);
    }
    
    @Test
    public void PokemonRepository_GetAll_ReturnMoreThenOnePokemon(){
        Pokemon pokemon = Pokemon.builder().name("picacu").type("electric").build();
        Pokemon pokemon2 = Pokemon.builder().name("picacu2").type("electric2").build();
        
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);
        
        List<Pokemon> pokeonList = pokemonRepository.findAll();
        
        assertNotNull(pokeonList);
        assertEquals(pokeonList.size(), 2);
        
    }
    
    @Test
    public void PokemonRepository_FindById_ReturnPokemon(){
        Pokemon pokemon = Pokemon.builder().name("picacu").type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon  pokemons= pokemonRepository.findById(pokemon.getId()).get();

        assertNotNull(pokemons);

    }
    
    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull(){
        Pokemon pokemon = Pokemon.builder().name("picacu").type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon  pokemons= pokemonRepository.findByType(pokemon.getType()).get();

        assertNotNull(pokemons);

    }
    
    @Test
    public void PokemonRepository_UpdatePocemon_ReturnPokemonNotNull(){
        Pokemon pokemon = Pokemon.builder().name("picacu").type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon  pokemons= pokemonRepository.findById(pokemon.getId()).get();
        pokemons.setName("Pikacu2");
        pokemons.setType("NotElectric");

        Pokemon updatedPokemon = pokemonRepository.save(pokemons);

        assertNotNull(updatedPokemon.getName());
        assertNotNull(updatedPokemon.getType());

    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty(){
        Pokemon pokemon = Pokemon.builder().name("picacu").type("electric").build();

        pokemonRepository.save(pokemon);

        pokemonRepository.delete(pokemon);
        Optional<Pokemon>  pokemons= pokemonRepository.findById(pokemon.getId());

        assertTrue(pokemons.isEmpty());

    }
}
