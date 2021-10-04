package com.crud.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoClienteDTO {
    private Long codigo;
    private String nome;
    private Integer idade;
    private String cpf;


}
