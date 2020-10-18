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
import org.una.tienda.facturacion.exceptions.ClienteSinCorreoException;
import org.una.tienda.facturacion.exceptions.ClienteSinDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteSinTelefonoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionClienteConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionFacturaDetalleConEstadoInactivoException;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class ClienteServiceImpletationTests {

    @Autowired
    private IClienteService clienteService;

    ClienteDTO clienteEjemplo;

    ClienteDTO clientePrueba;
    ClienteDTO clientePrueba4;
    ClienteDTO clientePrueba3;
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
    }

    public void initDataSinTelefono() {
        clientePrueba = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("KevinAcuna");
            }
        };
    }
    public void initDataSinDireccion() {
        clientePrueba3 = new ClienteDTO() {
            {
                
                setEmail("colo7112012@gmail.com");
                setNombre("KevinAcuna");
                setTelefono("61358010");
            }
        };
    }
    public void initDataSinCorreo() {
        clientePrueba4 = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setTelefono("61358010");
                setNombre("KevinAcuna");
            }
        };
    }

    public void initData2() throws NoModificarInformacionClienteConEstadoInactivoException, ClienteSinDireccionException, ClienteSinDireccionException, ClienteSinTelefonoException, ClienteSinCorreoException {
        clientePrueba = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
        clientePrueba = clienteService.create(clientePrueba);
        clientePrueba.setEstado(false);
        clientePrueba = clienteService.update(clientePrueba, clientePrueba.getId()).get();

    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() throws ClienteSinDireccionException, ClienteSinTelefonoException, ClienteSinCorreoException {

        clienteEjemplo = clienteService.create(clienteEjemplo);

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (clienteEncontrado.isPresent()) {
            ClienteDTO cliente = clienteEncontrado.get();
            assertEquals(clienteEjemplo.getId(), cliente.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnClienteCorrectamente() throws ClienteSinDireccionException, NoModificarInformacionClienteConEstadoInactivoException, ClienteSinTelefonoException, ClienteSinCorreoException {

        clienteEjemplo = clienteService.create(clienteEjemplo);
        clienteEjemplo.setDireccion("cliente modificado");
        clienteService.update(clienteEjemplo, clienteEjemplo.getId());
        Optional<ClienteDTO> productoEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ClienteDTO cliente = productoEncontrado.get();
            Assertions.assertEquals(clienteEjemplo.getId(), cliente.getId());
            Assertions.assertEquals(clienteEjemplo.getDireccion(), cliente.getDireccion());
            Assertions.assertEquals(clienteEjemplo.getEmail(), cliente.getEmail());
            Assertions.assertEquals(clienteEjemplo.getNombre(), cliente.getNombre());
            Assertions.assertEquals(clienteEjemplo.getTelefono(), cliente.getTelefono());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnClienteCorrectamente() throws ClienteSinDireccionException, ClienteSinTelefonoException, ClienteSinCorreoException {
        clienteEjemplo = clienteService.create(clienteEjemplo);
        clienteService.delete(clienteEjemplo.getId());
        Optional<ClienteDTO> productoEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            fail("El objeto no ha sido eliminado de la BD");
        } else {
            clienteEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void seEvitaClienteSinTelefono() throws ClienteSinTelefonoException {
        initDataSinTelefono();
        assertThrows(ClienteSinTelefonoException.class,
                () -> {
                    clienteService.create(clientePrueba);
                }
        );
    }
    
    @Test
    public void seEvitaClienteSinDireccion() throws ClienteSinDireccionException {
        initDataSinDireccion();
        assertThrows(ClienteSinDireccionException.class,
                () -> {
                    clienteService.create(clientePrueba3);
                }
        );
    }
    
    
    
    @Test
    public void seEvitaClienteSinCorreo() throws ClienteSinCorreoException {
        initDataSinCorreo();
        assertThrows(ClienteSinCorreoException.class,
                () -> {
                    clienteService.create(clientePrueba4);
                }
        );
    }

    @Test
    public void seEvitaModificarUnClienteConEstadoInactivo() throws NoModificarInformacionClienteConEstadoInactivoException, ClienteSinDireccionException, ClienteSinTelefonoException, ClienteSinCorreoException {
        initData2();
        assertThrows(NoModificarInformacionClienteConEstadoInactivoException.class,
                () -> {
                    clienteService.update(clientePrueba, clientePrueba.getId());
                }
        );
    }

    @AfterEach
    public void tearDown() {
        if (clienteEjemplo != null) {
            if (clienteEjemplo.getId() != null) {
                clienteService.delete(clienteEjemplo.getId());
            }
            clienteEjemplo = null;
        }

    }

}
