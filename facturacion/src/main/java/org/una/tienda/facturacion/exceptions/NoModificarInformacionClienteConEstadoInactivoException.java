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
public class NoModificarInformacionClienteConEstadoInactivoException extends Exception {

    private String exception;
    
    public NoModificarInformacionClienteConEstadoInactivoException(String exception)
    {
        this.exception=exception;
    }

    @Override
    public String getMessage() {
       return this.exception;    
    }

   

}
