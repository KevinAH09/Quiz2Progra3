/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author colo7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoPrecioDTO {

    private Long id;

    private double descuentoMaximo;
    
    private double descuentoPromocional;

    private double precioColones;

    private boolean estado;
    
    private ProductoDTO productosId;

    private Date fechaRegistro;

    private Date fechaModificacion;
}
