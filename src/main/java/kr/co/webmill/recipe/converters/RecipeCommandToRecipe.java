package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.domains.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe  implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final CategoryCommandToCategory categoryConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, IngredientCommandToIngredient ingredientConverter, CategoryCommandToCategory categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setServings(source.getServings());
        recipe.setId(source.getId());
        recipe.setDirections(source.getDirections());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        if(source.getNotesCommand() != null) {
            recipe.setNotes(notesConverter.convert(source.getNotesCommand()));
        }
        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories().forEach(catetory -> recipe.getCategories().add(categoryConverter.convert(catetory)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }
        return recipe;
    }
}
