UPDATE smn_compras.smn_oferta SET
	ofe_estatus = 'AN'
WHERE
	smn_cotizacion_id = ${fld:smn_cotizacion_id}
	AND
	smn_oferta_id != ${fld:smn_oferta_id}