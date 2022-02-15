package kr.co.webmill.recipe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile imageFile);
}
