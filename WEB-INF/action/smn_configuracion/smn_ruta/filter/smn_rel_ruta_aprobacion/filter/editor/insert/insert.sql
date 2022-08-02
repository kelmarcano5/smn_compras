INSERT INTO smn_compras.smn_rel_ruta_aprobacion
(
	smn_ruta_aprobacion_id,
	smn_ruta_id,
	rra_aprobacion,
	smn_lineas_id,
	smn_roles_id,
	smn_regla_id,
	rra_idioma,
	rra_usuario_id,
	rra_fecha_registro,
	rra_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_ruta_aprobacion},
	${fld:smn_ruta_id},
	${fld:rra_aprobacion},
	${fld:smn_lineas_id},
	${fld:smn_roles_id},
	${fld:smn_regla_id},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
