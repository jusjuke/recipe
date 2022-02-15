package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.repository.RecipeRepository;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    RecipeRepository recipeRepository;
    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    @Synchronized
    public void saveImageFile(Long recipeId, MultipartFile imageFile) {
        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();
            byte[] bytes = new byte[imageFile.getBytes().length];
            int i = 0;
            for(byte b : imageFile.getBytes()){
                bytes[i++] = b;
            }
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        } catch (IOException ex){
            log.error("Error occurred", ex);
            ex.printStackTrace();
        }
    }
}
