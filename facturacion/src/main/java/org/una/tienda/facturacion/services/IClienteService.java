/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.exceptions.ClienteSinCorreoException;
import org.una.tienda.facturacion.exceptions.ClienteSinDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteSinTelefonoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionClienteConEstadoInactivoException;

/**
 *
 * @author Bosco
 */
public interface IClienteService {

    public Optional<List<ClienteDTO>> findAll();

    public Optional<ClienteDTO> findById(Long id);

    public ClienteDTO create(ClienteDTO cliente) throws ClienteSinDireccionException,ClienteSinTelefonoException,ClienteSinCorreoException;

    public Optional<ClienteDTO> update(ClienteDTO cliente, Long id) throws NoModificarInformacionClienteConEstadoInactivoException;

    public void delete(Long id);
}
