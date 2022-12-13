UPDATE smn_compras.smn_req_detalle_dcto_retenc SET
	smn_requisicion_detalle_id=${fld:smn_requisicion_detalle_id},
	smn_codigo_descuento_rf=${fld:smn_codigo_descuento_rf},
	drc_monto_base=${fld:drc_monto_base},
	drc_porcentaje=${fld:drc_porcentaje},
	drc_monto_descuento=${fld:drc_monto_descuento},
	drc_idioma='${def:locale}',
	drc_usuario='${def:user}',
	drc_fecha_registro={d '${def:date}'},
	drc_hora='${def:time}'

WHERE
	smn_req_detalle_dcto_retenc_id=${fld:id}

