/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/**
 *
 * @author colo7
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TipoCambioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void seCalculaCorrectamenteTipoDeCambioADolares() {
        String valorEsperado = "10";
        String valorEnColones = "6100";
        String resultadoApi = this.restTemplate.getForObject("http://localhost:" + port + "/tipo-cambio/a-dolares/" + valorEnColones, String.class);
        System.out.println(resultadoApi);
        Assertions.assertEquals(resultadoApi, valorEsperado, "El tipo de cambio no se calcula correctamente");
    }

}
