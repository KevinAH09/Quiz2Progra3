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
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoCorreoDireccionException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionConEstadoInactivo;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class FacturaServiceImpletationTests {

    @Autowired
    private IFacturaService facturaService;

    FacturaDTO facturaEjemplo;
    FacturaDTO facturaPrueba;

    ClienteDTO clienteEjemplo;
    ClienteDTO clientePrueba;

    @Autowired
    private IClienteService clienteService;

    @BeforeEach
    public void setup() throws ClienteConTelefonoCorreoDireccionException {
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

    public void initData() {
        clientePrueba = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
        clientePrueba = clienteService.create(clientePrueba);
        facturaPrueba = new FacturaDTO() {
            {
                {
                    setDescuentoGeneral(0);
                    setCaja(0);
                    setEstado(false);
                    setClienteId(clientePrueba);
                }

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
    public void sePuedeModificarUnaFacturaCorrectamente() throws NoModificarInformacionConEstadoInactivo {

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
    public void seEvitaModificarUnaFacturaConEstadoInactivo() {
        initData();
        assertThrows(NoModificarInformacionConEstadoInactivo.class,
                () -> {
                    facturaService.create(facturaPrueba);
                }
        );
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
            if (facturaEjemplo.getId() != null) {
                facturaService.delete(facturaEjemplo.getId());
            }
            facturaEjemplo = null;
        }

    }
}
