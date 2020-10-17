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
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.repositories.ClienteRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author colo7
 */
@Service
public class IClienteServiceImplementation implements IClienteService{
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public Optional<List<ClienteDTO>> findAll() {
      return (Optional<List<ClienteDTO>>) ConversionLista.findList((clienteRepository.findAll()), ClienteDTO.class);
    }

    @Override
    public Optional<ClienteDTO> findById(Long id) {
       return (Optional<ClienteDTO>) ConversionLista.oneToDto(clienteRepository.findById(id), ClienteDTO.class);
    }

    @Override
    public ClienteDTO create(ClienteDTO clienteDTO) {
        Cliente cliente = MapperUtils.EntityFromDto(clienteDTO, Cliente.class);
        cliente = clienteRepository.save(cliente);
        return MapperUtils.DtoFromEntity(cliente, ClienteDTO.class);
    }

    @Override
    public Optional<ClienteDTO> update(ClienteDTO clienteDto, Long id) {
        if (clienteRepository.findById(id).isPresent()) {
            Cliente cliente = MapperUtils.EntityFromDto(clienteDto, Cliente.class);
            cliente = clienteRepository.save(cliente);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente,ClienteDTO.class));
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
       clienteRepository.deleteById(id);
    }
    
}
