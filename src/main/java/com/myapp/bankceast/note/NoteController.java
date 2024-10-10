package com.myapp.bankceast.note;

import com.myapp.bankceast.models.Note;
import com.myapp.bankceast.note.responses.NoteSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/notes")
public class NoteController {

    public final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        List all = noteService.findAll();
        NoteSuccessResponse<Object> response = NoteSuccessResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Data fetched successfully.")
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .data(all)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Optional <Note> note = noteService.findById(id);
        NoteSuccessResponse<Object> response = NoteSuccessResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Data fetched successfully.")
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .data(note)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity create(@RequestParam Integer amount, @RequestParam Long debtor_id){
        final Note note = noteService.create(amount, debtor_id);
        NoteSuccessResponse response = NoteSuccessResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Note created successfully.")
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .data(note)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
