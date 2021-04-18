package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.NotesCommand;
import kr.co.webmill.recipe.domains.Notes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class NotesCommandToNotesTest {

    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "description";
    @Mock
    private NotesCommand notesCommand;
    private NotesCommandToNotes converter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new NotesCommandToNotes();

    }

    @Test
    public void nullConvertTest(){
        Notes notes = converter.convert(null);
        assertNull(notes);
    }
    @Test
    public void emptyConvertTest(){
        notesCommand = new NotesCommand();
        assertNotNull(converter.convert(notesCommand));
    }
    @Test
    public void convertTest() {
        when(notesCommand.getRecipeNotes()).thenReturn(DESCRIPTION);
        when(notesCommand.getId()).thenReturn(ID);
        Notes notes = converter.convert(notesCommand);
        assertEquals(notes.getId(), ID);
        assertEquals(notes.getRecipeNotes(), DESCRIPTION);
    }
}