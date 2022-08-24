UPDATE smn_compras.smn_orden_compra_fecha_entrega SET
	smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id},
	ofe_consecutivo=${fld:ofe_consecutivo},
	ofe_cantidad=${fld:ofe_cantidad},
	ofe_fecha_entrega=${fld:ofe_fecha_entrega},
	estatus=${fld:estatus},
	ofe_idioma='${def:locale
}',
	ofe_usuario='${def:user}',
	ofe_fecha_registro={d '${def:date}'},
	ofe_hora='${def:time}'

WHERE
	smn_orden_compra_fecha_entrega_id=${fld:id}

