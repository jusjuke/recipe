package kr.co.webmill.recipe.repository;

import kr.co.webmill.recipe.domains.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
