package ru.sshibko.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.sshibko.tms.model.Task;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto implements Serializable {

    private Long id;
    private String email;
    private String fullName;
}
