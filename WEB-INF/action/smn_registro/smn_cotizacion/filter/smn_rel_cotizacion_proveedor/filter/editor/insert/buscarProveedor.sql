SELECT
	smn_proveedor_id
FROM
	smn_compras.smn_rel_cotizacion_proveedor
WHERE
	smn_proveedor_id = ${fld:smn_proveedor_id}
AND
	smn_cotizacion_id = ${fld:smn_cotizacion_id}