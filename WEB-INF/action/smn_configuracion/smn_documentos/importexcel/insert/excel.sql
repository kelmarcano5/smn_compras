INSERT INTO smn_compras.smn_documentos
(
	smn_documentos_id,
	smn_tipo_documento_id,
	dcc_codigo,
	dcc_nombre,
	dcc_transaccion_id,
	dcc_recurrente,
	dcc_fecha_inicio,
	dcc_fecha_final,
	dcc_modalidad,
	dcc_escotizacion,
	dcc_cant_cotizaciones,
	dcc_esoferta,
	dcc_cant_ofertas,
	dcc_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_documentos},
	?,
	?,
	?,
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
