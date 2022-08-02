INSERT INTO smn_compras.smn_ruta
(
	smn_ruta_id,
	rut_codigo,
	rut_nombre,
	rut_cantidad_aprobaciones,
	rut_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_ruta},
	?,
	?,
	?,
	{d '${def:date}'}
)
