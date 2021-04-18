package kr.co.webmill.recipe.repository;

import kr.co.webmill.recipe.domains.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
