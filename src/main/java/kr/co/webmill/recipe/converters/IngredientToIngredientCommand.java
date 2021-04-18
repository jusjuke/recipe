package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.domains.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {


    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public IngredientCommand convert(Ingredient source) {
        if(source == null) {
            return null;
        }
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(source.getId());
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureConverter.convert(source.getUnitOfMeasure()));
        ingredientCommand.setRecipeId(source.getRecipe().getId());
        return ingredientCommand;
    }
}
