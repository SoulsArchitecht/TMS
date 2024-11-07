package ru.sshibko.tms.mapper;


import ru.sshibko.tms.dto.PagedDataDto;

import java.io.Serializable;
import java.util.List;

public class PagedDataMapper implements Serializable {

    public static PagedDataDto<Object> mapToDto(List<Object> data) {
        PagedDataDto<Object> pagedDataDto = new PagedDataDto<>();
        pagedDataDto.setTotal(100);
        return pagedDataDto;
    }

}
