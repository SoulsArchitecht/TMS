package ru.sshibko.tms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedDataDto<T> implements Serializable {

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<T> data;

    private long total;
}
