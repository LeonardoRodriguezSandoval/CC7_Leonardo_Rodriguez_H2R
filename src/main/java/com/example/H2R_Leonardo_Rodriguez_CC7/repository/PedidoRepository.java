package com.example.H2R_Leonardo_Rodriguez_CC7.repository;

import com.example.H2R_Leonardo_Rodriguez_CC7.model.EstadoPedido;
import com.example.H2R_Leonardo_Rodriguez_CC7.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByEstado(EstadoPedido estado);

    List<Pedido> findAllByProductoIdAndEstado(Long idProducto, EstadoPedido estado);

    boolean existsByProductoIdAndEstado(Long idProducto, EstadoPedido estado);
}
