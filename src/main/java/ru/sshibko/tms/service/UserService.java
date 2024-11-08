package ru.sshibko.tms.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.sshibko.tms.dto.PagedDataDto;
import ru.sshibko.tms.dto.UserDto;
import ru.sshibko.tms.exception.ResourceNotFoundException;
import ru.sshibko.tms.mapper.CommentMapper;
import ru.sshibko.tms.mapper.UserMapper;
import ru.sshibko.tms.model.Comment;
import ru.sshibko.tms.model.Task;
import ru.sshibko.tms.model.User;
import ru.sshibko.tms.repository.CommentRepository;
import ru.sshibko.tms.repository.TaskRepository;
import ru.sshibko.tms.repository.UserRepository;

import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserService implements CRUDService<UserDto> {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    @Override
    public UserDto getById(Long id) {
        log.info("User get by ID: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with given id: " + id + " is not exists"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public Collection<UserDto> getAll() {
        log.info("Getting all users");
        List<User> userList = userRepository.findAll();
        return userList
                .stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        log.info("Creating new user");
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        log.info("User with ID " + savedUser.getId() + " created successfully!");
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto update(Long userId, UserDto updatedUserDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id " + userId
                        + " is not exists")
        );

        log.info("Updating user with ID " + userId);

        user.setEmail(updatedUserDto.getEmail());
        user.setFullName(updatedUserDto.getFullName());
        user.setAttachedTasks(taskRepository.findByAssigneeIdNotPaged(userId));
        user.setCreatedTasks(taskRepository.findByAuthorIdNotPaged(userId));

        User updatedUser = userRepository.save(user);
        log.info("User with ID " + userId + " updated successfully!");

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        log.info("Deleting user with ID: " + userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with given id: "
                        + userId + " is not exists")
        );

        userRepository.deleteById(userId);
        log.info("User with ID " + userId + " deleted successfully");
    }

    public PagedDataDto<User> findAllUsersPaged(String filter, PageRequest pageRequest) {
        //TODO filters
/*        Page<User> userPage;
        if (filter != null) {
            userPage = userRepository.findByFullName("", pageRequest);
        } else {
            userPage = userRepository.findAll(pageRequest);
        }*/

        //Page<Task> pagedData = taskRepository.findAll(pageRequest);

        Page<User> userPage = userRepository.findAll(pageRequest);

        PagedDataDto<User> pagedDataDto = new PagedDataDto<>();
        pagedDataDto.setData(userPage.getContent());
        pagedDataDto.setTotal(userPage.getTotalPages());

        return pagedDataDto;
    }
}
