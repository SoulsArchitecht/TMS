package ru.sshibko.tms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sshibko.tms.model.Priority;
import ru.sshibko.tms.model.Status;
import ru.sshibko.tms.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatus(Status status, Pageable pageable);

    Page<Task> findByPriority(Priority priority, Pageable pageable);

    Page<Task> findByAuthorId(Long authorId, Pageable pageable);

    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);
}
