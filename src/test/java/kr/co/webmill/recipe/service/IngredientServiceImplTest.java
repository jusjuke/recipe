package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.converters.IngredientCommandToIngredient;
import kr.co.webmill.recipe.converters.IngredientToIngredientCommand;
import kr.co.webmill.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import kr.co.webmill.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import kr.co.webmill.recipe.domains.Ingredient;
import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.domains.UnitOfMeasure;
import kr.co.webmill.recipe.repository.IngredientRepository;
import kr.co.webmill.recipe.repository.RecipeRepository;
import kr.co.webmill.recipe.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private IngredientService ingredientService;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private Ingredient ingredient;
    private Ingredient ingredient1;

    private Set<Ingredient> ingredients = new HashSet<>();

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @Before
    public void setUp() throws Exception {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setDescription("Test Ingredient1");
        ingredient.setAmount(new BigDecimal(1.1));
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        uom.setDescription("Spoon");
        ingredient.setUnitOfMeasure(uom);
        ingredients.add(ingredient);

        ingredient1 = new Ingredient();
        ingredient1.setId(2L);
        ingredient1.setDescription("Test Ingredient2");
        ingredient1.setAmount(new BigDecimal(2.2));
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(2L);
        uom1.setDescription("Cup");
        ingredient1.setUnitOfMeasure(uom1);
        ingredients.add(ingredient1);

        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository, recipeRepository,
                unitOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);

    }

    @Test
    public void getIngredients() {
        when(ingredientRepository.findAll()).thenReturn(ingredients);
        Set<Ingredient> returnedIngredients = ingredientService.getIngredients();
        assertNotNull(returnedIngredients);
        assertEquals(2, returnedIngredients.size());
    }

    @Test
    public void findById() {
        // given
        Optional<Ingredient> ingredientOptional = Optional.of(ingredient);
        // when
        when(ingredientRepository.findById(anyLong())).thenReturn(ingredientOptional);
        Ingredient retIngredient = ingredientService.findById(1L);

        // then
        assertNotNull(retIngredient);
        assertEquals(retIngredient, ingredient);
        verify(ingredientRepository, times(1)).findById(anyLong());
        verify(ingredientRepository, never()).findAll();
    }

    @Test
    public void findCommandById() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setIngredient(ingredient);
        Optional<Ingredient> ingredientOptional = Optional.of(ingredient);
        // when
        when(ingredientRepository.findById(anyLong())).thenReturn(ingredientOptional);
        IngredientCommand ingredientCommand = ingredientService.findCommandById(1L);
        //then
        assertNotNull(ingredientCommand);
        assertEquals(ingredientCommand.getId(), ingredient.getId());
    }

    @Test
    public void saveIngredientCommand() {
        //given



        Recipe foundRecipe = new Recipe();
        foundRecipe.setId(1L);
        foundRecipe.setIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(foundRecipe);
        IngredientCommand paramIngredientCommand = ingredientToIngredientCommand.convert(ingredient);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(1L);
        savedRecipe.setIngredient(ingredient);

        Optional<Ingredient> ingredientOptional = Optional.of(ingredient);
        Optional<UnitOfMeasure> uomOptional = Optional.of(new UnitOfMeasure());

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(ingredientRepository.findById(anyLong())).thenReturn(ingredientOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(uomOptional);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(paramIngredientCommand);

        //then
        assertEquals(ingredient.getId(), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void deleteIngredientById() {
        ingredientService.deleteIngredientById(1L);
        verify(ingredientRepository, times(1)).deleteById(anyLong());
    }


    @Test
    public void findCommandByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setIngredient(ingredient);
        recipe.setIngredient(ingredient1);
        //recipe.getIngredients().add(ingredient);
        //recipe.getIngredients().add(ingredient1);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndIngredientId(1L, 2L);
        assertEquals(Long.valueOf(2L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
}