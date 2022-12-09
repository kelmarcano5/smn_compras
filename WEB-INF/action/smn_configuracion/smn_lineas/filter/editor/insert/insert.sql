INSERT INTO smn_compras.smn_lineas
(
	smn_lineas_id,
	smn_tipo_linea_id,
	lin_codigo,
	lin_nombre,
	lin_idioma,
	lin_usuario_id,
	lin_fecha_registro,
	lin_hora,
	smn_almacen_consumo_rf
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_lineas},
	${fld:smn_tipo_linea_id},
	${fld:lin_codigo},
	${fld:lin_nombre},
	'${def:locale}',
	'${def:user}',
	'${def:date}',
	'${def:time}',
	${fld:smn_almacen_consumo_rf}
)
