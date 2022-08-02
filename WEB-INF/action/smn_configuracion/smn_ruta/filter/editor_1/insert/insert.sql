INSERT INTO smn_compras.smn_ruta
(
	smn_ruta_id,
	rut_codigo,
	rut_nombre,
	rut_cantidad_aprobaciones,
	rut_idioma,
	rut_usuario_id,
	rut_fecha_registro,
	rut_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_ruta},
	${fld:rut_codigo},
	${fld:rut_nombre},
	${fld:rut_cantidad_aprobaciones},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
