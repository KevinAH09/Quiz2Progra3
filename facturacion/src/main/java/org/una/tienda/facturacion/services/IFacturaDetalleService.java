/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

/**
 *
 * @author Bosco
 */
public interface IFacturaDetalleService {
    public Optional<List<FacturaDetalleDTO>> findAll();

    public Optional<FacturaDetalleDTO> findById(Long id);
    
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalle)throws ProductoConDescuentoMayorAlPermitidoException;

    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalle, Long id);
    
    public void delete(Long id);
    
}
