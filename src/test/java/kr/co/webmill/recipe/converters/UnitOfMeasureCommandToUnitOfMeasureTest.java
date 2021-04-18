package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.UnitOfMeasureCommand;
import kr.co.webmill.recipe.domains.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "description";
    @Mock
    private UnitOfMeasureCommand unitOfMeasureCommand;
    private UnitOfMeasureCommandToUnitOfMeasure converter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @Test
    public void nullConvertTest(){
        UnitOfMeasure uom = converter.convert(null);
        assertNull(uom);
    }
    @Test
    public void emptyConvertTest(){
        unitOfMeasureCommand = new UnitOfMeasureCommand();
        assertNotNull(converter.convert(unitOfMeasureCommand));
    }
    @Test
    public void convertTest() {
        when(unitOfMeasureCommand.getDescription()).thenReturn(DESCRIPTION);
        when(unitOfMeasureCommand.getId()).thenReturn(ID);
        UnitOfMeasure uom = converter.convert(unitOfMeasureCommand);
        assertEquals(uom.getId(), ID);
        assertEquals(uom.getDescription(), DESCRIPTION);
        //unitOfMeasureCommand.setId(ID);
        //unitOfMeasureCommand.setDescription(DESCRIPTION);

    }
}