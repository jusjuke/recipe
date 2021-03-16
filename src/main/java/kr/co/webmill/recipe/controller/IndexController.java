package kr.co.webmill.recipe.controller;

import kr.co.webmill.recipe.domains.Category;
import kr.co.webmill.recipe.domains.UnitOfMeasure;
import kr.co.webmill.recipe.repository.CategoryRepository;
import kr.co.webmill.recipe.repository.UnitOfMeasureRepository;
import kr.co.webmill.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {
//   @Autowired
//    private CategoryRepository categoryRepository;
//   @Autowired
//    private UnitOfMeasureRepository unitOfMeasureRepository;
/**
    public indexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository){
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }
 **/

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model){
        log.debug("Getting index page here..");
        model.addAttribute("recipes", recipeService.getRecipes());
 /**
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");
        System.out.println(categoryOptional.get().getId());
        System.out.println(unitOfMeasureOptional.get().getId());
  **/
        return "index";
    }
}
