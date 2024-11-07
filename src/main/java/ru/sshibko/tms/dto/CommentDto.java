package ru.sshibko.tms.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto implements Serializable {

    private Long id;
    private Long taskId;
    private Long authorId;
    private String text;
    private Instant createdAt;
}
