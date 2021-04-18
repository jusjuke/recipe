package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.converters.RecipeCommandToRecipe;
import kr.co.webmill.recipe.converters.RecipeToRecipeCommand;
import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

   // @Autowired
    //RecipeServiceImpl recipeService;
    @Autowired
    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);

    }
    @Test
    public void getRecipes() throws Exception{
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe recipeReturned = recipeService.findById(anyLong());
        assertNotNull("Null recipe returned", recipeReturned);
        assertEquals(recipeReturned, recipeOptional.get());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();


    }
    @Test
    @Transactional
    public void deleteRecipe(){
        Long idToDelete = 2L;
        recipeRepository.deleteById(idToDelete);
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}