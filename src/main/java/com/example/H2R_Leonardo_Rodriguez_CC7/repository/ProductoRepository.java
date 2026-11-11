package com.example.H2R_Leonardo_Rodriguez_CC7.repository;

import com.example.H2R_Leonardo_Rodriguez_CC7.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
