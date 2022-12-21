INSERT INTO smn_compras.smn_req_detalle_dcto_retenc
(
	smn_req_detalle_dcto_retenc_id,
	smn_requisicion_detalle_id,
	smn_codigo_descuento_rf,
	drc_monto_base,
	drc_porcentaje,
	drc_monto_descuento,
	drc_idioma,
	drc_usuario,
	drc_fecha_registro,
	drc_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_req_detalle_dcto_retenc},
	${fld:smn_requisicion_detalle_id},
	${fld:smn_codigo_descuento_rf},
	${fld:drc_monto_base},
	${fld:drc_porcentaje},
	${fld:drc_monto_descuento},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
