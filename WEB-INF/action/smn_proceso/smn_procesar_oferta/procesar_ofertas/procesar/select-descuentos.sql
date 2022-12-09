SELECT
	SUM(ocd_monto_descuento) AS monto_descuento_ml,
	SUM(ocd_monto_descuento_ma) AS monto_descuento_ma
FROM
	smn_compras.smn_orden_compra_descuentos_retenciones
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}