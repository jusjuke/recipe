package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.CategoryCommand;
import kr.co.webmill.recipe.domains.Category;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryCommandToCategoryTest {

    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "description";
    @Mock
    private CategoryCommand categoryCommand;
    private CategoryCommandToCategory converter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new CategoryCommandToCategory();

    }

    @Test
    public void nullConvertTest(){
        Category category = converter.convert(null);
        assertNull(category);
    }
    @Test
    public void emptyConvertTest(){
        categoryCommand = new CategoryCommand();
        assertNotNull(converter.convert(categoryCommand));
    }
    @Test
    public void convertTest() {
        when(categoryCommand.getDescription()).thenReturn(DESCRIPTION);
        when(categoryCommand.getId()).thenReturn(ID);
        Category category = converter.convert(categoryCommand);
        assertEquals(category.getId(), ID);
        assertEquals(category.getDescription(), DESCRIPTION);
    }
}