package kr.co.webmill.recipe.converters;
import kr.co.webmill.recipe.commands.*;
import kr.co.webmill.recipe.domains.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

    private  RecipeToRecipeCommand converter;
    private Recipe recipe;

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
    public static final String CAT_DESC_1 = "Category 1 Description";
    public static final Long CAT_ID_2 = 2L;
    public static final String CAT_DESC_2 = "Category 2 Description";

    public static final Long NOTES_ID = 1L;
    public static final String NOTES_DESC = "Notes Description";

    public static final Long INGRED_ID_1 = 1L;
    public static final Long INGRED_ID_2 = 2L;


    public static final String INGRED_DESC = "description";
    public static final BigDecimal INGRED_AMOUNT = new BigDecimal(1.5);
    public static final Long UOMID = 1L;
    public static final String UOM_DESC = "Uom Description";

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());

    }
    @Test
    public void nullConvertTest(){
        RecipeCommand recipeCommand = converter.convert(null);
        assertNull(recipeCommand);
    }
    @Test
    public void emptyConvertTest(){
        recipe = new Recipe();
        assertNotNull(converter.convert(recipe));
    }
    @Test
    public void convertTest() {

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Set<Category> categories = new HashSet<>();
        Category category1 = new Category();
        category1.setId(CAT_ID_1);
        category1.setDescription(CAT_DESC_1);
        categories.add(category1);
        Category category2 = new Category();
        category2.setId(CAT_ID_2);
        category2.setDescription(CAT_DESC_2);
        categories.add(category2);

        recipe.setCategories(categories);

        Set<Ingredient> ingredients = new HashSet<>();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOMID);
        unitOfMeasure.setDescription(UOM_DESC);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setUnitOfMeasure(unitOfMeasure);
        ingredient1.setId(INGRED_ID_1);
        ingredient1.setAmount(INGRED_AMOUNT);
        ingredient1.setDescription(INGRED_DESC);
        ingredients.add(ingredient1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setUnitOfMeasure(unitOfMeasure);
        ingredient2.setId(INGRED_ID_2);
        ingredient2.setAmount(INGRED_AMOUNT);
        ingredient2.setDescription(INGRED_DESC);
        ingredients.add(ingredient2);

        recipe.setIngredients(ingredients);

        Notes notes= new Notes();
        notes.setId(NOTES_ID);
        notes.setRecipeNotes(NOTES_DESC);

        recipe.setNotes(notes);

        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(RECIPE_DESCRIPTION);
        recipe.setDirections(RECIPE_DIRECTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);


        RecipeCommand recipeCommand = converter.convert(recipe);
        assertNotNull(recipeCommand);
        assertEquals(RECIPE_ID, recipeCommand.getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(RECIPE_DESCRIPTION, recipeCommand.getDescription());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
        Iterator<CategoryCommand> categoryCommandIt = recipeCommand.getCategories().iterator();
        while(categoryCommandIt.hasNext()){
            CategoryCommand categoryCommand = categoryCommandIt.next();
            assertNotNull(categoryCommand);
            assertEquals(categoryCommand.getId(), 1L, 2L);
            if(categoryCommand.getId() == 1L){
                assertEquals(categoryCommand.getDescription(), CAT_DESC_1);
            } else {
                assertEquals(categoryCommand.getDescription(), CAT_DESC_2);
            }
        }
        Iterator<IngredientCommand> ingredCommandIt = recipeCommand.getIngredients().iterator();
        while(ingredCommandIt.hasNext()){
            IngredientCommand ingredientCommand = ingredCommandIt.next();
            assertNotNull(ingredientCommand);
            assertEquals(ingredientCommand.getId(), 1L, 2L);
            assertEquals(ingredientCommand.getDescription(), INGRED_DESC);
            assertEquals(ingredientCommand.getAmount(), INGRED_AMOUNT);
            assertEquals(ingredientCommand.getUnitOfMeasureCommand().getId(), UOMID);
            assertEquals(ingredientCommand.getUnitOfMeasureCommand().getDescription(), UOM_DESC);
        }
        assertEquals(NOTES_ID, recipeCommand.getNotesCommand().getId());
        assertEquals(NOTES_DESC, recipeCommand.getNotesCommand().getRecipeNotes());
        assertEquals(RECIPE_DIRECTION, recipeCommand.getDirections());

    }
}