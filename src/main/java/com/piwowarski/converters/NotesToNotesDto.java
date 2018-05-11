package com.piwowarski.converters;

import com.piwowarski.DTO.NotesDto;
import com.piwowarski.models.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesDto implements Converter<Notes, NotesDto> {

    @Synchronized
    @Nullable
    @Override
    public NotesDto convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesDto notesCommand = new NotesDto();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
