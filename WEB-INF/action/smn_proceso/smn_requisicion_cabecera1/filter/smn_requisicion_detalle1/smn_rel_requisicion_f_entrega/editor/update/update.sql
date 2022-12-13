UPDATE smn_compras.smn_rel_requisicion_f_entrega SET
	smn_requisicion_detalle_id=${fld:smn_requisicion_detalle_id},
	cfe_consecutivo=${fld:cfe_consecutivo},
	cfe_cantidad=${fld:cfe_cantidad},
	cfe_fecha_de_entrega=${fld:cfe_fecha_de_entrega},
	cfe_idioma='${def:locale}',
	cfe_usuario='${def:user}',
	cfe_fecha_registro={d '${def:date}'},
	cfe_hora='${def:time}'

WHERE
	smn_rel_requisicion_f_entrega_id=${fld:id}

