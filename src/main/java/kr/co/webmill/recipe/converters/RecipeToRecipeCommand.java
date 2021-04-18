package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.domains.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final CategoryToCategoryCommand categoryConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesConverter, IngredientToIngredientCommand ingredientConverter, CategoryToCategoryCommand categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public RecipeCommand convert(Recipe source) {
        if(source == null) {
            return null;
        }
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setId(source.getId());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        if(source.getNotes() != null) {
            recipeCommand.setNotesCommand(notesConverter.convert(source.getNotes()));
        }
        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories().forEach(catetoryCommand -> recipeCommand.getCategories().add(categoryConverter.convert(catetoryCommand)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredientCommand -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
        }
        return recipeCommand;
    }
}