CREATE DATABASE SISTEMAINVENTARIO1;

-- --------------------------------------------------------
USE SISTEMAINVENTARIO1;
--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `id_articulo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `precio_costo` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `cod_tipo_articulo` int(11) NOT NULL,
  `cod_proveedor` varchar(20) NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `codigo_barras` int NOT NULL,
  PRIMARY KEY (`id_articulo`),
  KEY `ref_tipo_articulo_idx` (`cod_tipo_articulo`,`codigo_barras`),
  KEY `ref_prov_art_idx` (`cod_proveedor`),
  KEY `ref_prov_art_cod` (`codigo_barras`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12;

--
-- Volcar la base de datos para la tabla `articulo`
--

INSERT INTO `articulo` (`id_articulo`,`nombre`,`precio_venta`,`precio_costo`,`stock`,`cod_tipo_articulo`,`cod_proveedor`,`fecha_ingreso`,`codigo_barras`) VALUES
(1, 'Bota Larga', 1000, 800, 20, 16, '00003-A', '2012-11-03',1),
(2, 'Bota corta', 700, 500, 55, 16, '00003-A', '2009-10-11',2),
(3, 'Botin ', 150, 130, 18, 16, '00003-A', '2012-09-01',3),
(4, 'Tenis ', 120, 100, 8, 16, '00002-2', '2010-10-02',4),
(5, 'Zandalias', 500, 300, 16, 16, '00001-1', '2012-11-01',5),
(6, 'Jeans', 900, 700, 18, 17, '00004-4', '2020-10-02',6),
(7, 'Camisas', 500, 300, 12, 17, '00003-A', '2020-10-20',7),
(8, 'Chaquetas', 700, 500, 8, 17, '00004-4', '2009-11-02',8),
(9, 'Chaquetas sudadera', 600, 400, 20, 17, '00002-2', '2011-10-23',9),
(10, 'Pantalon sudadera', 600, 400, 17, 17, '00002-2', '2011-10-23',10),
(11, 'Sudadera completa', 1000, 800, 20, 17, '00002-2', '2012-10-23',11),
(12, 'Camisetas', 300, 200, 30, 17, '00005-F', '2010-10-03',12); 

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE  `cliente` (
  `id_Cliente` int not null auto_increment,
  `Documento` varchar(15) NOT NULL,
  `cod_tipo_documento` int(11) NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Apellidos` varchar(30) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL,
  `Pais` varchar(40) NOT NULL,
  `Ciudad` varchar(40) NOT NULL,
  `Localidad` varchar(40) NOT NULL,
  `Direccion` varchar(40) not NULL,
  `CodigoPostal` int(6) NOT NULL,
  `Empleado` int DEFAULT NULL,
  PRIMARY KEY (`id_Cliente`, `Documento`),
  KEY `cli_cliente` (`Documento`),
  KEY `cod_tipodocumento_idx` (`cod_tipo_documento`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11;

--
-- Volcar la base de datos para la tabla `cliente`
--




INSERT INTO `cliente` (`id_Cliente`,`Documento`,`cod_tipo_documento`,`Nombre`,`Apellidos`,`Telefono`,`email`,`Pais`,`Ciudad`,`Localidad`,`Direccion`,`CodigoPostal`,`Empleado`) VALUES
(1,'125', 1, 'Carlos', 'Perez','3127654323', 'hola@holamundo.es','España','Álava', 'Aurrio', 'Carrera 23 N 34-34',28012 ,1),
(2,'000032', 1, 'dfdfff', 'ffff','77777','ffff@aaaaaa.es','España','Álava' ,'Añana', 'fffff',28014 ,1),
(3,'0001', 1, 'Camilo', 'Lopez','8239578','zzzzzz@bbbbb.es','España','Albacete','Gineta (La)','Calle 23 N 12-43',28012 ,1),
(4,'0002', 2, 'Maria', 'Arango','8253478', 'email@email.com', 'España','Álava','Murrio','Carrera 5 N 23-12',28094,1),
(5,'0003', 1, 'Andres', 'Florez','3127654323', 'ss@lenin.urss','España','Almería','Abrucena', 'Carrera 23 N 12A 34',28047, 1),
(6,'0004', 1, 'Carlos', 'Marin','8345623','acab@1312.com','España', 'Cáceres','Abertura',  'Calle 3 N 98_21',29092,1),
(7,'0005', 1, 'Elvira', 'Orozco','3219843543','julian@bastard.com','España','A Coruña','Ares', 'Centro', 28011, 1),
(8,'0006', 1, 'Kevin', 'Ayala','8354624','lololo@lalala.com','España','Álava','Murrio',  'Carrera 23 N 4B 23',28011,1),
(9,'0007', 1, 'Angela', 'Hoyos','3217654300','lalalal@lololo.com','España','Alicante','Agost', 'Calle 4 N 23 34',28024, 1),
(10,'0008', 2, 'Marcela', 'Gomez', '11548752','gmail@email.es','España','Badajoz','Aceuchal', 'Centro',28154, 1),
(11,'00000000',1,'Cliente','Default','000000','default@default.def','default','default','default','Default',00000,1);



----------------------------------------------------------



-- 
-- Estructura de tabla para la tabla `detalle_factura`
--

CREATE TABLE `detalle_factura` (
  `id_Dtfactura` int not null auto_increment,
  `cod_factura` varchar(20) NOT NULL,
  `cod_articulo` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `total` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id_Dtfactura`,`cod_factura`,`cod_articulo`),
  KEY `ref_facturacion_idx` (`cod_factura`),
  KEY `ref_ar_fact_idx` (`cod_articulo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3;

--
-- Volcar la base de datos para la tabla `detalle_factura`
--

INSERT INTO `detalle_factura` (`id_Dtfactura`,`cod_factura`, `cod_articulo`, `cantidad`, `total`) VALUES
(1,'9910966', 3, 1, '150000'),
(2,'9910966', 4, 2, '240000'),
(3,'9910966', 5, 1, '47500');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `devolucion`
--

CREATE TABLE `devolucion` (
  `id_devolucion` int not null auto_increment,
  `cod_detallefactura` varchar(20) NOT NULL,
  `cod_detallearticulo` int(11) NOT NULL,
  `Motivo` varchar(15) NOT NULL,
  `Fecha_devolucion` date NOT NULL,
  `cantidad` int(11) NOT NULL,
  `cod_empleado` int NOT NULL,
  PRIMARY KEY (`id_devolucion`,`cod_detallefactura`,`cod_detallearticulo`),
  KEY `ref_detallefactu_idx` (`cod_detallefactura`),
  KEY `ref_deta_art_idx` (`cod_detallearticulo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--
-- Volcar la base de datos para la tabla `devolucion`
--

INSERT INTO `devolucion` (`id_devolucion`, `cod_detallefactura`, `cod_detallearticulo`, `Motivo`, `Fecha_devolucion`, `cantidad`,`cod_empleado`) VALUES
(1,'9910966', 3, 'pequeño', '2013-01-14', 1,1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id_factura` int not null auto_increment,
  `Nnm_factura` varchar(20) NOT NULL,
  `cod_cliente` varchar(15) NOT NULL,
  `cod_empleado` int NOT NULL,
  `Fecha_facturacion` date NOT NULL,
  `cod_formapago` int(11) NOT NULL,
  `total_factura` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_factura`,`Nnm_factura`),
  KEY `ref_factura_Nom` (`Nnm_factura`),
  KEY `ref_cli_idx` (`cod_cliente`),
  KEY `ref_formapago_idx` (`cod_formapago`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--
-- Volcar la base de datos para la tabla `factura`
--

INSERT INTO `factura` (`id_factura`,`Nnm_factura`, `cod_cliente`, `cod_empleado`, `Fecha_facturacion`, `cod_formapago`, `total_factura`) VALUES
(1,'9910966', '125', 1, '2013-01-13', 1, '437500');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `forma_de_pago`
--

CREATE TABLE `forma_de_pago` (
  `id_formapago` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion_formapago` varchar(20) NOT NULL,
  PRIMARY KEY (`id_formapago`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4;

--
-- Volcar la base de datos para la tabla `forma_de_pago`
--

INSERT INTO `forma_de_pago` (`id_formapago`, `Descripcion_formapago`) VALUES
(1, 'EFECTIVO'),
(2, 'TARJETA DE CREDITO'),
(3, 'CHEQUE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id_proveedor` int not null auto_increment,
  `No_documento` varchar(20) NOT NULL,
  `cod_tipo_documento` int(11) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Apellido` varchar(20) DEFAULT NULL,
  `Nombre_comercial` varchar(20) NOT NULL,
  `Telefono` varchar(15) NOT NULL,
  `email` varchar(60) NOT NULL,
  `Pais` varchar(40) NOT NULL,
  `Ciudad` varchar(40) NOT NULL,
  `Localidad` varchar(40) NOT NULL,
  `direccion` varchar(40) NOT NULL,
  `Productos` int NOT NULL,
  PRIMARY KEY (`id_proveedor`,`No_documento`,`Productos`),
  key `proveedor_documento`(`No_documento`),
  KEY `proveedor_ibfk_1` (`cod_tipo_documento`),
  KEY `proveedor_ibfk_2` (`Productos`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8;

--
-- Volcar la base de datos para la tabla `proveedor`
--
INSERT INTO `proveedor` (`id_proveedor`,`No_documento`,`cod_tipo_documento`,`Nombre`,`Apellido`,`Nombre_comercial`,`Telefono`,`email`,`Pais`,`Ciudad`,`Localidad`,`direccion`,`Productos`) VALUES
(1,'00001-1', 3, 'Carlos', 'Maya', 'Estilos','11548752', 'mason@illuminati.es' , 'España', 'Melilla', 'Melilla', 'Calle 12-12', 1),
(2,'00002-2', 3, 'Marino', 'Burbano', 'Sports','3217654300','kkkkk@lanlan.kkk','España','Melilla' , 'Melilla', 'Centro',  2),
(3,'00003-A', 2, 'Paola', 'Sanchez', 'Fashion','7517454300','8888@hh.al','España','Melilla','Melilla', 'Veraneras',  3),
(4,'00004-4', 2, 'Camilo', 'Muñoz', 'Jeans&Jackets','3219843543','hh@adolf.reich','España', 'Melilla','Melilla','Centro', 4),
(5,'00005-F', 3, 'Marlon', 'Brandom', 'Bellas','8354624','gggg@tttt.es','España','Melilla','Melilla', 'Calle 34 B 12',  5),
(6,'00006-6', 3, 'Carlos', 'sanchez diaz', 'jeans&&jeans', '8213423','eeee@iiiii.es','España', 'Melilla','Melilla', 'calle 45 N 23-23', 1),
(7,'2222', 1, 'cccc', 'ddfff', 'ffff','6684756','uuuu@jjjj.es','España', 'Melilla','Melilla', 'calle dddd', 6),
(8,'43434', 1, 'fdsfdsfdsfds', 'fdsfsfds', 'fsdfdsfds','123458','rafa@nadal.ten','España','Melilla','Melilla','calle fsdfdsfds', 7),
(9,'00005-F', 3, 'Marlon', 'Brandom', 'H&M','8354624','gggg@tttt.es','España','Melilla','Melilla', 'Calle 34 B 12',  8),
(10,'00006-6', 3, 'Carlos', 'sanchez diaz', 'PUL&ANDBER', '8213423','eeee@iiiii.es','España', 'Melilla','Melilla', 'calle 45 N 23-23', 9),
(11,'2222', 1, 'cccc', 'ddfff', 'DRTRE','6684756','uuuu@jjjj.es','España', 'Melilla','Melilla', 'calle dddd', 10),
(12,'43434', 1, 'fdsfdsfdsfds', 'fdsfsfds', 'MANGO','123458','rafa@nadal.ten','España','Melilla','Melilla','calle fsdfdsfds', 11),
(10,'00006-6', 3, 'Carlos', 'sanchez diaz', 'PPPPPP', '8213423','eeee@iiiii.es','España', 'Melilla','Melilla', 'calle 45 N 23-23', 12),
(11,'2222', 1, 'cccc', 'ddfff', 'GGGGGGG','6684756','uuuu@jjjj.es','España', 'Melilla','Melilla', 'calle dddd', 13),
(12,'43434', 1, 'fdsfdsfdsfds', 'fdsfsfds', 'BRESKA','123458','rafa@nadal.ten','España','Melilla','Melilla','calle fsdfdsfds', 14);

-- --------------------------------------------------------

--
-- Articulos
--
create table producto(
id_articulo int auto_increment,
descripcion varchar(80) not null,
codigo_barras varchar(80) not null,
primary key(`id_articulo`)
);

INSERT INTO `producto`(`id_articulo`, `descripcion`,`codigo_barras`) VALUES
(1,'Pantalones','13254870'),
(2,'Zapatos','47922285'),
(3,'Sudadera','35987462'),
(4,'Chaqueta','35487495'),
(5,'Polos','99632582'),
(6,'Camisetas','65478912'),
(7,'Camisas','879451525'),
(8,'Zandalias','15864886'),
(9,'Chaqueta sudadera','35487495'),
(10,'Bota Larga','65478912'),
(11,'Bota Corta','879451525'),
(12,'Tenis','15864886'),
(13,'Jeans','65478912'),
(14,'Botin','879451525');

--
-- Estructura de tabla para la tabla `tipo_articulo`
--

CREATE TABLE `tipo_articulo` (
  `id_tipoarticulo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_articulo` varchar(80) NOT NULL,
  `IVA` int not null,
  PRIMARY KEY (`id_tipoarticulo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Volcar la base de datos para la tabla `tipo_articulo`
--

INSERT INTO `tipo_articulo` (`id_tipoarticulo`, `descripcion_articulo`,`IVA`) VALUES
(1, 'PAN, CEREALES Y HARINA',4),
(2, 'LIBOS,REVISTAS, PERIODICO Y CUADERNOS',4),
(3, 'FRUTERIA',4),
(4, 'VERDULERIA',4),
(5, 'LECHE, QUESO Y HUEVOS', 4),
(6, 'AGUA', 10),
(7, 'REFRESCOS Y APERITIVOS',10),
(8, 'CARNE', 10),
(9, 'PESCADO',10),
(10, 'CONSERVAS VEGETALES', 10),
(11, 'CONSERVAS ANIMAL',10),
(12, 'PRODUCTOS BASICOS, PAN INDUSTRIAL', 10),
(13, 'ACEITE',10),
(14, 'HIGIENE FEMENINO Y MASCULINO',10),
(15, 'BEBIDAS ALCOHOLICAS',21),
(16, 'ROPA',21),
(17, 'CALZADO',21),
(18, 'COSMÉTICOS',21),
(19, 'PAÑALES',21),
(20, 'PARAFARMACIA',21);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_de_documento`
--

CREATE TABLE `tipo_de_documento` (
  `id_tipo_documento` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(10) NOT NULL,
  PRIMARY KEY (`id_tipo_documento`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `tipo_de_documento`
--

INSERT INTO `tipo_de_documento` (`id_tipo_documento`, `Descripcion`) VALUES
(1, 'DNI'),
(2, 'NIE'),
(3, 'CIF'),
(4, 'PASAPORTE');

--
-- Estructura de tabla para la tabla Empleado
--
create table `empleado`(
Id_empleado int auto_increment not null,
CodigoEmpleado int not null unique,
cod_tipodocumento int not null,
DNI_NIE varchar(11) not null unique,
Nombre varchar(20) not null,
Apellidos varchar(20) not null,
Email varchar(30) ,
Puesto int not null, 
primary key(Id_empleado)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3;

--
-- Cargar datos Empleado
--
Insert into `empleado`(Id_empleado, codigoEmpleado, cod_tipodocumento, DNI_NIE, Nombre, Apellidos, Email, Puesto)values
(1, 1, 1, '9999999A', 'Juan Carlos', 'Toledo', 'hola@mundo.com', 1),
(2, 2, 2, 'D8888888B',  'Angel Bruno', 'Lopez', 'hola@mundo.es', 2);
--
-- Estructura de tabla para la tabla Login
--
create table `login`(
`Id_empleado` int(5) NOT NULL, 
`Password` varchar(256) NOT NULL,
PRIMARY KEY(Id_empleado),
foreign key(Id_empleado) references Empleado(CodigoEmpleado) on update cascade on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 
--
-- Cargar datos en login
--
INSERT INTO `login` (`Id_empleado`, `Password`) VALUES
(1, 'admin'),
(2, 'user');
--
-- Estructura de tabla para la tabla puestoEmpleado
--
create table `puesto_empleado`(
Id_puesto int not null auto_increment,
Descripcion varchar(230) not null,
primary key(Id_puesto)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Cargar Datos puesto Empleado
--
INSERT INTO `puesto_empleado` (`Id_puesto`, `Descripcion`) VALUES
(1, 'Administrador'),
(2, 'Usuario'),
(3, 'ControlerAdmin');

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD CONSTRAINT `ref_prov_art` FOREIGN KEY (`cod_proveedor`) REFERENCES `proveedor` (`No_documento`),
  ADD CONSTRAINT `ref_tipo_articulo` FOREIGN KEY (`cod_tipo_articulo`) REFERENCES `tipo_articulo` (`id_tipoarticulo`) ON UPDATE CASCADE,
   ADD CONSTRAINT `ref_producto` FOREIGN KEY (`codigo_barras`) REFERENCES `producto` (`id_articulo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `ref_empleado` FOREIGN KEY (`Empleado`) REFERENCES `empleado` (`CodigoEmpleado`),
  ADD CONSTRAINT `ref_tipo_doc` FOREIGN KEY (`cod_tipo_documento`) REFERENCES `tipo_de_documento` (`id_tipo_documento`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `detalle_factura`
--
ALTER TABLE `detalle_factura`
  ADD CONSTRAINT `ref_ar_fact` FOREIGN KEY (`cod_articulo`) REFERENCES `articulo` (`id_articulo`);

--
-- Filtros para la tabla `devolucion`
--
ALTER TABLE `devolucion`
  ADD CONSTRAINT `ref_detallefactu` FOREIGN KEY (`cod_detallefactura`) REFERENCES `detalle_factura` (`cod_factura`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ref_devuempleado` FOREIGN KEY (`cod_empleado`) REFERENCES `empleado` (`CodigoEmpleado`),
  ADD CONSTRAINT `ref_deta_art` FOREIGN KEY (`cod_detallearticulo`) REFERENCES `detalle_factura` (`cod_articulo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `ref_factucliente` FOREIGN KEY (`cod_cliente`) REFERENCES `cliente` (`Documento`),
  ADD CONSTRAINT `ref_factuempleado` FOREIGN KEY (`cod_empleado`) REFERENCES `empleado` (`CodigoEmpleado`),
  ADD CONSTRAINT `ref_formapago` FOREIGN KEY (`cod_formapago`) REFERENCES `forma_de_pago` (`id_formapago`),
  ADD CONSTRAINT `ref_facturacion` FOREIGN KEY (`Nnm_factura`) REFERENCES `detalle_factura` (`cod_factura`);

--
-- Filtros para la tabla `proveedor`
--

ALTER TABLE `proveedor`
  ADD CONSTRAINT `ref_tipodocumento` FOREIGN KEY (`cod_tipo_documento`) REFERENCES `tipo_de_documento` (`id_tipo_documento`),
  ADD CONSTRAINT `ref_productos` FOREIGN KEY (`Productos`) REFERENCES `producto` (`id_articulo`);
--
-- Filtros para la tabla `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `ref_login` FOREIGN KEY (`Id_Empleado`) REFERENCES `Empleado` (`CodigoEmpleado`) ON DELETE CASCADE ON UPDATE CASCADE;
  
--
-- Filtros para la tabla `login`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `ref_puesto` FOREIGN KEY (`puesto`) REFERENCES `puesto_empleado` (`Id_puesto`),
  ADD CONSTRAINT `ref_tipoDocEmple`FOREIGN KEY (`cod_tipodocumento`) REFERENCES `tipo_de_documento` (`id_tipo_documento`) ON DELETE CASCADE ON UPDATE CASCADE;