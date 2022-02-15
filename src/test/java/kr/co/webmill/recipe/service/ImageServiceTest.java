package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceTest {
    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception{

        //given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt"
                , "text/plain", "Spring framework jsyoon".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        //when
        imageService.saveImageFile(1L, multipartFile);
        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture()); //캡쳐 하는곳
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

    }
}