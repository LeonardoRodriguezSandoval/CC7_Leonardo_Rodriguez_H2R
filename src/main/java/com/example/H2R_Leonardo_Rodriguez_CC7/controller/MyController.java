package com.example.H2R_Leonardo_Rodriguez_CC7.controller;

import com.example.H2R_Leonardo_Rodriguez_CC7.controller.dto.CrearPedidoDTO;
import com.example.H2R_Leonardo_Rodriguez_CC7.controller.dto.PedidoDetalleDTO;
import com.example.H2R_Leonardo_Rodriguez_CC7.model.Pedido;
import com.example.H2R_Leonardo_Rodriguez_CC7.model.Producto;
import com.example.H2R_Leonardo_Rodriguez_CC7.service.MyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*")
public class MyController {
    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @PostMapping("/registrar-pedido")
    public ResponseEntity<Pedido> crearPedido(@RequestBody @Valid CrearPedidoDTO payload) {
        return ResponseEntity
                .status(201)
                .body(service.crearPedido(payload));
    }

    @PatchMapping("/cancelar-pedido/{id}")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(service.cancelarPedido(id));
    }

    @PatchMapping("/procesar-pedido/{id}")
    public ResponseEntity<Pedido> procesarPedido(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(service.procesarPedido(id));
    }

    @PatchMapping("/desactivar-producto/{id}")
    public ResponseEntity<Producto> desactivarProducto(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(service.desactivarProducto(id));
    }

    @PatchMapping("/cancelar-pedidos-producto/{id}")
    public ResponseEntity<String> cancelarPedidosDeProducto(@PathVariable Long id) {
        service.cancelarPedidosDeProducto(id);
        return ResponseEntity
                .status(200)
                .body("Se cancelaron correctamente los pedidos pendientes");
    }
}