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
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.entities.FacturaDetalle;
import org.una.tienda.facturacion.repositories.FacturaDetalleRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author colo7
 */
@Service
public class IFacturaDetalleServiceImplementation implements IFacturaDetalleService{

     @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;
    
    @Override
    public Optional<List<FacturaDetalleDTO>> findAll() {
        return (Optional<List<FacturaDetalleDTO>>) ConversionLista.findList((facturaDetalleRepository.findAll()), FacturaDetalleDTO.class);
    }

    @Override
    public Optional<FacturaDetalleDTO> findById(Long id) {
         return (Optional<FacturaDetalleDTO>) ConversionLista.oneToDto(facturaDetalleRepository.findById(id), FacturaDetalleDTO.class);
    }

    @Override
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalleDTO) {
       FacturaDetalle facturaDetalle = MapperUtils.EntityFromDto(facturaDetalleDTO, FacturaDetalle.class);
        facturaDetalle = facturaDetalleRepository.save(facturaDetalle);
        return MapperUtils.DtoFromEntity(facturaDetalle, FacturaDetalleDTO.class);
    }

    @Override
    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO, Long id) {
        if (facturaDetalleRepository.findById(id).isPresent()) {
            FacturaDetalle facturaDetalle = MapperUtils.EntityFromDto(facturaDetalleDTO, FacturaDetalle.class);
            facturaDetalle = facturaDetalleRepository.save(facturaDetalle);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(facturaDetalle,FacturaDetalleDTO.class));
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
      facturaDetalleRepository.deleteById(id);
    }
    
}
