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
import org.una.tienda.facturacion.exceptions.ClienteSinCorreoException;
import org.una.tienda.facturacion.exceptions.ClienteSinDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteSinTelefonoException;
import org.una.tienda.facturacion.exceptions.NoGuardarInformacionFacturaConClienteInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionClienteConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionEnFacturaConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionProductoExistenciaConEstadoInactivoException;

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
    FacturaDTO facturaPruebaClienteInactivo;

    ClienteDTO clienteEjemplo;
    ClienteDTO clientePrueba;
    ClienteDTO clientePruebaClienteInactivo;

    @Autowired
    private IClienteService clienteService;

    @BeforeEach
    public void setup() throws ClienteSinDireccionException, ClienteSinTelefonoException, ClienteSinCorreoException {
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

    public void initData() throws ClienteSinDireccionException, NoModificarInformacionProductoExistenciaConEstadoInactivoException, NoGuardarInformacionFacturaConClienteInactivoException, NoModificarInformacionFacturaDetalleConEstadoInactivoException, NoModificarInformacionEnFacturaConEstadoInactivoException, ClienteSinTelefonoException, ClienteSinCorreoException{
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
        facturaPrueba = facturaService.create(facturaPrueba);
        facturaPrueba.setEstado(false);
        facturaPrueba = facturaService.update(facturaPrueba, facturaPrueba.getId()).get();

    }
    
     public void initDataClienteInactivo() throws ClienteSinDireccionException, NoModificarInformacionFacturaDetalleConEstadoInactivoException, NoModificarInformacionClienteConEstadoInactivoException, ClienteSinTelefonoException, ClienteSinCorreoException{
        clientePruebaClienteInactivo = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
        clientePruebaClienteInactivo = clienteService.create(clientePruebaClienteInactivo);
        clientePruebaClienteInactivo.setEstado(false);
        clienteService.update(clientePruebaClienteInactivo,clientePruebaClienteInactivo.getId());
        
        facturaPruebaClienteInactivo = new FacturaDTO() {
            {
                {
                    setDescuentoGeneral(0);
                    setCaja(0);
                    setEstado(false);
                    setClienteId(clientePruebaClienteInactivo);
                }

            }
        };

    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamente() throws NoGuardarInformacionFacturaConClienteInactivoException {

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
    public void sePuedeModificarUnaFacturaCorrectamente() throws NoModificarInformacionFacturaDetalleConEstadoInactivoException, NoGuardarInformacionFacturaConClienteInactivoException, NoModificarInformacionEnFacturaConEstadoInactivoException {

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
    public void sePuedeEliminarUnaFacturaCorrectamente() throws NoGuardarInformacionFacturaConClienteInactivoException {
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

    @Test
    public void seEvitaModificarUnaFacturaConEstadoInactivo() throws NoModificarInformacionEnFacturaConEstadoInactivoException, ClienteSinDireccionException, NoGuardarInformacionFacturaConClienteInactivoException, NoModificarInformacionProductoExistenciaConEstadoInactivoException, NoModificarInformacionFacturaDetalleConEstadoInactivoException, ClienteSinTelefonoException, ClienteSinCorreoException {
        initData();
        assertThrows(NoModificarInformacionEnFacturaConEstadoInactivoException.class,
                () -> {
                    facturaService.update(facturaPrueba, facturaPrueba.getId());
                }
        );
    }
    @Test
    public void seEvitaGuardarInformacionFacturaConClienteInactivo() throws ClienteSinDireccionException, NoModificarInformacionClienteConEstadoInactivoException, NoModificarInformacionFacturaDetalleConEstadoInactivoException, ClienteSinTelefonoException, ClienteSinCorreoException {
        initDataClienteInactivo();
        assertThrows(NoGuardarInformacionFacturaConClienteInactivoException.class,
                () -> {
                    facturaService.create(facturaPruebaClienteInactivo);
                }
        );
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
