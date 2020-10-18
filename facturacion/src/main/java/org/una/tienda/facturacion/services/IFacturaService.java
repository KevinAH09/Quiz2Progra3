/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionConEstadoInactivoException;

/**
 *
 * @author Bosco
 */
public interface IFacturaService {
    public Optional<List<FacturaDTO>> findAll();

    public Optional<FacturaDTO> findById(Long id);
    
    public void delete(Long id);
    
    public FacturaDTO create(FacturaDTO factura);

    public Optional<FacturaDTO> update(FacturaDTO factura, Long id)throws NoModificarInformacionConEstadoInactivoException;
}
