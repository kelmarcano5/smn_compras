SELECT 
	smn_orden_compra_cabecera_id
FROM
	smn_compras.smn_orden_compra_cabecera
WHERE
	smn_proveedor_id = ${fld:smn_proveedor_id}
AND
	smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}