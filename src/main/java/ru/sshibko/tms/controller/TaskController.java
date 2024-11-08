package ru.sshibko.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sshibko.tms.dto.PagedDataDto;
import ru.sshibko.tms.dto.TaskDto;
import ru.sshibko.tms.model.Task;
import ru.sshibko.tms.service.TaskService;

import java.util.Collection;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto newTask = taskService.create(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") Long id,
                                              @RequestBody TaskDto updatedTaskDto) {
        TaskDto taskDto = taskService.update(id, updatedTaskDto);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.ok("Task with id " + taskId + " deleted successfully!");
    }

    @GetMapping
    public ResponseEntity<PagedDataDto<Task>> getAllTaskPaged(
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PagedDataDto<Task> pagedDataDto = taskService.findAllTasksPaged(filter, PageRequest.of(page, size));
        return ResponseEntity.ok(pagedDataDto);
    }

    @GetMapping("/")
    public ResponseEntity<Collection<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(
                taskService.getAll()
        );
    }

}
