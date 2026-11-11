package com.example.H2R_Leonardo_Rodriguez_CC7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDTO {
    @NotNull(message = "idProducto no debe estar vacio")
    private Long idProducto;

    @NotBlank(message = "nombreCliente no debe estar vacio")
    private String nombreCliente;

    @NotNull(message = "cantidad no debe estar vacia")
    @Positive(message = "cantidad debe ser mayor a cero")
    private Integer cantidad;
}
