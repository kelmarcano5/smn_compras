UPDATE smn_compras.smn_cotizacion_f_entrega SET
	smn_cotizacion_id=${fld:smn_cotizacion_id},
	cfe_consecutivo=${fld:cfe_consecutivo},
	cfe_cantidad=${fld:cfe_cantidad},
	cfe_fecha_entrega=${fld:cfe_fecha_entrega},
	cfe_status=${fld:cfe_status}

WHERE
	smn_cotizacion_f_entrega_id=${fld:id}

