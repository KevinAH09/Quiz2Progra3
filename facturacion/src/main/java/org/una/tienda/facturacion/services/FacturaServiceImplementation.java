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
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.entities.Factura;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionConEstadoInactivoException;
import org.una.tienda.facturacion.repositories.FacturaRepository;
import org.una.tienda.facturacion.utils.ConversionLista;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author Bosco
 */
@Service
public class FacturaServiceImplementation implements IFacturaService {

    @Autowired
    private FacturaRepository FacturaRepository;
    @Autowired
    private IFacturaService facturaService;

    @Override
    public Optional<List<FacturaDTO>> findAll() {
        return (Optional<List<FacturaDTO>>) ConversionLista.findList((FacturaRepository.findAll()), FacturaDTO.class);
    }

    private Optional<FacturaDTO> oneToDto(Optional<Factura> one) {
        if (one.isPresent()) {
            FacturaDTO FacturaDTO = MapperUtils.DtoFromEntity(one.get(), FacturaDTO.class);
            return Optional.ofNullable(FacturaDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaDTO> findById(Long id) {
        return oneToDto(FacturaRepository.findById(id));

    }

    @Override
    @Transactional
    public FacturaDTO create(FacturaDTO FacturaDTO) {
        Factura factura = MapperUtils.EntityFromDto(FacturaDTO, Factura.class);
        factura = FacturaRepository.save(factura);
        return MapperUtils.DtoFromEntity(factura, FacturaDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FacturaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<FacturaDTO> update(FacturaDTO facturadto, Long id) throws NoModificarInformacionConEstadoInactivoException {

        Optional<FacturaDTO> factura = facturaService.findById(facturadto.getId());

        if (factura.isEmpty()) {
            return null;
        }
        System.out.println(factura);
        if (factura.get().isEstado() == false) {
            throw new NoModificarInformacionConEstadoInactivoException("Se intenta modificar una factura con un estado inactivo");
        }
        Factura Factura = MapperUtils.EntityFromDto(facturadto, Factura.class);
        Factura = FacturaRepository.save(Factura);
        return Optional.ofNullable(MapperUtils.DtoFromEntity(Factura, FacturaDTO.class));

    }

}
