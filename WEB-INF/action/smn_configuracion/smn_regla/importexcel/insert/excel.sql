INSERT INTO smn_compras.smn_regla
(
	smn_regla_id,
	rul_codigo,
	rul_nombre,
	rul_cantidad_desde,
	rul_cantidad_hasta,
	rul_var_cantidad,
	smn_monedas_id,
	rul_monto_desde,
	rul_monto_hasta,
	rul_var_monto,
	rul_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_regla},
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	'${def:date
}'
)