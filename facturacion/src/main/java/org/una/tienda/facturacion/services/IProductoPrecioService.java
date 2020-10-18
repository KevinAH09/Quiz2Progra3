/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionProductoPrecioConEstadoInactivoException;

/**
 *
 * @author Bosco
 */
public interface IProductoPrecioService {
     public Optional<List<ProductoPrecioDTO>> findAll();

    public Optional<ProductoPrecioDTO> findById(Long id);
    
    public ProductoPrecioDTO create(ProductoPrecioDTO ProductoPrecio);

    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO ProductoPrecio, Long id)throws NoModificarInformacionProductoPrecioConEstadoInactivoException;
    
    public void delete(Long id);
}
