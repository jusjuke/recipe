package kr.co.webmill.recipe.commands;

import kr.co.webmill.recipe.domains.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long Id;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private  String url;
    private String directions;
    private String description;
    private Difficulty difficulty;
    private Byte[] image;
    private NotesCommand notesCommand;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
}
