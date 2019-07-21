package kr.co.webmill.recipe.domains;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private BigDecimal amount;
    @OneToOne(fetch = FetchType.EAGER)
    UnitOfMeasure unitOfMeasure;
    @ManyToOne()
    private Recipe recipe;
}
