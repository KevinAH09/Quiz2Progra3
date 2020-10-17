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
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.FacturaDetalle;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;
import org.una.tienda.facturacion.repositories.FacturaDetalleRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author colo7
 */
@Service
public class IFacturaDetalleServiceImplementation implements IFacturaDetalleService {

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;
    @Autowired
    private IProductoPrecioService productoPrecioService;

    @Override
    public Optional<List<FacturaDetalleDTO>> findAll() {
        return (Optional<List<FacturaDetalleDTO>>) ConversionLista.findList((facturaDetalleRepository.findAll()), FacturaDetalleDTO.class);
    }

    @Override
    public Optional<FacturaDetalleDTO> findById(Long id) {
        return (Optional<FacturaDetalleDTO>) ConversionLista.oneToDto(facturaDetalleRepository.findById(id), FacturaDetalleDTO.class);
    }

    @Override
    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO, Long id) {
        if (facturaDetalleRepository.findById(id).isPresent()) {
            FacturaDetalle facturaDetalle = MapperUtils.EntityFromDto(facturaDetalleDTO, FacturaDetalle.class);
            facturaDetalle = facturaDetalleRepository.save(facturaDetalle);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(facturaDetalle, FacturaDetalleDTO.class));
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        facturaDetalleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalle) throws ProductoConDescuentoMayorAlPermitidoException{

        Optional<ProductoPrecioDTO> productoPrecio = productoPrecioService.findById(facturaDetalle.getProductoId().getId());

        if (productoPrecio.isEmpty()) {
            return null;
        }
        System.out.println(productoPrecio);
        if (facturaDetalle.getDescuentoFinal() > productoPrecio.get().getDescuentoMaximo()) {
            throw new ProductoConDescuentoMayorAlPermitidoException("Se intenta facturar un producto con un descuento mayor al permitido");
        }
        FacturaDetalle usuario = MapperUtils.EntityFromDto(facturaDetalle, FacturaDetalle.class);
        usuario = facturaDetalleRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, FacturaDetalleDTO.class);
    }

}
