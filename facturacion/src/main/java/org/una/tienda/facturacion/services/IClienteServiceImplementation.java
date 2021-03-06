/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.exceptions.ClienteSinCorreoException;
import org.una.tienda.facturacion.exceptions.ClienteSinDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteSinTelefonoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionClienteConEstadoInactivoException;
import org.una.tienda.facturacion.repositories.ClienteRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author colo7
 */
@Service
public class IClienteServiceImplementation implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private IClienteService clienteService;

    @Override
    public Optional<List<ClienteDTO>> findAll() {
        return (Optional<List<ClienteDTO>>) ConversionLista.findList((clienteRepository.findAll()), ClienteDTO.class);
    }

    @Override
    public Optional<ClienteDTO> findById(Long id) {
        return (Optional<ClienteDTO>) ConversionLista.oneToDto(clienteRepository.findById(id), ClienteDTO.class);
    }

    @Override
    public ClienteDTO create(ClienteDTO clienteDTO) throws ClienteSinDireccionException,ClienteSinTelefonoException,ClienteSinCorreoException {
        if (clienteDTO.getTelefono() == null) {
            throw new ClienteSinTelefonoException("Se intenta guardar un cliente sin telefono asignado");
        }
        if (clienteDTO.getDireccion() == null) {
            throw new ClienteSinDireccionException("Se intenta guardar un cliente sin direccion asignada");

        }
        if (clienteDTO.getEmail() == null) {
            throw new ClienteSinCorreoException("Se intenta guardar un cliente sin correo asignado");

        }
        Cliente cliente = MapperUtils.EntityFromDto(clienteDTO, Cliente.class);
        cliente = clienteRepository.save(cliente);
        return MapperUtils.DtoFromEntity(cliente, ClienteDTO.class);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> update(ClienteDTO cliente1, Long id) throws NoModificarInformacionClienteConEstadoInactivoException {

        Optional<ClienteDTO> factura = clienteService.findById(cliente1.getId());

        if (factura.isEmpty()) {
            return null;
        }
        System.out.println(factura);
        if (factura.get().isEstado() == false) {
            throw new NoModificarInformacionClienteConEstadoInactivoException("Se intenta modificar una factura detalle con un estado inactivo");
        }
        Cliente cliente = MapperUtils.EntityFromDto(cliente1, Cliente.class);
        cliente = clienteRepository.save(cliente);
        return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente, ClienteDTO.class));
    }
}
