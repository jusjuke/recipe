package kr.co.webmill.recipe.controller;

import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class indexControllerTest {
    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);


    }
    @Test
    public void testMockMVC() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndex() {
        //given

        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        recipeData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipeData);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);


        String viewName = indexController.getIndex(model);
        assertEquals(viewName, "index");
        verify(recipeService, times(1)).getRecipes();
//        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> capturedSet = argumentCaptor.getValue();
        assertEquals(capturedSet.size(), 2);
    }

    @Test
    public void name() {
    }
}