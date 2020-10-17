/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class ProductoExistenciaServiceImpletationTests {
    
     @Autowired
    private IProductoService productoService;

    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    ProductoExistenciaDTO productoExistenciaEjemplo;

     ProductoDTO productoEjemplo;
    
    @BeforeEach
    public void setup() {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);
        productoExistenciaEjemplo = new ProductoExistenciaDTO(){
            {
                setCantidad(200);
                setProductosId(productoEjemplo);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoExistenciaCorrectamente() {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        Optional<ProductoExistenciaDTO> productoExistenciaEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoExistenciaEncontrado.isPresent()) {
            ProductoExistenciaDTO productoExistencia = productoExistenciaEncontrado.get();
            assertEquals(productoExistenciaEjemplo.getId(), productoExistencia.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }
    @Test
    public void sePuedeModificarUnProductoExistenciaCorrectamente() {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);
        productoExistenciaEjemplo.setCantidad(39393);
        productoExistenciaService.update(productoExistenciaEjemplo, productoExistenciaEjemplo.getId());
        Optional<ProductoExistenciaDTO> productoEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoExistenciaDTO productoExistencia = productoEncontrado.get();
            Assertions.assertEquals(productoExistenciaEjemplo.getId(), productoExistencia.getId());
            Assertions.assertEquals(productoExistenciaEjemplo.getCantidad(), productoExistencia.getCantidad());
            Assertions.assertEquals(productoExistenciaEjemplo.getProductosId().getId(), productoExistencia.getProductosId().getId());
        } else {
            fail("No se encontro la información en la BD");
        }
    }
    @Test
    public void sePuedeEliminarUnProductoExistenciaCorrectamente() {
        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);
        productoExistenciaService.delete(productoExistenciaEjemplo.getId());
        Optional<ProductoExistenciaDTO> productoEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            fail("El objeto no ha sido eliminado de la BD");
        }else{
            productoExistenciaEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @AfterEach
    public void tearDown() {
        if (productoExistenciaEjemplo != null) {
            productoExistenciaService.delete(productoExistenciaEjemplo.getId());
            productoExistenciaEjemplo = null;
        }

    }
}
