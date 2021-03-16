package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    @Autowired
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);

    }
    @Test
    public void getRecipes() throws Exception{
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(((Set) recipes).size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

}