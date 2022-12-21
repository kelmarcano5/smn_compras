UPDATE smn_compras.smn_cotizacion SET
	smn_requisicion_detalle_id=${fld:smn_requisicion_detalle_id},
	cot_secuencia=${fld:cot_secuencia},
	smn_documento_id=${fld:smn_documento_id},
	cot_numero_documento=${fld:cot_numero_documento},
	--smn_proveedor_id=${fld:smn_proveedor_id},
	cot_fecha_envio=${fld:cot_fecha_envio},
	cot_fecha_requerido=${fld:cot_fecha_requerido},
	smn_item_id=${fld:smn_item_rf},
	cot_estatus=${fld:cot_estatus},
	cot_idioma='${def:locale}',
	cot_usuario='${def:user}',
	cot_fecha_registro={d '${def:date}'},
	cot_hora='${def:time}'

WHERE
	smn_cotizacion_id=${fld:id}

