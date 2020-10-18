/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoCorreoDireccionException;

/**
 *
 * @author Bosco
 */

public interface IClienteService {
    public Optional<List<ClienteDTO>> findAll();

    public Optional<ClienteDTO> findById(Long id);
    
    public ClienteDTO create(ClienteDTO cliente) throws ClienteConTelefonoCorreoDireccionException;

    public Optional<ClienteDTO> update(ClienteDTO cliente, Long id);
    
    public void delete(Long id);
}
