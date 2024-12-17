package com.webknot.webtrak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Getter
@Setter
public class GenericResponseDTO {
    String message;
    Object data;
}
