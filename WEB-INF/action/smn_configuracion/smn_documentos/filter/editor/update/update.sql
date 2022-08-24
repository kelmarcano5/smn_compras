UPDATE smn_compras.smn_documentos SET
	smn_tipo_documento_id=${fld:smn_tipo_documento_id},
	dcc_codigo=${fld:dcc_codigo},
	dcc_nombre=${fld:dcc_nombre},
	dcc_transaccion_id=${fld:dcc_transaccion_id},
	dcc_recurrente=${fld:dcc_recurrente},
	dcc_fecha_inicio=${fld:dcc_fecha_inicio},
	dcc_fecha_final=${fld:dcc_fecha_final},
	dcc_modalidad=${fld:dcc_modalidad},
	dcc_escotizacion=${fld:dcc_escotizacion},
	dcc_moneda_alterna=${fld:dcc_moneda_alterna},
	dcc_cant_cotizaciones=${fld:dcc_cant_cotizaciones},
	dcc_esoferta=${fld:dcc_esoferta},
	dcc_cant_ofertas=${fld:dcc_cant_ofertas},
	dcc_idioma='${def:locale}',
	dcc_usuario_id='${def:user}',
	dcc_fecha_registro='${def:date}',
	dcc_hora='${def:time}',
	dcc_genera_pedido=${fld:dcc_genera_pedido},
	dcc_cant_dias_cierre=${fld:dcc_cant_dias_cierre},
	dcc_hora_cierre=${fld:dcc_hora_cierre}
WHERE
	smn_documentos_id=${fld:id}

