INSERT INTO smn_compras.smn_rel_ruta_aprobacion
(
	smn_ruta_aprobacion_id,
	smn_ruta_id,
	rra_aprobacion,
	smn_lineas_id,
	smn_roles_id,
	smn_regla_id,
	rra_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_rel_ruta_aprobacion},
	?,
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
