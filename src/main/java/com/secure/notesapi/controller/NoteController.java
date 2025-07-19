package com.secure.notesapi.controller;

import com.secure.notesapi.dto.NoteDTO;
import com.secure.notesapi.dto.NoteRequestDTO;
import com.secure.notesapi.model.Note;
import com.secure.notesapi.model.User;
import com.secure.notesapi.repository.NoteRepository;
import com.secure.notesapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    private User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserEmail()).orElseThrow();
    }

    @GetMapping
    public List<NoteDTO> getUserNotes() {
        User user = getCurrentUser();
        return noteRepository.findByUser(user).stream()
                .map(note -> new NoteDTO(note.getId(), note.getTitle(), note.getContent()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public NoteDTO createNote(@Valid @RequestBody NoteRequestDTO request) {
        User user = getCurrentUser();
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUser(user);

        Note saved = noteRepository.save(note);
        return new NoteDTO(saved.getId(), saved.getTitle(), saved.getContent());
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        if (!note.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access denied");
        }
        return new NoteDTO(note.getId(), note.getTitle(), note.getContent());
    }

    @PutMapping("/{id}")
    public NoteDTO updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDTO request) {
        Note note = noteRepository.findById(id).orElseThrow();
        if (!note.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access denied");
        }
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note updated = noteRepository.save(note);
        return new NoteDTO(updated.getId(), updated.getTitle(), updated.getContent());
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        if (!note.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access denied");
        }
        noteRepository.delete(note);
        return "Note deleted successfully.";
    }

    // ADMIN ONLY
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<NoteDTO> getAllNotesForAdmin() {
        return noteRepository.findAll().stream()
                .map(note -> new NoteDTO(note.getId(), note.getTitle(), note.getContent()))
                .collect(Collectors.toList());
    }
}
