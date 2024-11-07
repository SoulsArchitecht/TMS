package ru.sshibko.tms.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto implements Serializable {

    private Long id;
    private String email;
    private String fullName;
}
