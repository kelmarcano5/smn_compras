SELECT
	*
FROM
	smn_compras.smn_orden_compra_cabecera
WHERE
	smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}
AND
	occ_estatus = 'RE'
