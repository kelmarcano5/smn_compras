UPDATE smn_compras.smn_orden_compra_fecha_entrega SET
	smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id},
	ocf_consecutivo=${fld:ocf_consecutivo},
	ocf_cantidad=${fld:ocf_cantidad},
	ocf_fecha_entrega=${fld:ocf_fecha_entrega},
	ocf_estatus=${fld:ocf_estatus},
	ocf_idioma='${def:locale}',
	ocf_usuario='${def:user}',
	ocf_fecha_registro={d '${def:date}'},
	ocf_hora='${def:time}'

WHERE
	smn_orden_compra_fecha_entrega_id=${fld:id}

