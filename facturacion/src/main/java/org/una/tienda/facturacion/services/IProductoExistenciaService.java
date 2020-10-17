/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;

/**
 *
 * @author Bosco
 */
public interface IProductoExistenciaService {
    public Optional<List<ProductoExistenciaDTO>> findAll();

    public Optional<ProductoExistenciaDTO> findById(Long id);
    
    public ProductoExistenciaDTO create(ProductoExistenciaDTO ProductoExistencia);

    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO ProductoExistencia, Long id);
}
