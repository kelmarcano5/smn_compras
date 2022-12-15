UPDATE smn_compras.smn_oferta_f_entrega SET
	smn_oferta_id=${fld:smn_oferta_id},
	ofe_consecutivo=${fld:ofe_consecutivo},
	ofe_cantidad=${fld:ofe_cantidad},
	ofe_fecha_entrega=${fld:ofe_fecha_entrega},
	ofe_status=${fld:ofe_status}

WHERE
	smn_oferta_f_entrega_id=${fld:id}

