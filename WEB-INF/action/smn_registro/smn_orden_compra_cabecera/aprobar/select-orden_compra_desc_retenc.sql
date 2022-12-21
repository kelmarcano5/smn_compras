SELECT
	*
FROM
	smn_compras.smn_orden_compra_descuentos_retenciones
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}
	