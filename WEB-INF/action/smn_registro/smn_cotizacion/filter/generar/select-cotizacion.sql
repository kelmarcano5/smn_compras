SELECT 
	*
FROM
	smn_compras.smn_cotizacion
INNER JOIN
	smn_compras.smn_rel_cotizacion_proveedor
ON
	smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id
WHERE
	smn_compras.smn_cotizacion.smn_cotizacion_id = ${fld:smn_cotizacion_id}
AND
	smn_compras.smn_cotizacion.cot_estatus = 'RE'