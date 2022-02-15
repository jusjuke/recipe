package kr.co.webmill.recipe.controller;

import kr.co.webmill.recipe.commands.RecipeCommand;
import kr.co.webmill.recipe.domains.Recipe;
import kr.co.webmill.recipe.service.ImageService;
import kr.co.webmill.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }
    @GetMapping({"recipe/{id}/image"})
    public String showImageForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/imageupload";
    }
    @PostMapping({"recipe/{id}/image"})
    public String handleImageUpload(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";
    }
    @GetMapping({"recipe/{id}/recipeimage"})
    public void renderImageFromDb(@PathVariable String id, HttpServletResponse response){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for(byte b : recipeCommand.getImage()){
            byteArray[i++] = b;
        }
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        try {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
