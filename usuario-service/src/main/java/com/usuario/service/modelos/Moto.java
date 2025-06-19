package com.usuario.service.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Moto {
    private Integer usuarioId;
    private String marca;
    private String modelo;
}
