package ru.sshibko.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sshibko.tms.dto.CommentDto;
import ru.sshibko.tms.dto.PagedDataDto;
import ru.sshibko.tms.model.Comment;
import ru.sshibko.tms.service.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.create(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("id") Long id,
                                                    @RequestBody CommentDto updatedCommentDto) {
        CommentDto commentDto = commentService.update(id, updatedCommentDto);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok("Task with id " + id + " deleted successfully");
    }

    @GetMapping("/")
    public ResponseEntity<Collection<CommentDto>> getAllComments() {
        return ResponseEntity.ok(
                commentService.getAll()
        );
    }

    @GetMapping
    public ResponseEntity<PagedDataDto<Comment>> getAllCommentsPaged(
        @RequestParam(value = "filter", required = false, defaultValue = "") Long filter,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

            PagedDataDto<Comment> pagedDataDto = commentService.findAllCommentsPaged(filter, PageRequest.of(page, size));
            return ResponseEntity.ok(pagedDataDto);
    }

}
