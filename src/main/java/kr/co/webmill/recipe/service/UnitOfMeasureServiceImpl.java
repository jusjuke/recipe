package kr.co.webmill.recipe.service;

import kr.co.webmill.recipe.commands.UnitOfMeasureCommand;
import kr.co.webmill.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import kr.co.webmill.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import kr.co.webmill.recipe.domains.UnitOfMeasure;
import kr.co.webmill.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> getAllUnitOfMeasures() {
        /**
        Iterable<UnitOfMeasure> unitOfMeasureIterable = unitOfMeasureRepository.findAll();
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        unitOfMeasureIterable.iterator().forEachRemaining(unitOfMeasure -> unitOfMeasureCommands
                .add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));
        return unitOfMeasureCommands;
         **/
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(),false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
