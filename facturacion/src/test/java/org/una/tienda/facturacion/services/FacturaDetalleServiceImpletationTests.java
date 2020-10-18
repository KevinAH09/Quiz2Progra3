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
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoCorreoDireccionException;
import org.una.tienda.facturacion.exceptions.NoCrearFacturaConProductoPrecioCeroException;
import org.una.tienda.facturacion.exceptions.NoCrearFacturasConCantidadCeroException;
import org.una.tienda.facturacion.exceptions.NoGuardarInformacionFacturaConClienteInactivoException;
import org.una.tienda.facturacion.exceptions.NoModificarInformacionConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

/**
 *
 * @author colo7
 */
@SpringBootTest
public class FacturaDetalleServiceImpletationTests {

    @Autowired
    private IFacturaDetalleService facturaDetalleService;

    @Autowired
    private IProductoPrecioService productoPrecioService;

    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProductoService productoService;

    FacturaDetalleDTO facturaDetalleEjemplo;

    FacturaDTO facturaEjemplo;

    ClienteDTO clienteEjemplo;

    ProductoDTO productoEjemplo;

    ProductoPrecioDTO productoPrecioEjemplo;

    ProductoExistenciaDTO productoExistenciaEjemplo;

    FacturaDetalleDTO facturaDetallePrueba;

    FacturaDTO facturaPrueba;

    ClienteDTO clientePrueba;

    ProductoDTO productoPrueba;

    ProductoPrecioDTO productoPrecioPrueba;

    ProductoExistenciaDTO productoExistenciaPrueba;

    FacturaDetalleDTO facturaDetallePrecioCero;

    FacturaDTO facturaPrecioCero;

    ClienteDTO clientePrecioCero;

    ProductoDTO productoPrecioCero;

    ProductoPrecioDTO productoPrecioPrecioCero;

    ProductoExistenciaDTO productoExistenciaPrecioCero;
    
    FacturaDetalleDTO facturaDetalleCantidadCero;

    FacturaDTO facturaCantidadCero;

    ClienteDTO clienteCantidadCero;

    ProductoDTO productoCantidadCero;

    ProductoPrecioDTO productoPrecioCantidadCero;

    ProductoExistenciaDTO productoExistenciaCantidadCero;

