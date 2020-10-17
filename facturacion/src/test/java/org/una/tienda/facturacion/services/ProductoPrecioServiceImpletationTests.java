/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;

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
    
    

    ProductoPrecioDTO ProductoPrecioEjemplo;

    @BeforeEach
    public void setup() {
        ProductoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setDescuentoMaximo(0);
                setDescuentoPromocional(0);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoPrecioCorrectamente() {
 
        ProductoPrecioEjemplo = productoPrecioService.create(ProductoPrecioEjemplo);

        Optional<ProductoPrecioDTO> ProductoPrecioEncontrado = productoPrecioService.findById(ProductoPrecioEjemplo.getId());

        if (ProductoPrecioEncontrado.isPresent()) {
            ProductoPrecioDTO ProductoPrecio = ProductoPrecioEncontrado.get();
            assertEquals(ProductoPrecioEjemplo.getId(), ProductoPrecio.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @AfterEach
    public void tearDown() {
        if (ProductoPrecioEjemplo != null) {
            productoPrecioService.delete(ProductoPrecioEjemplo.getId());
            ProductoPrecioEjemplo = null;
        }

    }
}
