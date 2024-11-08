package ru.sshibko.tms.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.sshibko.tms.dto.CommentDto;
import ru.sshibko.tms.dto.PagedDataDto;
import ru.sshibko.tms.exception.ResourceNotFoundException;
import ru.sshibko.tms.mapper.CommentMapper;
import ru.sshibko.tms.model.Comment;
import ru.sshibko.tms.model.Task;
import ru.sshibko.tms.repository.CommentRepository;
import ru.sshibko.tms.repository.TaskRepository;
import ru.sshibko.tms.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentService implements CRUDService<CommentDto> {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Override
    public CommentDto getById(Long id) {
        log.info("Comment get by ID: " + id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment with given id: " + id + " is not exists"));
        return CommentMapper.mapToCommentDto(comment);
    }

    @Override
    public Collection<CommentDto> getAll() {
        log.info("Getting all comments");
        List<Comment> commentList = commentRepository.findAll();
        return commentList
                .stream()
                .map(CommentMapper::mapToCommentDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto create(CommentDto commentDto) {
        log.info("Creating new comment");
        Comment comment = CommentMapper.mapToComment(commentDto);
        Long authorId = commentDto.getAuthorId();
        Long taskId = commentDto.getTaskId();

        comment.setAuthor(userRepository.findById(authorId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + authorId
                        + " is not exists")
        ));
        comment.setTask(taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task with given id " + taskId
                        + " is not exists")
        ));
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment with ID " + savedComment.getId() + " created successfully!");
        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto update(Long commentId, CommentDto updatedCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment with given id " + commentId
                        + " is not exists")
        );
        log.info("Updating comment with ID " + commentId);
        Long authorId = updatedCommentDto.getAuthorId();
        Long taskId = updatedCommentDto.getTaskId();

        comment.setText(updatedCommentDto.getText());

        comment.setAuthor(userRepository.findById(authorId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + authorId
                        + " is not exists")
        ));
        comment.setTask(taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task with given id " + taskId
                        + " is not exists")
        ));

        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment with ID " + commentId + " updated successfully!");

        return CommentMapper.mapToCommentDto(updatedComment);
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        log.info("Deleting comment with ID: " + commentId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment with given id: "
                        + commentId + " is not exists")
        );

        commentRepository.deleteById(commentId);
        log.info("Comment with ID " + commentId + " deleted successfully");
    }


    public PagedDataDto<Comment> findAllCommentsPaged(Long filter, PageRequest pageRequest) {
        Page<Comment> commentPage;
        if (filter != null) {
            commentPage = commentRepository.findByAuthorId(filter, pageRequest);
        } else {
            commentPage = commentRepository.findAll(pageRequest);
        }

        //Page<Task> pagedData = taskRepository.findAll(pageRequest);

        PagedDataDto<Comment> pagedDataDto = new PagedDataDto<>();
        pagedDataDto.setData(commentPage.getContent());
        pagedDataDto.setTotal(commentPage.getTotalPages());

        return pagedDataDto;
    }
}
