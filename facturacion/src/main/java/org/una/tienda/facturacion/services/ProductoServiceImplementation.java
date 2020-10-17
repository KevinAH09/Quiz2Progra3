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
import org.una.aeropuerto.utils.ConversionLista;
import org.una.aeropuerto.utils.MapperUtils;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.entities.Producto;
import org.una.tienda.facturacion.repositories.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cfugu
 */
@Service
public class ProductoServiceImplementation implements IProductoService {

    @Autowired
    private ProductoRepository ProductoRepository;

    @Override
    public Optional<List<ProductoDTO>> findAll() {
        return (Optional<List<ProductoDTO>>) ConversionLista.findList((ProductoRepository.findAll()), ProductoDTO.class);
    }

//    @Override
//    public Optional<ProductoDTO> findById(Long id) {
//        return (Optional<ProductoDTO>) ConversionLista.oneToDto(ProductoRepository.findById(id), ProductoDTO.class);
//    }
//    @Override
//    public ProductoDTO create(ProductoDTO Producto) {
//        Producto producto = MapperUtils.EntityFromDto(Producto, Producto.class);
//        producto = ProductoRepository.save(producto);
//        return MapperUtils.DtoFromEntity(producto, ProductoDTO.class);
//    }
    //    @Override
//    @Transactional
//    public void delete(Long id) {
//        ProductoRepository.deleteById(id);
//    }
    @Override
    public Optional<ProductoDTO> update(ProductoDTO Producto, Long id) {
        if (ProductoRepository.findById(id).isPresent()) {
            Producto producto = MapperUtils.EntityFromDto(Producto, Producto.class);
            producto = ProductoRepository.save(producto);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(producto, ProductoDTO.class));
        } else {
            return null;
        }

    }

    private Optional<ProductoDTO> oneToDto(Optional<Producto> one) {
        if (one.isPresent()) {
            ProductoDTO ProductoDTO = MapperUtils.DtoFromEntity(one.get(), ProductoDTO.class);
            return Optional.ofNullable(ProductoDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findById(Long id) {
        return oneToDto(ProductoRepository.findById(id));

    }

    @Override
    @Transactional
    public ProductoDTO create(ProductoDTO ProductoDTO) {
        Producto usuario = MapperUtils.EntityFromDto(ProductoDTO, Producto.class);
        usuario = ProductoRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, ProductoDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductoRepository.deleteById(id);
    }

}
