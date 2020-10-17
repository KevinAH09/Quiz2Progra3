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
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.dtos.ProductoDTO;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class FacturaDetalleServiceImpletationTests {

    @Autowired
    private IFacturaDetalleService facturaDetalleService;

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IClienteService clienteService;
    
    @Autowired
    private IProductoService productoService;

    FacturaDetalleDTO facturaDetalleEjemplo;

    FacturaDTO facturaEjemplo;

    ClienteDTO clienteEjemplo;

    ProductoDTO productoEjemplo;

    @BeforeEach
    public void setup() {

        clienteEjemplo = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
        clienteEjemplo = clienteService.create(clienteEjemplo);
        facturaEjemplo = new FacturaDTO() {
            {
                setCaja(21);
                setDescuentoGeneral(2);
                setClienteId(clienteEjemplo);

            }
        };
        facturaEjemplo = facturaService.create(facturaEjemplo);

        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);
        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaEjemplo);
                setProductoId(productoEjemplo);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaDetalleCorrectamente() {

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        Optional<FacturaDetalleDTO> facturaEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDetalleDTO facturaDetalle = facturaEncontrado.get();
            assertEquals(facturaDetalleEjemplo.getId(), facturaDetalle.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @AfterEach
    public void tearDown() {
        if (facturaDetalleEjemplo != null) {
            facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            facturaDetalleEjemplo = null;
        }
        

    }
}
