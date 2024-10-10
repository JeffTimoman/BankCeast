package com.myapp.bankceast.note.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteSuccessResponse <T>{
    private HttpStatus httpStatus;
    private String message;
    private ZonedDateTime timestamp;
    private T data;
}
