SELECT
	smn_compras.smn_orden_compra_detalle.*
FROM
	smn_compras.smn_orden_compra_detalle
WHERE	
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}

	