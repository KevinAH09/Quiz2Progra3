/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionProductoExistenciaConEstadoInactivoException;

/**
 *
 * @author Bosco
 */
public interface IProductoExistenciaService {
    public Optional<List<ProductoExistenciaDTO>> findAll();

    public Optional<ProductoExistenciaDTO> findById(Long id);
    
    public Optional<ProductoExistenciaDTO> findByProductoId(Long id);
    
    public ProductoExistenciaDTO create(ProductoExistenciaDTO ProductoExistencia);

    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO ProductoExistencia, Long id)throws NoModificarInformacionProductoExistenciaConEstadoInactivoException;
    
    public void delete(Long id);
}
