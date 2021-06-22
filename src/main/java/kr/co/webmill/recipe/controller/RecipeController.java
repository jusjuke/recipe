package kr.co.webmill.recipe.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.domains.Difficulty;
import kr.co.webmill.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipe/{id}/show"})
    public String showById(@PathVariable String id,  Model model){
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping(name="recipe", path="/recipe")
    //@RequestMapping(name="recipe", method=RequestMethod.POST)
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @RequestMapping({"/recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id,  Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping({"/recipe/{id}/delete"})
    public String deleteRecipe(@PathVariable String id){
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/";
    }

}
