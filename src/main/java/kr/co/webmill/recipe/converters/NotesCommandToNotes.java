package kr.co.webmill.recipe.converters;

import kr.co.webmill.recipe.commands.NotesCommand;
import kr.co.webmill.recipe.domains.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Override
    @Synchronized
    @Nullable
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }
        Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
