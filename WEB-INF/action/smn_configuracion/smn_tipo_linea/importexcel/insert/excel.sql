INSERT INTO smn_compras.smn_tipo_linea
(
	smn_tipo_linea_id,
	tlc_codigo,
	tlc_nombre,
	tlc_naturaleza,
	tlc_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_tipo_linea},
	?,
	?,
	?,
	'${def:date
}'
)
