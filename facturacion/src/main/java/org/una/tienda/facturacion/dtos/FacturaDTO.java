/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.dtos;

import java.util.Date;

/**
 *
 * @author colo7
 */
public class FacturaDTO {
 
    private Long id;
    
    private int caja;
 
    private double descuentoGeneral;

    private boolean estado;
    
   
    private ClienteDTO clienteId;
    
   
    private Date fechaRegistro;

    
    private Date fechaModificacion;
}
