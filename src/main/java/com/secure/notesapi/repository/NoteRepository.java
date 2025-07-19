package com.secure.notesapi.repository;

import com.secure.notesapi.model.Note;
import com.secure.notesapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
}
