UPDATE smn_compras.smn_requisicion_detalle SET
	smn_requisicion_cabecera_id=${fld:smn_requisicion_cabecera_id},
	smn_linea_id=${fld:smn_linea_id},
	smn_naturaleza_id=${fld:smn_naturaleza_id},
	smn_servicio_id=${fld:smn_servicio_id},
	smn_item_id=${fld:smn_item_id},
	smn_afijo_id=${fld:smn_afijo_id},
	rrs_producto_encontrado=${fld:rrs_producto_encontrado},
	smn_contrato_id=${fld:smn_contrato_id},
	rrs_motivo_variacion=${fld:rrs_motivo_variacion},
	rrs_porcentaje=${fld:rrs_porcentaje},
	rss_cantidad=${fld:rss_cantidad},
	rrs_precio=${fld:rrs_precio},
	rrs_monto=${fld:rrs_monto},
	smn_moneda_id=${fld:smn_moneda_id},
	rrs_precio_moneda_alterna=${fld:rrs_precio_moneda_alterna},
	rrs_monto_moneda_alterna=${fld:rrs_monto_moneda_alterna},
	smn_proveedor_id=${fld:smn_proveedor_id},
	rrs_especificaciones_del_requerimiento=${fld:rrs_especificaciones_del_requerimiento},
	rrs_fecha_de_requerido=${fld:rrs_fecha_de_requerido},
	rrs_observaciones=${fld:rrs_observaciones},
	rrs_idioma='${def:locale}',
	rrs_usuario='${def:user}',
	rrs_fecha_registro={d '${def:date}'},
	rrs_hora='${def:time}'

WHERE
	smn_requisicion_detalle_id=${fld:id}

