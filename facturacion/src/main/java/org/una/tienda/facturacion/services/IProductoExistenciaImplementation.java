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
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.entities.ProductoExistencia;
import org.una.tienda.facturacion.repositories.ProductoExistenciaRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author colo7
 */
@Service
public class IProductoExistenciaImplementation implements IProductoExistenciaService{
    
    @Autowired
    private ProductoExistenciaRepository productoExistenciaRepository;
            
    @Override
    public Optional<List<ProductoExistenciaDTO>> findAll() {
        return (Optional<List<ProductoExistenciaDTO>>) ConversionLista.findList((productoExistenciaRepository.findAll()), ProductoExistenciaDTO.class);
    }

    @Override
    public Optional<ProductoExistenciaDTO> findById(Long id) {
      return (Optional<ProductoExistenciaDTO>) ConversionLista.oneToDto(productoExistenciaRepository.findById(id), ProductoExistenciaDTO.class);
    }

    @Override
    public ProductoExistenciaDTO create(ProductoExistenciaDTO productoExistenciaDTO) {
        ProductoExistencia productoExistencia = MapperUtils.EntityFromDto(productoExistenciaDTO, ProductoExistencia.class);
        productoExistencia = productoExistenciaRepository.save(productoExistencia);
        return MapperUtils.DtoFromEntity(productoExistencia, ProductoExistenciaDTO.class);
    }

    @Override
    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO productoExistenciaDTO, Long id) {
         if (productoExistenciaRepository.findById(id).isPresent()) {
            ProductoExistencia productoExistencia = MapperUtils.EntityFromDto(productoExistenciaDTO, ProductoExistencia.class);
            productoExistencia = productoExistenciaRepository.save(productoExistencia);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(productoExistencia, ProductoExistenciaDTO.class));
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
       productoExistenciaRepository.deleteById(id);
    }
    
}
