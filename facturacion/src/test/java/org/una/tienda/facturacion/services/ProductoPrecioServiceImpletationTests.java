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
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionConEstadoInactivoException;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class ProductoPrecioServiceImpletationTests {

    @Autowired
    private IProductoPrecioService productoPrecioService;

    @Autowired
    private IProductoService productoService;

    ProductoDTO productoEjemplo;
    ProductoDTO productoPrueba;

    ProductoPrecioDTO productoPrecioEjemplo;
    ProductoPrecioDTO productoPrecioPrueba;

    @BeforeEach
    public void setup() {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);
        productoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setDescuentoMaximo(0);
                setDescuentoPromocional(0);
                setPrecioColones(200);
                setProductosId(productoEjemplo);
            }
        };
    }

    public void initData() throws NoModificarInformacionConEstadoInactivoException {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);
        productoPrecioPrueba = new ProductoPrecioDTO() {
            {
                setDescuentoMaximo(0);
                setDescuentoPromocional(0);
                setPrecioColones(200);
                setProductosId(productoPrueba);
            }
        };
        productoPrecioPrueba = productoPrecioService.create(productoPrecioPrueba);
        productoPrecioPrueba.setEstado(false);
        productoPrecioPrueba = productoPrecioService.update(productoPrecioPrueba, productoPrecioPrueba.getId()).get();

    }

    @Test
    public void sePuedeCrearUnProductoPrecioCorrectamente() {

        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

        Optional<ProductoPrecioDTO> ProductoPrecioEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (ProductoPrecioEncontrado.isPresent()) {
            ProductoPrecioDTO ProductoPrecio = ProductoPrecioEncontrado.get();
            assertEquals(productoPrecioEjemplo.getId(), ProductoPrecio.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnProductoPrecioCorrectamente() throws NoModificarInformacionConEstadoInactivoException {

        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);
        productoPrecioEjemplo.setDescuentoPromocional(0);
        productoPrecioService.update(productoPrecioEjemplo, productoPrecioEjemplo.getId());
        Optional<ProductoPrecioDTO> productoPrecioEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (productoPrecioEncontrado.isPresent()) {
            ProductoPrecioDTO productoPrecio = productoPrecioEncontrado.get();
            Assertions.assertEquals(productoPrecioEjemplo.getId(), productoPrecio.getId());
            Assertions.assertEquals(productoPrecioEjemplo.getDescuentoMaximo(), productoPrecio.getDescuentoMaximo());
            Assertions.assertEquals(productoPrecioEjemplo.getDescuentoPromocional(), productoPrecio.getDescuentoPromocional());
            Assertions.assertEquals(productoPrecioEjemplo.getPrecioColones(), productoPrecio.getPrecioColones());
            Assertions.assertEquals(productoPrecioEjemplo.getProductosId().getId(), productoPrecio.getProductosId().getId());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnProductoPrecioCorrectamente() {
        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);
        productoPrecioService.delete(productoPrecioEjemplo.getId());
        Optional<ProductoPrecioDTO> productoPrecioEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (productoPrecioEncontrado != null) {
            fail("El objeto no ha sido eliminado de la BD");
        } else {
            productoPrecioEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void seEvitaModificarProductoPrecioConEstadoInactivo() throws NoModificarInformacionConEstadoInactivoException {
        initData();
        assertThrows(NoModificarInformacionConEstadoInactivoException.class,
                () -> {
                    productoPrecioService.update(productoPrecioPrueba, productoPrecioPrueba.getId());
                }
        );
    }

    @AfterEach
    public void tearDown() {
        if (productoPrecioEjemplo != null) {
            productoPrecioService.delete(productoPrecioEjemplo.getId());
            productoPrecioEjemplo = null;
        }

    }
}
