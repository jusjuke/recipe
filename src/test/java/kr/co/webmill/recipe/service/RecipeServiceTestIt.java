package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.converters.RecipeCommandToRecipe;
import kr.co.webmill.recipe.converters.RecipeToRecipeCommand;
import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceTestIt {
    public static final String NEW_DESC = "New Recipe Description";
    @Autowired
    private  RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Transactional
    public void saveRecipeCommand() {
        Iterable<Recipe> recipeIterable = recipeRepository.findAll();
        Recipe recipe = recipeIterable.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        recipeCommand.setDescription(NEW_DESC);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        assertEquals(NEW_DESC, savedRecipeCommand.getDescription());
        assertEquals(recipe.getId(), savedRecipeCommand.getId());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }

}