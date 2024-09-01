package ru.sshibko.tms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sshibko.tms.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByTaskId(Long taskId, Pageable pageable);

    Page<Comment> findByAuthorId(Long authorId, Pageable pageable);
}
