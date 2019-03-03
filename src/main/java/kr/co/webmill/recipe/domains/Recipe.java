package kr.co.webmill.recipe.domains;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
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
    private  String url;
    private String directions;
    private Difficulty difficulty;
    @Lob
    private Byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    public Recipe() {

    }
}
