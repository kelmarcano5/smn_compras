INSERT INTO smn_compras.smn_req_detalle_dcto_retenc
(
	smn_req_detalle_dcto_retenc_id,
	smn_requisicion_detalle_id,
	smn_codigo_descuento_rf,
	drc_monto_base,
	drc_porcentaje,
	drc_monto_descuento,
	drc_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_req_detalle_dcto_retenc},
	?,
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
