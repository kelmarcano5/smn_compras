UPDATE smn_compras.smn_rel_ruta_aprobacion SET
	smn_ruta_id=${fld:smn_ruta_id},
	rra_aprobacion=${fld:rra_aprobacion},
	smn_lineas_id=${fld:smn_lineas_id},
	smn_roles_id=${fld:smn_roles_id},
	smn_regla_id=${fld:smn_regla_id},
	rra_idioma='${def:locale}',
	rra_usuario_id='${def:user}',
	rra_fecha_registro={d '${def:date}'},
	rra_hora='${def:time}'

WHERE
	smn_ruta_aprobacion_id=${fld:id}

