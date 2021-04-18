package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.*;
import kr.co.webmill.recipe.domains.Difficulty;
import kr.co.webmill.recipe.domains.Ingredient;
import kr.co.webmill.recipe.domains.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
    private  RecipeCommandToRecipe converter;
    private  RecipeCommand recipeCommand;

    public static final Long RECIPE_ID = 2L;
    public static final Integer PREP_TIME = Integer.valueOf("3");
    public static final Integer COOK_TIME = Integer.valueOf("2");
    public static final Integer SERVINGS = Integer.valueOf("4");
    public static final String SOURCE = "Recipe Source";
    public static final  String URL = "Http://cooketest.com";
    public static final String DIRECTION = "Make well" ;
    public static final String RECIPE_DESCRIPTION = "RECIPE DESCRIPTION";
    public static final String RECIPE_DIRECTION = "RECIPE DIRECTION";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;

    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID_2 = 2L;

    public static final Long NOTES_ID = 1L;

    public static final Long INGRED_ID_1 = 1L;
    public static final Long INGRED_ID_2 = 2L;


    public static final String INGRED_DESC = "description";
    public static final BigDecimal INGRED_AMOUNT = new BigDecimal(1.5);
    public static final Long UOMID = new Long(1L);
    public static final String UOM_DESC = "Uom Description";

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory());

    }
    @Test
    public void nullConvertTest(){
        Recipe recipe = converter.convert(null);
        assertNull(recipe);
    }
    @Test
    public void emptyConvertTest(){
        recipeCommand = new RecipeCommand();
        assertNotNull(converter.convert(recipeCommand));
    }
    @Test
    public void convertTest() {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);

        Set<CategoryCommand> categoryCommands = new HashSet<>();
        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID_1);
        categoryCommands.add(categoryCommand1);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID_2);
        categoryCommands.add(categoryCommand2);

        recipeCommand.setCategories(categoryCommands);

        Set<IngredientCommand> ingredientCommands = new HashSet<>();
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOMID);
        unitOfMeasureCommand.setDescription(UOM_DESC);
        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setUnitOfMeasureCommand(unitOfMeasureCommand);
        ingredientCommand1.setId(INGRED_ID_1);
        ingredientCommand1.setAmount(INGRED_AMOUNT);
        ingredientCommand1.setDescription(INGRED_DESC);
        ingredientCommands.add(ingredientCommand1);
        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setUnitOfMeasureCommand(unitOfMeasureCommand);
        ingredientCommand2.setId(INGRED_ID_2);
        ingredientCommand2.setAmount(INGRED_AMOUNT);
        ingredientCommand2.setDescription(INGRED_DESC);
        ingredientCommands.add(ingredientCommand2);

        recipeCommand.setIngredients(ingredientCommands);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);

        recipeCommand.setNotesCommand(notesCommand);

        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(RECIPE_DESCRIPTION);
        recipeCommand.setDirections(RECIPE_DIRECTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);


        Recipe recipe = converter.convert(recipeCommand);
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(RECIPE_DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(RECIPE_DIRECTION, recipe.getDirections());

    }

}