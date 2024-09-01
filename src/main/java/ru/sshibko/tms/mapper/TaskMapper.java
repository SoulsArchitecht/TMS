package ru.sshibko.tms.mapper;

import ru.sshibko.tms.dto.TaskDto;
import ru.sshibko.tms.model.Task;

import java.io.Serializable;

public class TaskMapper implements Serializable {

    public static TaskDto mapToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();

        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());
        taskDto.setAuthorId(task.getAuthor().getId());
        taskDto.setAssigneeId(task.getAuthor().getId());
        taskDto.setComments(task.getComments());

        return taskDto;
    }

    public static Task mapToTask(TaskDto taskDto) {
        Task task = new Task();

        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setCreatedAt(taskDto.getCreatedAt());
        task.setUpdatedAt(taskDto.getUpdatedAt());
        task.setComments(taskDto.getComments());

        return task;
    }
}
