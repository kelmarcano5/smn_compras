UPDATE
	smn_compras.smn_cotizacion
SET
	cot_estatus = 'GE'
WHERE
	smn_cotizacion_id = ${fld:smn_cotizacion_id}
RETURNING smn_cotizacion_id;