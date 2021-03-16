package kr.co.webmill.recipe.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "categories")
    private Set<Recipe> recipes;

    public Category() {
    }
}
