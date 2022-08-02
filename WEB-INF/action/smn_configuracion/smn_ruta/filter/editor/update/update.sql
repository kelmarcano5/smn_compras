UPDATE smn_compras.smn_ruta SET
	rut_codigo=${fld:rut_codigo},
	rut_nombre=${fld:rut_nombre},
	rut_cantidad_aprobaciones=${fld:rut_cantidad_aprobaciones},
	rut_idioma='${def:locale}',
	rut_usuario_id='${def:user}',
	rut_fecha_registro={d '${def:date}'},
	rut_hora='${def:time}'

WHERE
	smn_ruta_id=${fld:id}

