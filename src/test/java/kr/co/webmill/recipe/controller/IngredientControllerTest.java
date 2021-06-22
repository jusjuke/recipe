package kr.co.webmill.recipe.controller;

import kr.co.webmill.recipe.commands.IngredientCommand;
import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.commands.UnitOfMeasureCommand;
import kr.co.webmill.recipe.domains.Ingredient;
import kr.co.webmill.recipe.service.IngredientService;
import kr.co.webmill.recipe.service.RecipeService;
import kr.co.webmill.recipe.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {
    private IngredientController ingredientController;

    @Mock
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientCommand ingredientCommand;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setId(2L);
    }

    @Test
    public void ingredientList() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/2/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/ingredientlist"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void ingredientShow() throws Exception{
        when(ingredientService.findCommandByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("ingredient/ingredientshow"));

    }

    @Test
    public void ingredientEdit() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setId(2L);
        when(ingredientService.findCommandByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("ingredient/ingredientform"));
    }
    @Test
    public void ingredientUpdate() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setId(2L);
        //when
        when(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).thenReturn(ingredientCommand);
        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("recipeId", "1")
                .param("id", "2")
                .param("description", "test")
                .param("amount", "2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/" + ingredientCommand.getId() + "/show"));
    }

    @Test
    public void TestIngredientDelete() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));
    }

    @Test
    public void TestNewIngredient() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        Set<UnitOfMeasureCommand> uomList = new HashSet<>();
        uomList.add(new UnitOfMeasureCommand());
        uomList.add(new UnitOfMeasureCommand());

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.getAllUnitOfMeasures()).thenReturn(uomList);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("ingredient/ingredientform"));
    }


}