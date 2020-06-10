package metodosjavaClass;

public class SentenciasSQL {

    /*
    *Sentencias SQL para cargar datos en un comboBox
     */
    public static String sqlProvincia = "SELECT Id_provincia, provincia FROM provincias";
    public static String sqlMunicipios = "SELECT provincia, municipio FROM municipios where provincia = ";

    /*
    *Sentencias Tipo de Documento, tipo de articulo y documento Proveedor
     */
    public static String sqlDocumento = "SELECT id_tipo_documento, Descripcion from tipo_de_documento";
    public static String sqlDocumentoId = "SELECT id_tipo_documento, Descripcion from tipo_de_documento where id_tipo_documento =  ";
    public static String sqlTipoArticulo = "SELECT id_tipoarticulo, descripcion_articulo from tipo_articulo WHERE descripcion_articulo = ";
    public static String sqlNomComercioProveedor = "SELECT No_documento, Nombre_comercial from proveedor  WHERE  Nombre_comercial = ";
    public static String sqlCodigoBarrasID = "SELECT id_articulo from producto "
            + "where codigo_barras= ";

    /*
    * Sentencias en la tabla cliente
     */
    public static String ingresarCliente = "INSERT INTO cliente(Documento, cod_tipo_documento, Nombre, Apellidos, Telefono, email"
            + ", Pais, Ciudad, Localidad, Direccion, CodigoPostal, Empleado) VALUES ";
    public static String sqlModificarCliente = "UPDATE cliente SET ";
    public static String sqlEliminarCliente = "DELETE FROM cliente WHERE ";
    public static String sqlConsultarDocumentoCliente = "SELECT Documento, Nombre, Apellidos from cliente";
    public static String sqlClienteTabla = "Select id_Cliente, Documento,cod_tipo_documento, Nombre, Apellidos, Telefono, Pais, Ciudad, Localidad, Direccion, CodigoPostal, email, Empleado "
            + "from cliente ";

    /*
    *Sentencias de proveedores
     */
    public static String ingresarProveedor = "INSERT INTO proveedor (No_documento,cod_tipo_documento,Nombre,Apellido,Nombre_comercial,Telefono,"
            + "email,Pais,Ciudad,Localidad,direccion,Productos) VALUES ";
    public static String sqlModificarProveedor = "UPDATE proveedor SET ";
    public static String sqlEliminarProveedor = "DELETE FROM proveedor WHERE ";
    public static String sqlConsultarDocumentoProveedor = "SELECT No_documento from proveedor";
    public static String sqlConsultaProveedorTabla = "SELECT id_proveedor,No_documento, cod_tipo_documento, Nombre, Apellido, Nombre_comercial, Telefono,"
            + " email, Pais, Ciudad, Localidad, direccion, producto.descripcion from proveedor  inner join producto on proveedor.Productos = producto.id_articulo";
    public static String sqlProductoProveedor = "SELECT id_articulo, descripcion from producto";

    /*
    *Sentecnias para registrar Articulos
     */
    //tipo de Articulo
    public static String ingresarTipoArticulo = "INSERT INTO tipo_articulo ( descripcion_articulo, IVA ) VALUES ";
    public static String ingresarProducto = "INSERT INTO producto ( descripcion, codigo_barras ) VALUES ";
    public static String ingresarArticulo = "INSERT INTO articulo (nombre, precio_venta, precio_costo, stock, stock_minimo, stock_maximo, cod_tipo_articulo, cod_proveedor, fecha_ingreso, codigo_barras) VALUES ";
    public static String sqlConsultaArticulotabla = "SELECT articulo.id_articulo, articulo.Nombre, articulo.precio_venta, "
            + "articulo.precio_costo, articulo.stock, articulo.stock_minimo, articulo.stock_maximo, "
            + " tipo_articulo.descripcion_articulo, proveedor.Nombre_comercial, "
            + "articulo.fecha_ingreso, producto.codigo_barras "
            + " from articulo inner join proveedor  on articulo.cod_proveedor = proveedor.No_documento "
            + " inner join tipo_articulo  on articulo.cod_tipo_articulo = tipo_articulo.id_tipoarticulo "
            + " inner join producto on articulo.codigo_barras = producto.id_articulo ";
    public static String sqlTipArticulo = "SELECT * from tipo_articulo";
    public static String sqlProducto = "select producto.id_articulo, producto.descripcion, producto.codigo_barras from producto "
            + "inner join proveedor on producto.id_articulo=proveedor.Productos group by producto.id_articulo;";
    public static String sqlProveedorComb = "SELECT No_documento, Nombre_comercial from proveedor where Productos = ";
    public static String sqlModificarArticulo = "UPDATE articulo SET ";
    public static String sqlEliminarArticulo = "DELETE FROM articulo WHERE ";

    /*
    *Sentencias para registrar Empleados
     */
    public static String sqlPuesto = "SELECT * from puesto_empleado";
    public static String sqlIdPuesto = "SELECT Id_puesto, Descripcion from puesto_empleado where Descripcion = ";
    public static String ingresarEmpleado = "INSERT INTO empleado (codigoEmpleado, cod_tipodocumento, DNI_NIE, Nombre, Apellidos, Email, Puesto) VALUES ";
    public static String ingresarLogin = "INSERT INTO login (Id_empleado, Password) VALUES ";
    public static String sqlModificarEmpleado = "UPDATE empleado SET ";
    public static String sqlEliminarEmpleado = "DELETE FROM empleado WHERE ";
    public static String sqlConsultaCodigoEmpleado = "SELECT codigoEmpleado from empleado";
    public static String sqlConsultaEmpleadoTabla = "SELECT Id_empleado, CodigoEmpleado, cod_tipodocumento, DNI_NIE , Nombre, Apellidos, Email,Puesto, puesto_empleado.Descripcion "
            + "FROM empleado INNER JOIN puesto_empleado ON empleado.Puesto = puesto_empleado.Id_puesto ";