    @BeforeEach
    public void setup() throws ClienteConTelefonoCorreoDireccionException, NoGuardarInformacionFacturaConClienteInactivoException {

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
                setCaja(21);
                setDescuentoGeneral(2);
                setClienteId(clienteEjemplo);

            }
        };
        facturaEjemplo = facturaService.create(facturaEjemplo);

        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoEjemplo = productoService.create(productoEjemplo);
        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaEjemplo);
                setProductoId(productoEjemplo);
            }
        };
        productoExistenciaEjemplo = new ProductoExistenciaDTO() {
            {
                setProductosId(productoEjemplo);
                setCantidad(1);
            }
        };
        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);
        productoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setProductosId(productoEjemplo);
                setPrecioColones(1000);
                setDescuentoMaximo(20);
                setDescuentoPromocional(2);
            }
        };
        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

    }

    public void initData() throws NoModificarInformacionConEstadoInactivoException, ClienteConTelefonoCorreoDireccionException, NoGuardarInformacionFacturaConClienteInactivoException {
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
                setCaja(21);
                setDescuentoGeneral(2);
                setClienteId(clientePrueba);

            }
        };

        facturaPrueba = facturaService.create(facturaPrueba);
        facturaPrueba.setEstado(false);
        facturaPrueba = facturaService.update(facturaPrueba, facturaPrueba.getId()).get();

        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoPrueba = productoService.create(productoPrueba);
        facturaDetallePrueba = new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaPrueba);
                setProductoId(productoPrueba);
            }
        };
        facturaDetallePrueba = new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaPrueba);
                setProductoId(productoPrueba);
            }
        };
        productoExistenciaPrueba = new ProductoExistenciaDTO() {
            {
                setProductosId(productoPrueba);
                setCantidad(1);
            }
        };
        productoExistenciaPrueba = productoExistenciaService.create(productoExistenciaPrueba);
        productoPrecioPrueba = new ProductoPrecioDTO() {
            {
                setProductosId(productoPrueba);
                setPrecioColones(1000);
                setDescuentoMaximo(5);
                setDescuentoPromocional(2);
            }
        };
        productoPrecioPrueba = productoPrecioService.create(productoPrecioPrueba);
    }
    public void initDataPrcioCero() throws ClienteConTelefonoCorreoDireccionException, NoModificarInformacionConEstadoInactivoException, NoGuardarInformacionFacturaConClienteInactivoException{
         clientePrecioCero = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
        clientePrecioCero = clienteService.create(clientePrecioCero);
        facturaPrecioCero = new FacturaDTO() {
            {
                setCaja(21);
                setDescuentoGeneral(2);
                setClienteId(clientePrecioCero);

            }
        };

        facturaPrecioCero = facturaService.create(facturaPrecioCero);
        facturaPrecioCero.setEstado(false);
        facturaPrecioCero = facturaService.update(facturaPrecioCero, facturaPrecioCero.getId()).get();

        productoPrecioCero = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoPrecioCero = productoService.create(productoPrecioCero);
        facturaDetallePrecioCero= new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaPrecioCero);
                setProductoId(productoPrecioCero);
            }
        };
        facturaDetallePrecioCero = new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaPrecioCero);
                setProductoId(productoPrecioCero);
            }
        };
        productoExistenciaPrecioCero = new ProductoExistenciaDTO() {
            {
                setProductosId(productoPrecioCero);
                setCantidad(1);
            }
        };
        productoExistenciaPrecioCero = productoExistenciaService.create(productoExistenciaPrecioCero);
        productoPrecioPrecioCero = new ProductoPrecioDTO() {
            {
                setProductosId(productoPrecioCero);
                setPrecioColones(0);
                setDescuentoMaximo(15);
                setDescuentoPromocional(2);
            }
        };
        productoPrecioPrecioCero = productoPrecioService.create(productoPrecioPrecioCero);
    }
    
    public void initDataCantidadCero() throws ClienteConTelefonoCorreoDireccionException, NoModificarInformacionConEstadoInactivoException, NoGuardarInformacionFacturaConClienteInactivoException{
         clienteCantidadCero = new ClienteDTO() {
            {
                setDireccion("San Antonio");
                setEmail("colo7112012@gmail.com");
                setNombre("Kevin");
                setTelefono("61358010");
            }
        };
        clienteCantidadCero = clienteService.create(clienteCantidadCero);
        facturaCantidadCero = new FacturaDTO() {
            {
                setCaja(21);
                setDescuentoGeneral(2);
                setClienteId(clienteCantidadCero);

            }
        };

        facturaCantidadCero = facturaService.create(facturaCantidadCero);
        facturaCantidadCero.setEstado(false);
        facturaCantidadCero = facturaService.update(facturaCantidadCero, facturaCantidadCero.getId()).get();

        productoCantidadCero = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoCantidadCero = productoService.create(productoCantidadCero);
        facturaDetalleCantidadCero= new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaCantidadCero);
                setProductoId(productoCantidadCero);
            }
        };
        facturaDetalleCantidadCero = new FacturaDetalleDTO() {
            {
                setCantidad(200);
                setDescuentoFinal(10);
                setFacturaId(facturaCantidadCero);
                setProductoId(productoCantidadCero);
            }
        };
        productoExistenciaCantidadCero = new ProductoExistenciaDTO() {
            {
                setProductosId(productoCantidadCero);
                setCantidad(0);
            }
        };
        productoExistenciaCantidadCero = productoExistenciaService.create(productoExistenciaCantidadCero);
        productoPrecioCantidadCero = new ProductoPrecioDTO() {
            {
                setProductosId(productoCantidadCero);
                setPrecioColones(1000);
                setDescuentoMaximo(15);
                setDescuentoPromocional(2);
            }
        };
        productoPrecioCantidadCero = productoPrecioService.create(productoPrecioCantidadCero);
    }
    

    @Test
    public void sePuedeCrearUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, NoCrearFacturaConProductoPrecioCeroException, NoCrearFacturasConCantidadCeroException {

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        Optional<FacturaDetalleDTO> facturaEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDetalleDTO facturaDetalle = facturaEncontrado.get();
            assertEquals(facturaDetalleEjemplo.getId(), facturaDetalle.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, NoModificarInformacionConEstadoInactivoException, NoCrearFacturaConProductoPrecioCeroException, NoCrearFacturasConCantidadCeroException {

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);
        facturaDetalleEjemplo.setCantidad(39393);
        facturaDetalleService.update(facturaDetalleEjemplo, facturaDetalleEjemplo.getId());
        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent()) {
            FacturaDetalleDTO facturaDetalle = facturaDetalleEncontrado.get();
            Assertions.assertEquals(facturaDetalleEjemplo.getId(), facturaDetalle.getId());
            Assertions.assertEquals(facturaDetalleEjemplo.getCantidad(), facturaDetalle.getCantidad());
            Assertions.assertEquals(facturaDetalleEjemplo.getDescuentoFinal(), facturaDetalle.getDescuentoFinal());
            Assertions.assertEquals(facturaDetalleEjemplo.getProductoId().getId(), facturaDetalle.getProductoId().getId());
            Assertions.assertEquals(facturaDetalleEjemplo.getFacturaId().getId(), facturaDetalle.getFacturaId().getId());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, NoCrearFacturaConProductoPrecioCeroException, NoCrearFacturasConCantidadCeroException {
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);
        facturaDetalleService.delete(facturaDetalleEjemplo.getId());
        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent()) {
            fail("El objeto no ha sido eliminado de la BD");
        } else {
            facturaDetalleEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() throws NoModificarInformacionConEstadoInactivoException, ClienteConTelefonoCorreoDireccionException, NoGuardarInformacionFacturaConClienteInactivoException {
        initData();
        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
                () -> {
                    facturaDetalleService.create(facturaDetallePrueba);
                }
        );
    }

    @Test
    public void seEvitaModificarUnaFacturaDetalleConEstadoInactivo() throws NoModificarInformacionConEstadoInactivoException, ClienteConTelefonoCorreoDireccionException, NoGuardarInformacionFacturaConClienteInactivoException {
        initData();
        assertThrows(NoModificarInformacionConEstadoInactivoException.class,
                () -> {
                    facturaService.update(facturaPrueba, facturaPrueba.getId());
                }
        );
    }
    
    @Test
    public void seEvitaModificarUnaFacturaDetalleConPrecioMayorACero() throws NoModificarInformacionConEstadoInactivoException, ClienteConTelefonoCorreoDireccionException, NoGuardarInformacionFacturaConClienteInactivoException{
        initDataPrcioCero();
        assertThrows(NoCrearFacturaConProductoPrecioCeroException.class,
                () -> {
                    facturaDetalleService.create(facturaDetallePrecioCero);
                }
        );
    }

    @Test
    public void NoCrearFacturasConCantidadCero() throws NoModificarInformacionConEstadoInactivoException, ClienteConTelefonoCorreoDireccionException, NoGuardarInformacionFacturaConClienteInactivoException{
        initDataCantidadCero();
        assertThrows(NoCrearFacturasConCantidadCeroException.class,
                () -> {
                    facturaDetalleService.create(facturaDetalleCantidadCero);
                }
        );
    }
    
    @AfterEach
    public void tearDown() {
        if (facturaDetalleEjemplo != null) {
            if (facturaDetalleEjemplo.getId() != null) {
                facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            }
            facturaDetalleEjemplo = null;
        }

    }

}

