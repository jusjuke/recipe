package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.commands.UnitOfMeasureCommand;
import kr.co.webmill.recipe.domains.Ingredient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class IngredientCommandToIngredientTest {
    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "description";
    public static final BigDecimal AMOUNT = new BigDecimal(1.5);
    public static final Long UOMID = new Long(1L);
    public static final String UOMDESCRIPTION = "Uom Description";


    @Mock
    private IngredientCommand ingredientCommand;
    @Mock
    private UnitOfMeasureCommand unitOfMeasureCommand;

    private IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new IngredientCommandToIngredient( new UnitOfMeasureCommandToUnitOfMeasure());

    }

    @Test
    public void nullConvertTest(){
        Ingredient ingredient = converter.convert(null);
        assertNull(ingredient);
    }
    @Test
    public void emptyConvertTest(){
        ingredientCommand = new IngredientCommand();
        ingredientCommand.setUnitOfMeasureCommand(new UnitOfMeasureCommand());
        assertNotNull(converter.convert(ingredientCommand));
    }
    @Test
    public void convertTest() {
        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOMID);
        unitOfMeasureCommand.setDescription(UOMDESCRIPTION);
        ingredientCommand = new IngredientCommand();
        ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);
        ingredientCommand.setId(ID);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
        Ingredient ingredient = converter.convert(ingredientCommand);
        assertEquals(ID, ingredient.getId());
        assertEquals(ingredient.getDescription(), DESCRIPTION);
        assertEquals(ingredient.getAmount(), AMOUNT);
        assertEquals(ingredient.getUnitOfMeasure().getId(), UOMID);
        assertEquals(ingredient.getUnitOfMeasure().getDescription(), UOMDESCRIPTION);
    }
}