package kr.co.webmill.recipe.bootstrap;

import kr.co.webmill.recipe.domains.*;
import kr.co.webmill.recipe.repository.CategoryRepository;
import kr.co.webmill.recipe.repository.RecipeRepository;
import kr.co.webmill.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private final RecipeRepository recipeRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>();
        Optional<UnitOfMeasure> unitOfMeasureOptionalEach = unitOfMeasureRepository.findByDescription("Each");
        if(!unitOfMeasureOptionalEach.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        Optional<UnitOfMeasure> unitOfMeasureOptionalTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!unitOfMeasureOptionalTablespoon.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        Optional<UnitOfMeasure> unitOfMeasureOptionalTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!unitOfMeasureOptionalTeaspoon.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        Optional<UnitOfMeasure> unitOfMeasureOptionalDash = unitOfMeasureRepository.findByDescription("Dash");
        if(!unitOfMeasureOptionalDash.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        Optional<UnitOfMeasure> unitOfMeasureOptionalPint = unitOfMeasureRepository.findByDescription("Pint");
        if(!unitOfMeasureOptionalPint.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        Optional<UnitOfMeasure> unitOfMeasureOptionalCup = unitOfMeasureRepository.findByDescription("Cup");
        if(!unitOfMeasureOptionalCup.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        Optional<UnitOfMeasure> unitOfMeasureOptionalOunce = unitOfMeasureRepository.findByDescription("Ounce");
        if(!unitOfMeasureOptionalOunce.isPresent()){
            throw new RuntimeException("Expected UOM Not Fouund");
        }
        UnitOfMeasure eachUom = unitOfMeasureOptionalEach.get();
        UnitOfMeasure tableSpoonUom = unitOfMeasureOptionalTablespoon.get();
        UnitOfMeasure teaSpoonUom = unitOfMeasureOptionalTeaspoon.get();
        UnitOfMeasure pintUom = unitOfMeasureOptionalPint.get();
        UnitOfMeasure cupUom = unitOfMeasureOptionalCup.get();
        UnitOfMeasure ounceUom = unitOfMeasureOptionalOunce.get();

        Optional<Category> americalCategoryOptional = categoryRepository.findByDescription("American");
        if(!americalCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }
        Category americanCategory = americalCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(0);
        guacRecipe.setCookTime(10);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. \n" +
                "Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. \n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. \n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, \n" +
                "place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it.");
        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Be careful handling chiles if using. " +
                "Wash your hands thoroughly after handling and do not touch your " +
                "eyes or the area near your eyes with your hands for several hours.");
        guacRecipe.setNotes(guacNotes);
        guacNotes.setRecipe(guacRecipe);

        guacRecipe.setIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.setIngredient(new Ingredient("salt", new BigDecimal(0.4), teaSpoonUom));
        guacRecipe.setIngredient(new Ingredient("lemon juice", new BigDecimal(1), tableSpoonUom));
        guacRecipe.setIngredient(new Ingredient("minced red onion ", new BigDecimal(0.4), cupUom));
        guacRecipe.setIngredient(new Ingredient("serrano chiles", new BigDecimal(2), eachUom));
        guacRecipe.setIngredient(new Ingredient("cilantro ", new BigDecimal(2), tableSpoonUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        recipes.add(guacRecipe);

        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Yummy Tacos");
        tacosRecipe.setPrepTime(3);
        tacosRecipe.setCookTime(7);
        tacosRecipe.setDifficulty(Difficulty.KIND_OF_HARD);
        tacosRecipe.setDirections("1 Heat the pan, prep the ingredients\n" +
                "2 Butter a tortilla and heat it in the pan until it bubbles\n" +
                "3 Add cheese\n" +
                "4 Flip half of the tortilla over the cheese side\n" +
                "5 Remove from pan, add extras");
        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("This method makes delicious, buttery, cheesey tacos. IfIngredient you are avoiding butter, " +
                "or frying oil (which you could also use), you could make these in a microwave. " +
                "Soften the tortillas first in the microwave. We use 20 seconds on high per tortilla, " +
                "with the tortillas sitting on a paper towel in the microwave to absorb moisture. " +
                "Once they've been softened this way you can add cheese and fold them over and heat them a few seconds more," +
                " just until the cheese melts.");
        tacosRecipe.setNotes(tacosNotes);
        tacosNotes.setRecipe(tacosRecipe);

        tacosRecipe.setIngredient(new Ingredient("Corn tortillas", new BigDecimal(1),eachUom));
        tacosRecipe.setIngredient(new Ingredient("Butter", new BigDecimal(0.4),tableSpoonUom));
        tacosRecipe.setIngredient(new Ingredient("Cheddar", new BigDecimal(1),eachUom));
        tacosRecipe.setIngredient(new Ingredient("Salsa", new BigDecimal(1),teaSpoonUom));

        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);
        return recipes;

    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading bootstrap data ..");
    }
}
