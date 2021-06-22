package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.domains.Ingredient;

import java.util.Set;

public interface IngredientService {
    Set<Ingredient> getIngredients();
    Ingredient findById(Long id);
    IngredientCommand findCommandById(Long id);
    IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteIngredientById(Long idToDelete);
    void deleteById(Long recipeId, Long ingredientId);
}
