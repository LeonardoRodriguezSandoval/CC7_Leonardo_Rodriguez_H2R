package com.example.H2R_Leonardo_Rodriguez_CC7.service;

import com.example.H2R_Leonardo_Rodriguez_CC7.controller.dto.CrearPedidoDTO;
import com.example.H2R_Leonardo_Rodriguez_CC7.controller.dto.PedidoDetalleDTO;
import com.example.H2R_Leonardo_Rodriguez_CC7.exception.CustomException;
import com.example.H2R_Leonardo_Rodriguez_CC7.model.EstadoPedido;
import com.example.H2R_Leonardo_Rodriguez_CC7.model.Pedido;
import com.example.H2R_Leonardo_Rodriguez_CC7.model.Producto;
import com.example.H2R_Leonardo_Rodriguez_CC7.repository.PedidoRepository;
import com.example.H2R_Leonardo_Rodriguez_CC7.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MyService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public MyService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Pedido crearPedido(CrearPedidoDTO payload) {
        Producto producto = productoRepository.findById(payload.getIdProducto())
                .orElseThrow( () -> new CustomException("El producto no existe"));

        Pedido pedido = pedidoRepository.findById(Long.valueOf(payload.getCantidad()))
                .orElseThrow( () -> new CustomException("El id del producto no existe"));

        if (!producto.isActivo()) {
            throw new CustomException("El producto no esta activo");
        }

        if (pedido.getCantidad() < 0){
            throw new CustomException("La cantidad del producto debe de ser mayor a cero");
        }

        if (producto.getStock() < payload.getCantidad()) {
            throw new CustomException("No hay suficiente stock");
        }

        Pedido pedido1 = new Pedido();
        pedido1.setNombreCliente(payload.getNombreCliente());
        pedido1.setCantidad(payload.getCantidad());
        pedido1.setPrecioUnitario(producto.getPrecio());
        pedido1.setTotal(producto.getPrecio() * payload.getCantidad());
        pedido1.setEstado(EstadoPedido.PENDIENTE);
        pedido1.setProducto(producto);

        producto.setStock(producto.getStock() - payload.getCantidad());
        productoRepository.save(producto);

        return pedidoRepository.save(pedido1);
    }

    @Transactional
    public Pedido cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe")
                );

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se pueden cancelar pedidos pendientes");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);

        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock() + pedido.getCantidad());
        productoRepository.save(producto);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido procesarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe")
                );

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se pueden procesar pedidos pendientes");
        }

        pedido.setEstado(EstadoPedido.PROCESADO);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Producto desactivarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no existe")
                );

        if (pedidoRepository.existsByProductoIdAndEstado(id, EstadoPedido.PENDIENTE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El producto tiene pedidos pendientes");
        }

        producto.setActivo(false);

        return productoRepository.save(producto);
    }

    @Transactional
    public void cancelarPedidosDeProducto(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no existe")
                );

        List<Pedido> pedidos = pedidoRepository
                .findAllByProductoIdAndEstado(idProducto, EstadoPedido.PENDIENTE);

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);

            pedido.setEstado(EstadoPedido.CANCELADO);
            producto.setStock(producto.getStock() + pedido.getCantidad());

            pedidoRepository.save(pedido);
        }

        productoRepository.save(producto);
    }



}
