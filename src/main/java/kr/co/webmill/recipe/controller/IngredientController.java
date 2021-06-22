package kr.co.webmill.recipe.controller;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.commands.UnitOfMeasureCommand;
import kr.co.webmill.recipe.domains.Difficulty;
import kr.co.webmill.recipe.service.IngredientService;
import kr.co.webmill.recipe.service.RecipeService;
import kr.co.webmill.recipe.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @RequestMapping({"/recipe/{recipeId}/ingredients"})
    public String ingredientList(@PathVariable String recipeId,  Model model){
        // Set<Ingredient> ingredients = ingredientService.getIngredients();
        // model.addAttribute("ingredients", ingredients);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "ingredient/ingredientlist";
    }

    @RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/show"})
    public String ingredientShow(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
         IngredientCommand ingredientCommand = ingredientService
                 .findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
          model.addAttribute("ingredient", ingredientCommand);
  //      RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
  //      model.addAttribute("ingredient", recipeCommand.getIngredients().);
        return "ingredient/ingredientshow";
    }
    @RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/update"})
    public String ingredientEdit(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        IngredientCommand ingredientCommand = ingredientService
                .findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredient", ingredientCommand);
        Set<UnitOfMeasureCommand> uomList = unitOfMeasureService.getAllUnitOfMeasures();
        model.addAttribute("uomList", uomList);
        return "ingredient/ingredientform";
    }
    @RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/delete"})
    public String ingredientDelete(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId +"/ingredients";
    }
    @RequestMapping({"/recipe/{recipeId}/ingredient/new"})
    public String newIngredient(@PathVariable String recipeId,  Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        model.addAttribute("ingredient", ingredientCommand);
        Set<UnitOfMeasureCommand> uomList = unitOfMeasureService.getAllUnitOfMeasures();
        model.addAttribute("uomList", uomList);
        return "ingredient/ingredientform";
    }
    @PostMapping
    @RequestMapping({"/recipe/{recipeId}/ingredient"})
    public String updateIngredient(@ModelAttribute IngredientCommand command, @PathVariable Long recipeId){
        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipe/" + ingredientCommand.getRecipeId() +"/ingredient/" + ingredientCommand.getId() + "/show";
    }
}
