package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.domains.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {


    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public Ingredient convert(IngredientCommand source) {
        if(source == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(unitOfMeasureConverter.convert(source.getUnitOfMeasureCommand()));
        return ingredient;
    }
}
