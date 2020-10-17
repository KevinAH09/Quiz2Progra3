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
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.ProductoPrecio;
import org.una.tienda.facturacion.repositories.ProductoPrecioRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author Bosco
 */
@Service
public class ProductoPrecioServiceImplementation implements IProductoPrecioService{
    @Autowired
    private ProductoPrecioRepository ProductoPrecioRepository;

    @Override
    public Optional<List<ProductoPrecioDTO>> findAll() {
        return (Optional<List<ProductoPrecioDTO>>) ConversionLista.findList((ProductoPrecioRepository.findAll()), ProductoPrecioDTO.class);
    }

    @Override
    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO ProductoPrecioDTO, Long id) {
        if (ProductoPrecioRepository.findById(id).isPresent()) {
            ProductoPrecio ProductoPrecio = MapperUtils.EntityFromDto(ProductoPrecioDTO, ProductoPrecio.class);
            ProductoPrecio = ProductoPrecioRepository.save(ProductoPrecio);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(ProductoPrecio, ProductoPrecioDTO.class));
        } else {
            return null;
        }

    }

    private Optional<ProductoPrecioDTO> oneToDto(Optional<ProductoPrecio> one) {
        if (one.isPresent()) {
            ProductoPrecioDTO ProductoPrecioDTO = MapperUtils.DtoFromEntity(one.get(), ProductoPrecioDTO.class);
            return Optional.ofNullable(ProductoPrecioDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoPrecioDTO> findById(Long id) {
        return oneToDto(ProductoPrecioRepository.findById(id));

    }

    @Override
    @Transactional
    public ProductoPrecioDTO create(ProductoPrecioDTO ProductoPrecioDTO) {
        ProductoPrecio productoPrecio = MapperUtils.EntityFromDto(ProductoPrecioDTO, ProductoPrecio.class);
        productoPrecio = ProductoPrecioRepository.save(productoPrecio);
        return MapperUtils.DtoFromEntity(productoPrecio, ProductoPrecioDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductoPrecioRepository.deleteById(id);
    }
}
