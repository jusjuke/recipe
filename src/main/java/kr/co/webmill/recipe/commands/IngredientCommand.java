package kr.co.webmill.recipe.commands;

import kr.co.webmill.recipe.domains.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    UnitOfMeasureCommand unitOfMeasureCommand;
    private Long recipeId;
}
