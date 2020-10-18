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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionProductoExistenciaConEstadoInactivoException;

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
    ProductoExistenciaDTO productoExistenciaPrueba;

    ProductoDTO productoEjemplo;
    ProductoDTO productoPrueba;

    @BeforeEach
    public void setup() {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);
        productoExistenciaEjemplo = new ProductoExistenciaDTO() {
            {
                setCantidad(200);
                setProductosId(productoEjemplo);
            }
        };
    }

    public void initData() throws NoModificarInformacionProductoExistenciaConEstadoInactivoException {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);
        productoExistenciaPrueba = new ProductoExistenciaDTO() {
            {
                setCantidad(200);
                setProductosId(productoPrueba);
            }
        };
        productoExistenciaPrueba = productoExistenciaService.create(productoExistenciaPrueba);
        productoExistenciaPrueba.setEstado(false);
        productoExistenciaPrueba = productoExistenciaService.update(productoExistenciaPrueba, productoExistenciaPrueba.getId()).get();

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
    public void sePuedeModificarUnProductoExistenciaCorrectamente() throws NoModificarInformacionProductoExistenciaConEstadoInactivoException {

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
        } else {
            productoExistenciaEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void seEvitaModificarProductoEnExistenciaConEstadoInactivo() throws NoModificarInformacionProductoExistenciaConEstadoInactivoException {
        initData();
        assertThrows(NoModificarInformacionProductoExistenciaConEstadoInactivoException.class,
                () -> {
                    productoExistenciaService.update(productoExistenciaPrueba, productoExistenciaPrueba.getId());
                }
        );
    }

    @AfterEach
    public void tearDown() {
        if (productoExistenciaEjemplo != null) {
            if (productoExistenciaEjemplo.getId() != null) {
                productoExistenciaService.delete(productoExistenciaEjemplo.getId());
            }
            productoExistenciaEjemplo = null;
        }

    }
}
