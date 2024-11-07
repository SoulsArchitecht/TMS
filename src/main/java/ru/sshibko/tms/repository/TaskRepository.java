package ru.sshibko.tms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sshibko.tms.model.Comment;
import ru.sshibko.tms.model.Priority;
import ru.sshibko.tms.model.Status;
import ru.sshibko.tms.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    String byPrioritySortedQuery = "SELECT * FROM tasks ORDER BY priority DESC";
    String byStatusSortedQuery = "SELECT * FROM tasks ORDER BY status DESC";
    String byAuthorIdSortedQuery = "SELECT * FROM tasks ORDER BY author_id DESC";
    String byAssigneeSortedId = "SELECT * FROM tasks ORDER BY assignee_id DESC";
    String byAuthorId = "SELECT * FROM t as tasks WHERE t.author_id=:authorId";
    String byAssigneeId = "SELECT * FROM t as tasks WHERE t.assignee_id=:assigneeId";
    String byPriority = "SELECT * FROM tasks t WHERE t.priority=:filter";
    String searchQuery = "SELECT * FROM tasks  WHERE "
            + "title LIKE %:keyword%"
            + " OR description LIKE %:keyword%"
            + "author";
    String findComments = "SELECT c.id FROM comments c WHERE c.task_id=:taskId";

    Page<Task> findByStatus(Status status, Pageable pageable);

    @Query(value = byPriority, nativeQuery = true)
    Page<Task> findByPriority(@Param("filter") String priority, Pageable pageable);

    @Query(value = byAuthorId, nativeQuery = true)
    Page<Task> findByAuthorId(@Param("authorId") Long authorId, PageRequest pageRequest);

    @Query(value = byAssigneeId, nativeQuery = true)
    Page<Task> findByAssigneeId(@Param("assigneeId") Long assigneeId, Pageable pageable);

    @Query(value = searchQuery, nativeQuery = true)
    Page<Task> findByKeywordPaged(@Param("keyword") String keyword, PageRequest pageRequest);

    @Query(value = findComments, nativeQuery = true)
    List<Long> findCommentsByTaskId(@Param("taskId") Long taskId);



}
