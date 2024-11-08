package ru.sshibko.tms.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.sshibko.tms.dto.PagedDataDto;
import ru.sshibko.tms.dto.TaskDto;
import ru.sshibko.tms.exception.ResourceNotFoundException;
import ru.sshibko.tms.mapper.TaskMapper;
import ru.sshibko.tms.model.Comment;
import ru.sshibko.tms.model.Priority;
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
public class TaskService implements CRUDService<TaskDto> {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public TaskDto getById(Long id) {
        log.info("Task get by ID: " + id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with given id: " + id + " is not exists"));
        return TaskMapper.mapToTaskDto(task);
    }

    @Override
    public Collection<TaskDto> getAll() {
        log.info("Getting all tasks");
        List<Task> taskList = taskRepository.findAll();
        return taskList
                .stream()
                .map(TaskMapper::mapToTaskDto)
                .toList();
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto taskDto) {
        log.info("Creating new task");
        Task task = TaskMapper.mapToTask(taskDto);
        Long authorId = taskDto.getAuthorId();
        Long assigneeId = taskDto.getAssigneeId();
        //List<Comment> comments = taskDto.getComments();
        List<Long> commentsIdList = taskRepository.findCommentsByTaskId(task.getId());

/*        List<Long> ids = comments.stream()
                .map(Comment::getId).collect(Collectors.toList());*/
        List<Comment> comments = commentRepository.findAllByIdIn(commentsIdList);

        //task.setComments(comments);
        task.setAuthor(userRepository.findById(authorId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + authorId
                        + " is not exists")
        ));
        task.setAssignee(userRepository.findById(assigneeId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + assigneeId
                        + " is not exists")
        ));
        Task savedTask = taskRepository.save(task);
        log.info("Task with ID " + savedTask.getId() + " created successfully!");
        return TaskMapper.mapToTaskDto(savedTask);
    }

    @Override
    @Transactional
    public TaskDto update(Long taskId, TaskDto updatedTaskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task with given id " + taskId
                        + " is not exists")
        );
        log.info("Updating task with ID " + taskId);
        Long authorId = updatedTaskDto.getAuthorId();
        Long assigneeId = updatedTaskDto.getAssigneeId();
        //List<Comment> comments = updatedTaskDto.getComments();

        task.setTitle(updatedTaskDto.getTitle());
        task.setDescription(updatedTaskDto.getDescription());
        task.setCreatedAt(updatedTaskDto.getCreatedAt());
        task.setUpdatedAt(updatedTaskDto.getUpdatedAt());
        task.setStatus(updatedTaskDto.getStatus());
        task.setPriority(updatedTaskDto.getPriority());
        task.setAssignee(userRepository.findById(assigneeId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + assigneeId
                + " is not exists")
        ));
        task.setAuthor(userRepository.findById(authorId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + authorId
                + " is not exists")
        ));
        //task.setComments(updatedTaskDto.getComments());
        //List<Long> commentsIdList = taskRepository.findCommentsByTaskId(task.getId());
        //List<Comment> comments = commentRepository.findAllByIdIn(commentsIdList);
        //task.setComments(comments);

        Task updatedTask = taskRepository.save(task);
        log.info("Task with ID " + taskId + " updated successfully!");

        return TaskMapper.mapToTaskDto(updatedTask);
    }

    @Override
    public void delete(Long taskId) {
        log.info("Deleting task with ID: " + taskId);
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task with given id: "
                + taskId + " is not exists")
        );

        taskRepository.deleteById(taskId);
        log.info("Task with ID " + taskId + " deleted successfully");
    }

    public PagedDataDto<Task> findAllTasksPaged(String filter, PageRequest pageRequest) {
        //TODO filters
/*        Page<Task> taskPage;
        if (filter != null) {
            taskPage = taskRepository.findByPriority(Priority.HIGH.toString(), pageRequest);
        } else {
            taskPage = taskRepository.findAll(pageRequest);
        }*/

        //Page<Task> pagedData = taskRepository.findAll(pageRequest);

        Page<Task> taskPage = taskRepository.findAll(pageRequest);

        PagedDataDto<Task> pagedDataDto = new PagedDataDto<>();
        pagedDataDto.setData(taskPage.getContent());
        pagedDataDto.setTotal(taskPage.getTotalPages());

        return pagedDataDto;
    }
}
