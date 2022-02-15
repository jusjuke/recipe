package kr.co.webmill.recipe.domains;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Entity

@EqualsAndHashCode(callSuper = false, exclude = {"ingredients", "notes", "categories"})
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "prepare_time")
    private Integer prepTime;
    private Integer cookTime;

    private Integer servings;
    private String source;

    public void setIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

    private  String url;
    @Lob
    private String directions;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    @Lob
    private byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   // @JoinTable(name="recipe_ingredient", joinColumns=@JoinColumn(name="recipe_id"))
    private Set<Ingredient> ingredients = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="recipe_category", joinColumns = @JoinColumn(name="recipe_id"), inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories = new HashSet<>();
    public Recipe() {

    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }
}
