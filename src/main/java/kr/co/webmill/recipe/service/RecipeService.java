package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface RecipeService {
     Set<Recipe> getRecipes();
     Recipe findById(long id);
     RecipeCommand findCommandById(Long id);
     RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
     void deleteRecipeById(Long idToDelete);
}
