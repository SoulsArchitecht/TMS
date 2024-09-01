package ru.sshibko.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.sshibko.tms.model.Comment;
import ru.sshibko.tms.model.Priority;
import ru.sshibko.tms.model.Status;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Instant createdAt;
    private Instant updatedAt;
    private Long authorId;
    private Long assigneeId;
    private List<Comment> comments;
}
