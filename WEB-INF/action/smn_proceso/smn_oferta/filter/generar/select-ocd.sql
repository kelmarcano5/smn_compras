SELECT
	ocd_monto_bruto_ml,
	smn_moneda_rf,
	smn_tasa_rf
FROM
	smn_compras.smn_orden_compra_detalle
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}