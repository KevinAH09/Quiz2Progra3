/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.una.tienda.facturacion.entities.ProductoPrecio;

/**
 *
 * @author Bosco
 */
public interface ProductoPrecioRepository extends JpaRepository<ProductoPrecio, Long>{
    
      @Query("SELECT u FROM ProductoPrecio u LEFT JOIN u.productosId d WHERE  d.id =:id")
      public Optional<ProductoPrecio> findByProductoId(@Param("id") Long id);
    
}
