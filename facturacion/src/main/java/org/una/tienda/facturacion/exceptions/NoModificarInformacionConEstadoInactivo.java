/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.exceptions;

/**
 *
 * @author cfugu
 */
public class NoModificarInformacionConEstadoInactivo extends Exception {

    private String exception;
    
    public NoModificarInformacionConEstadoInactivo(String exception)
    {
        this.exception=exception;
    }

    @Override
    public String getMessage() {
       return this.exception;    
    }

   

}