    /*
    *Sentencias de la interfaz Ventas y Pago
     */
    public static String sqlPago = "SELECT * from forma_de_pago";
    public static String sqlConsultarFactura = "SELECT cod_factura from detalle_factura ";
    public static String sqlRegCompra = "INSERT INTO detalle_factura (cod_factura, cod_articulo, cantidad, total) VALUES ";
    public static String insertarFactura = "INSERT INTO factura (Nnm_factura, cod_empleado, Fecha_facturacion, cod_formapago, total_factura) VALUES ";
    public static String sqlArticulos = "SELECT `id_articulo`, `nombre` FROM `articulo` order by `id_articulo`;";
    public static String sqlCantidad = "Select `id_articulo`, `stock` from articulo where id_articulo = ";
    public static String sqlEliminarDetalleFactura = "DELETE from detalle_factura WHERE ";
    public static String sqlIdArticulo = "SELECT id_articulo from articulo where Nombre = ";
    public static String sqlDocumentosClientes = "SELECT documento FROM cliente";
    public static String sqlDocumentoNombreCliente = "SELECT documento, Nombre from cliente where documento = ";
    public static String sqlConsultarActualizarDb = "Select id_articulo, stock from articulo where Nombre = ";
    public static String sqlConsultarStockArticulo = "Select stock from articulo where id_articulo = ";
    public static String sqlActualizarArticuloDb = "UPDATE articulo SET ";
    public static String obtenerPrecio = "select tipo_articulo.IVA,(articulo.precio_venta * (tipo_articulo.IVA / 100)) ,"
            + " ((articulo.precio_venta * (tipo_articulo.IVA / 100)) + articulo.precio_venta ) as precio_iva "
            + "from articulo inner join tipo_articulo on articulo.cod_tipo_articulo = tipo_articulo.id_tipoarticulo "
            + "where id_articulo = ";
    public static String sqlIvaPorcentaje = "select tipo_articulo.IVA,(articulo.precio_venta * (tipo_articulo.IVA / 100)), articulo.precio_venta "
            + "from articulo inner join tipo_articulo on articulo.cod_tipo_articulo = tipo_articulo.id_tipoarticulo "
            + "where id_articulo = ";

    /*
    *Sentencia de Registrar Devoluciones
     */
    public static String sqlDevolucionFactura = "SELECT Nnm_factura from factura";
    public static String sqlExisteCodeBar = "SELECT codigo_barras from  producto where codigo_barras = ";
    public static String ingresarDevolucion = "INSERT INTO devolucion (cod_detallefactura, cod_detallearticulo, Motivo, Fecha_devolucion, cantidad,"
            + " total_devolver, cod_empleado) VALUES ";
    public static String retirarDevolucion = "DELETE FROM detalle_factura WHERE cod_articulo = ";
    public static String verRegistroCodeBar = "SELECT  detalle_factura.cod_factura, articulo.nombre, detalle_factura.cantidad, ((articulo.precio_venta * (tipo_articulo.IVA / 100)) + articulo.precio_venta ) as precio_iva ,"
            + " producto.codigo_barras  FROM factura "
            + " INNER JOIN detalle_factura ON factura.Nnm_factura = detalle_factura.cod_factura "
            + " INNER JOIN articulo ON 	detalle_factura.cod_articulo = articulo.id_articulo "
            + " INNER JOIN tipo_articulo on articulo.cod_tipo_articulo = tipo_articulo.id_tipoarticulo "
            + " INNER JOIN producto on articulo.codigo_barras = producto.id_articulo "
            + " where producto.codigo_barras = ";

    /* Factura Sentencias*/
    public static String sqlConsulCliente = "SELECT Nombre, Apellidos, Documento from cliente where Documento = ";
    public static String sqlDistintasFactura = "Select distinct(factura.Nnm_factura), Fecha_facturacion "
            + "from factura where cod_cliente = ";
    public static String sqlFacturaImprimir = "SELECT  articulo.nombre, detalle_factura.cantidad, ((articulo.precio_venta * (tipo_articulo.IVA / 100)) + articulo.precio_venta ) as precio_iva , "
            + "detalle_factura.total, factura.total_factura"
            + " FROM factura "
            + " INNER JOIN detalle_factura ON factura.Nnm_factura = detalle_factura.cod_factura "
            + " INNER JOIN articulo ON 	detalle_factura.cod_articulo = articulo.id_articulo "
            + " INNER JOIN tipo_articulo on articulo.cod_tipo_articulo = tipo_articulo.id_tipoarticulo"
            + " where Nnm_factura = ";

    public static String sqlTicket = "Select Nnm_factura from factura where id_factura = (select max(id_factura) from factura)";

    /*Imprimir etiquetas */
    public static String sqlCodebar = "SELECT codigo_barras from producto";
    public static String sqlEtiquetaCodeBar = "select producto.codigo_barras, articulo.nombre, "
            + " ((articulo.precio_venta * (tipo_articulo.IVA / 100)) + articulo.precio_venta ) as precio_iva "
            + " from articulo inner join tipo_articulo on articulo.cod_tipo_articulo = tipo_articulo.id_tipoarticulo"
            + " inner join producto on articulo.codigo_barras = producto.id_articulo;";
}
