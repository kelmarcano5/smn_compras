UPDATE smn_compras.smn_oferta SET
	ofe_estatus='RZ',
	ofe_motivo_rechazo=${fld:ofe_motivo_rechazo},
	ofe_idioma='${def:locale}',
	ofe_usuario='${def:user}',
	ofe_fecha_registro='${def:date}',
	ofe_hora='${def:time}'
WHERE
	smn_cotizacion_id=(SELECT smn_cotizacion_id FROM smn_compras.smn_oferta WHERE smn_oferta_id = ${fld:id})

