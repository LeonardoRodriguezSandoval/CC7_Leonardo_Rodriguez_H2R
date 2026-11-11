package com.example.H2R_Leonardo_Rodriguez_CC7.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalleDTO {
    private Long id;
    private String nombreCliente;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private String estado;
    private Long idProducto;
    private String nombreProducto;
    private String categoriaProducto;
}
