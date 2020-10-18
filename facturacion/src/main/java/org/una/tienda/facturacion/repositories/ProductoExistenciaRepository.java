/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.una.tienda.facturacion.entities.ProductoExistencia;

/**
 *
 * @author Bosco
 */
public interface ProductoExistenciaRepository extends JpaRepository<ProductoExistencia, Long>{
     @Query("SELECT u FROM ProductoExistencia u LEFT JOIN u.productosId d WHERE  d.id =:id")
      public Optional<ProductoExistencia> findByProductoId(@Param("id") Long id);
}
