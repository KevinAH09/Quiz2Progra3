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
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.entities.ProductoExistencia;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionProductoExistenciaConEstadoInactivoException;
import org.una.tienda.facturacion.repositories.ProductoExistenciaRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author colo7
 */
@Service
public class IProductoExistenciaServiceImplementation implements IProductoExistenciaService {

    @Autowired
    private ProductoExistenciaRepository productoExistenciaRepository;
    @Autowired
    private IProductoExistenciaService productoExistenciaService;

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
    public void delete(Long id) {
        productoExistenciaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO ProductoExistencia, Long id) throws NoModificarInformacionProductoExistenciaConEstadoInactivoException {

        Optional<ProductoExistenciaDTO> product = productoExistenciaService.findById(ProductoExistencia.getId());

        if (product.isEmpty()) {
            return null;
        }
        System.out.println(product);
        if (product.get().isEstado() == false) {
            throw new NoModificarInformacionProductoExistenciaConEstadoInactivoException("Se intenta modificar un producto con un estado inactivo");
        }
        ProductoExistencia productoExistencia = MapperUtils.EntityFromDto(ProductoExistencia, ProductoExistencia.class);
        productoExistencia = productoExistenciaRepository.save(productoExistencia);
        return Optional.ofNullable(MapperUtils.DtoFromEntity(productoExistencia, ProductoExistenciaDTO.class));

    }

    @Override
    public Optional<ProductoExistenciaDTO> findByProductoId(Long id) {
         return (Optional<ProductoExistenciaDTO>) ConversionLista.oneToDto(productoExistenciaRepository.findByProductoId(id), ProductoExistenciaDTO.class);
    }
}
