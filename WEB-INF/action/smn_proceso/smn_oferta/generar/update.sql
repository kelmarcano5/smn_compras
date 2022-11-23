UPDATE smn_compras.smn_oferta SET
	ofe_estatus=${fld:ofe_estatus},
	ofe_idioma='${def:locale}',
	ofe_usuario='${def:user}',
	ofe_fecha_registro='${def:date}',
	ofe_hora='${def:time}'
WHERE
	smn_oferta_id=${fld:id}

