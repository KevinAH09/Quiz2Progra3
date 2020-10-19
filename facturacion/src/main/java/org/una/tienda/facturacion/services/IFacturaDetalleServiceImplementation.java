/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.FacturaDetalle;
import org.una.tienda.facturacion.entities.ProductoExistencia;
import org.una.tienda.facturacion.exceptions.NoCrearFacturaConProductoPrecioCeroException;
import org.una.tienda.facturacion.exceptions.NoCrearFacturasConCantidadCeroException;
import org.una.tienda.facturacion.exceptions.NoCrearFacturasConProductoInventarioCeroOMenorException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;
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
    private IFacturaDetalleService facturaDetalleService;
    @Autowired
    private IProductoPrecioService productoPrecioService;
    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    @Override
    public Optional<List<FacturaDetalleDTO>> findAll() {
        return (Optional<List<FacturaDetalleDTO>>) ConversionLista.findList((facturaDetalleRepository.findAll()), FacturaDetalleDTO.class);
    }

    @Override
    public Optional<FacturaDetalleDTO> findById(Long id) {
        return (Optional<FacturaDetalleDTO>) ConversionLista.oneToDto(facturaDetalleRepository.findById(id), FacturaDetalleDTO.class);
    }

    @Override
    public void delete(Long id) {
        facturaDetalleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalle) throws ProductoConDescuentoMayorAlPermitidoException,NoCrearFacturaConProductoPrecioCeroException,NoCrearFacturasConCantidadCeroException, NoCrearFacturasConProductoInventarioCeroOMenorException {

        Optional<ProductoPrecioDTO> productoPrecio = productoPrecioService.findByProductoId(facturaDetalle.getProductoId().getId());
        
         Optional<ProductoExistenciaDTO> productoExistencia = productoExistenciaService.findByProductoId(facturaDetalle.getProductoId().getId());
System.out.println(productoExistencia);
        if (productoPrecio.isEmpty()) {
            System.out.println("org.una.tienda.facturacion-CREATE -------------- ES NULOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            return null;
        }

        if (productoPrecio.get().getPrecioColones()== Double.valueOf("0.0")) {

            throw new NoCrearFacturaConProductoPrecioCeroException("Para que no se facture sobre productos con precio cero");

        }
        
        if(Double.valueOf("0.0") >= productoExistencia.get().getCantidad())
        {
            throw new NoCrearFacturasConProductoInventarioCeroOMenorException("Para que no se facture sobre productos con inventario cero o menor");
        }
        
        if(facturaDetalle.getCantidad()==Double.valueOf("0.0"))
        {
             throw new NoCrearFacturasConCantidadCeroException("Para que no se facture sobre productos con cantidad cero");
        }

        
        if (facturaDetalle.getDescuentoFinal() > productoPrecio.get().getDescuentoMaximo()) {
            throw new ProductoConDescuentoMayorAlPermitidoException("Se intenta facturar un producto con un descuento mayor al permitido");
        }
        FacturaDetalle usuario = MapperUtils.EntityFromDto(facturaDetalle, FacturaDetalle.class);
        usuario = facturaDetalleRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, FacturaDetalleDTO.class);
    }

    @Override
    @Transactional
    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalle, Long id) throws NoModificarInformacionFacturaDetalleConEstadoInactivoException {

        Optional<FacturaDetalleDTO> factura = facturaDetalleService.findById(facturaDetalle.getId());

        if (factura.isEmpty()) {
            return null;
        }
        System.out.println(factura);
        if (factura.get().isEstado() == false) {
            throw new NoModificarInformacionFacturaDetalleConEstadoInactivoException("Se intenta modificar una factura detalle con un estado inactivo");
        }
        FacturaDetalle facturaDetalle1 = MapperUtils.EntityFromDto(facturaDetalle, FacturaDetalle.class);
        facturaDetalle1 = facturaDetalleRepository.save(facturaDetalle1);
        return Optional.ofNullable(MapperUtils.DtoFromEntity(facturaDetalle1, FacturaDetalleDTO.class));

    }
}
