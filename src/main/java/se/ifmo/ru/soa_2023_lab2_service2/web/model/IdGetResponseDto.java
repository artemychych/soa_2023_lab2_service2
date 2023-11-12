package se.ifmo.ru.soa_2023_lab2_service2.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdGetResponseDto {
    private Integer id;
}
