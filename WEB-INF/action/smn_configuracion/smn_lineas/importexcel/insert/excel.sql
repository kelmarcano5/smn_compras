INSERT INTO smn_compras.smn_lineas
(
	smn_lineas_id,
	smn_tipo_linea_id,
	lin_codigo,
	lin_nombre,
	lin_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_lineas},
	?,
	?,
	?,
	'${def:date
}'
)
