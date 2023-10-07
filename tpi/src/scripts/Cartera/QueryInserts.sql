use TpIntegradorDB;

/*Insert en tabla de posicion*/
select * from posicion_;
INSERT INTO `tpintegradordb`.`posicion_` 
		(`oid_`, `deleted_`, `version_`, `cantidad`, `es_efectivo`, `fecha_posicion`, `instrumento_oid`, `moneda_oid`, `precio`, `usuario_oid`) 
VALUES ('2', 		false, 		0, 			5000, 		true, 		'2023-09-30', 			null, 					1,		0, 			1);

INSERT INTO `tpintegradordb`.`posicion_` 
		(`oid_`, `deleted_`, `version_`, `cantidad`, `es_efectivo`, `fecha_posicion`, `instrumento_oid`, `moneda_oid`, `precio`, `usuario_oid`) 
VALUES ('3', 		false, 		0, 			110, 		false, 		'2023-09-30', 			2, 					1,		1000, 			1);

INSERT INTO `tpintegradordb`.`posicion_` 
		(`oid_`, `deleted_`, `version_`, `cantidad`, `es_efectivo`, `fecha_posicion`, `instrumento_oid`, `moneda_oid`, `precio`, `usuario_oid`) 
VALUES ('4', 		false, 		0, 			-1000, 		true, 		'2023-09-30', 			null, 					1,		0, 			1);


/*Insert en tabla de usuario*/
select * from usuario;
INSERT INTO `tpintegradordb`.`usuario` (`oid_`, `deleted_`, `version_`, `nombre_usuario`) 
VALUES ('1', false, 0, 'Nicolas');


/*Insert en tabla de instrumento*/
select * from instrumento;
INSERT INTO `tpintegradordb`.`instrumento` (`oid_`, `deleted_`, `version_`, `apertura`, `cantidad_operaciones`, `descripcion`, `fecha`, `flash_compra`, `flash_venta`, `lamina_minima`, `lote`, `maximo`, `mercado`, `minimo`, `moneda`, `plazo`, `precio_ejercicio`, `simbolo`, `tipo_opcion`, `ultimo_cierre`, `ultimo_precio`, `volumen`) 
VALUES ('2', false, 0, 231, 652, 'BYMA', '2023-09-15T17:00:05.423', 0, 0, 1, 1, 234.75, 1, 220.25, 1, 1, null, 'BYMA', null, 224.75, 224.75, 0);
INSERT INTO `tpintegradordb`.`instrumento` (`oid_`, `deleted_`, `version_`, `apertura`, `cantidad_operaciones`, `descripcion`, `fecha`, `flash_compra`, `flash_venta`, `lamina_minima`, `lote`, `maximo`, `mercado`, `minimo`, `moneda`, `plazo`, `precio_ejercicio`, `simbolo`, `tipo_opcion`, `ultimo_cierre`, `ultimo_precio`, `volumen`) 
VALUES ('3', false, 0, 900, 794, 'Telecom Argentina', '2023-09-15T17:00:05.423', 0, 0, 1, 1, 905, 1, 870.1, 1, 1, null, 'TECO2', null, 894.05, 894.05, 0);


/*update campos simbolos en posicion*/
UPDATE `tpintegradordb`.`posicion_` AS p
JOIN `tpintegradordb`.`instrumento` AS i ON p.instrumento_oid = i.oid_
SET p.simbolo_instrumento = i.simbolo;

/*Insert en tabla puntas*/
select * from puntas;
INSERT INTO `tpintegradordb`.`puntas` (`oid_`, `deleted_`, `version_`, `cantidad_compra`, `cantidad_venta`, `precio_compra`, `precio_venta`, `instrumento_id`) 
VALUES 									('2', 	false, 			0, 			500, 				20, 			200, 			150,			 2);
