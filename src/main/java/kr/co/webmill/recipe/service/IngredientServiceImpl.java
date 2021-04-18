package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.converters.IngredientCommandToIngredient;
import kr.co.webmill.recipe.converters.IngredientToIngredientCommand;
import kr.co.webmill.recipe.domains.Ingredient;
import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.IngredientRepository;
import kr.co.webmill.recipe.repository.RecipeRepository;
import kr.co.webmill.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Set<Ingredient> getIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        Iterable<Ingredient> ingredientIterable = ingredientRepository.findAll();
        ingredientIterable.iterator().forEachRemaining(ingredients::add);
        return ingredients;
    }

    @Override
    public Ingredient findById(Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        if(!ingredientOptional.isPresent()){
            throw new RuntimeException("Ingredient doesn't exists");
        }
        return ingredientOptional.get();
    }

    @Override
    public IngredientCommand findCommandById(Long id) {
        Ingredient ingredient = findById(id);
        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe doesn't exists");
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredientRam -> ingredientRam.getId().equals(ingredientId))
                .map(ingredientRam -> ingredientToIngredientCommand.convert(ingredientRam))
                .findFirst();
        if(!ingredientCommandOptional.isPresent()){
            log.error("Ingredient donesn't exist");
        }
        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if(!recipeOptional.isPresent()){
            log.debug("Recipe doesn't exists");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Ingredient paramIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(paramIngredient.getAmount());
                ingredient.setDescription(paramIngredient.getDescription());
                ingredient.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasureCommand().getId())
                        .orElseThrow(() -> new RuntimeException("UnitOfMeasure not found.")));
            } else {
                recipe.setIngredient(paramIngredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst().get());
        }

    }

    @Override
    public void deleteIngredientById(Long idToDelete) {
        ingredientRepository.deleteById(idToDelete);
    }
}
