package ru.sshibko.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto implements Serializable {

    private Long id;
    private Long taskId;
    private Long authorId;
    private String text;
    private Instant createdAt;
}
