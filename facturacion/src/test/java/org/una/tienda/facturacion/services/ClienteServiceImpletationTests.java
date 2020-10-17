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

/**
 *
 * @author colo7
 */
@SpringBootTest
public class ClienteServiceImpletationTests {

    @Autowired
    private IClienteService clienteService;

    
    
    ClienteDTO clienteEjemplo;

    @BeforeEach
    public void setup() {
        clienteEjemplo = new ClienteDTO(){
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
    }
    

    @Test
    public void sePuedeCrearUnClienteCorrectamente() {

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
    public void sePuedeModificarUnProductoCorrectamente() {

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
    public void sePuedeEliminarUnProductoCorrectamente() {
        clienteEjemplo = clienteService.create(clienteEjemplo);
        clienteService.delete(clienteEjemplo.getId());
        Optional<ClienteDTO> productoEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (productoEncontrado != null) {
            fail("El objeto no ha sido eliminado de la BD");
        }else{
            clienteEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @AfterEach
    public void tearDown() {
        if (clienteEjemplo != null) {
            clienteService.delete(clienteEjemplo.getId());
            clienteEjemplo = null;
        }

    }

}
