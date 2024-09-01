package ru.sshibko.tms.mapper;

import ru.sshibko.tms.dto.CommentDto;
import ru.sshibko.tms.model.Comment;

import java.io.Serializable;

public class CommentMapper implements Serializable {

    public static CommentDto mapToCommentDto (Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setTaskId(comment.getTask().getId());
        commentDto.setAuthorId(comment.getAuthor().getId());
        commentDto.setCreatedAt(comment.getCreatedAt());

        return commentDto;
    }

    public static Comment mapToComment(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setCreatedAt(commentDto.getCreatedAt());

        return comment;
    }
}
