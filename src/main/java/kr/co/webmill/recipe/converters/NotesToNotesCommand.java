package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.NotesCommand;
import kr.co.webmill.recipe.domains.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Override
    @Synchronized
    @Nullable
    public NotesCommand convert(Notes source) {
        if(source == null) {
            return null;
        }
        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
