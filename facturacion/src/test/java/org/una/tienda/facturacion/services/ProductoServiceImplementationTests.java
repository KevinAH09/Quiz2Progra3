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
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionProductoConEstadoInactivoException;

/**
 *
 * @author Bosco
 */
@SpringBootTest
public class ProductoServiceImplementationTests {

    @Autowired
    private IProductoService productoService;

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
    }

    public void initData() throws NoModificarInformacionProductoConEstadoInactivoException {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);
        productoPrueba.setEstado(false);
        productoPrueba = productoService.update(productoPrueba, productoPrueba.getId()).get();

    }

    @Test
    public void sePuedeCrearUnProductoCorrectamente() {

        productoEjemplo = productoService.create(productoEjemplo);

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());
        System.out.println(productoEjemplo);
        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();

            assertEquals(productoEjemplo.getId(), producto.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnProductoCorrectamente() throws NoModificarInformacionFacturaDetalleConEstadoInactivoException, NoModificarInformacionProductoConEstadoInactivoException {

        productoEjemplo = productoService.create(productoEjemplo);
        productoEjemplo.setDescripcion("Producto modificado");
        productoService.update(productoEjemplo, productoEjemplo.getId());
        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();
            Assertions.assertEquals(productoEjemplo.getId(), producto.getId());
            Assertions.assertEquals(productoEjemplo.getDescripcion(), producto.getDescripcion());
            Assertions.assertEquals(productoEjemplo.getImpuesto(), producto.getImpuesto());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnProductoCorrectamente() {
        productoEjemplo = productoService.create(productoEjemplo);
        productoService.delete(productoEjemplo.getId());
        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());

        if (productoEncontrado != null) {
            fail("El objeto no ha sido eliminado de la BD");
        } else {
            productoEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void seEvitaModificarProductoConEstadoInactivo() throws NoModificarInformacionProductoConEstadoInactivoException, NoModificarInformacionProductoConEstadoInactivoException, NoModificarInformacionFacturaDetalleConEstadoInactivoException {
        initData();
        assertThrows(NoModificarInformacionProductoConEstadoInactivoException.class,
                () -> {
                    productoService.update(productoPrueba, productoPrueba.getId());
                }
        );
    }

    @AfterEach
    public void tearDown() {
        if (productoEjemplo != null) {
            if (productoEjemplo.getId() != null) {
                productoService.delete(productoEjemplo.getId());
            }

            productoEjemplo = null;
        }

    }
}
