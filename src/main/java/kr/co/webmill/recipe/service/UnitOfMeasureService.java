package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> getAllUnitOfMeasures();
}
