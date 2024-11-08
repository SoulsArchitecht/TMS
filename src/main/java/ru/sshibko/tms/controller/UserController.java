package ru.sshibko.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sshibko.tms.dto.PagedDataDto;
import ru.sshibko.tms.dto.UserDto;
import ru.sshibko.tms.model.User;
import ru.sshibko.tms.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @RequestBody UserDto updatedUserDto) {
        UserDto userDto = userService.update(userId, updatedUserDto);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User with id " + id + " deleted successfully");
    }

    @GetMapping
    public ResponseEntity<PagedDataDto<User>> getAllUsersPaged(
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PagedDataDto<User> pagedDataDto = userService.findAllUsersPaged(filter, PageRequest.of(page, size));
        return ResponseEntity.ok(pagedDataDto);
    }

    @GetMapping("/")
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAll()
        );
    }
}
