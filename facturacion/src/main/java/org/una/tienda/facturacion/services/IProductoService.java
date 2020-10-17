/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ProductoDTO;

/**
 *
 * @author Bosco
 */
public interface IProductoService {
    public Optional<List<ProductoDTO>> findAll();

    public Optional<ProductoDTO> findById(Long id);
    
    public ProductoDTO create(ProductoDTO Producto);

    public Optional<ProductoDTO> update(ProductoDTO Producto, Long id);
}
