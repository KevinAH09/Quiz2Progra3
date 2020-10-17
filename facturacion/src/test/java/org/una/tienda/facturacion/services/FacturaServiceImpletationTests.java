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
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class FacturaServiceImpletationTests {

    @Autowired
    private IFacturaService facturaService;

    FacturaDTO facturaEjemplo;

    ClienteDTO clienteEjemplo;

    @Autowired
    private IClienteService clienteService;

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
                setDescuentoGeneral(0);
                setCaja(0);
                setClienteId(clienteEjemplo);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamente() {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnaFacturaCorrectamente() {

        facturaEjemplo = facturaService.create(facturaEjemplo);
        facturaEjemplo.setCaja(39393);
        facturaService.update(facturaEjemplo, facturaEjemplo.getId());
        Optional<FacturaDTO> facturaDEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaDEncontrado.isPresent()) {
            FacturaDTO factura = facturaDEncontrado.get();
            Assertions.assertEquals(facturaEjemplo.getId(), factura.getId());
            Assertions.assertEquals(facturaEjemplo.getCaja(), factura.getCaja());
            Assertions.assertEquals(facturaEjemplo.getDescuentoGeneral(), factura.getDescuentoGeneral());
            Assertions.assertEquals(facturaEjemplo.getClienteId().getId(), factura.getClienteId().getId());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnaFacturaCorrectamente() {
        facturaEjemplo = facturaService.create(facturaEjemplo);
        facturaService.delete(facturaEjemplo.getId());
        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado != null) {
            fail("El objeto no ha sido eliminado de la BD");
        } else {
            facturaEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @AfterEach
    public void tearDown() {
        if (facturaEjemplo != null) {
            facturaService.delete(facturaEjemplo.getId());
            facturaEjemplo = null;
        }

    }
}
