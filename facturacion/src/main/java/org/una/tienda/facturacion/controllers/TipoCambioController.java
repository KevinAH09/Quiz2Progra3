/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author colo7
 */
@RestController
@RequestMapping("/tipo-cambio")
public class TipoCambioController {
    
    
    
    @GetMapping("/a-dolares/{colones}")
    public String findByCedula(@PathVariable(value = "colones") String colones) {
        try {
            System.out.println(colones);
            double colon = Double.valueOf(colones);
            double dolar = 610;
            
            return String.valueOf(colon/dolar);
        } catch (Exception e) {
            return e.getMessage();
        }

    }
    
}
