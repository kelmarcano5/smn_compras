SELECT
	ocd_monto_bruto_ml,
	ocd_monto_impuesto_ml,
	ocd_monto_desc_reten_ml,
	ocd_monto_bruto_ma,
	ocd_monto_impuesto_ma,
	ocd_monto_desc_reten_ma
FROM
	smn_compras.smn_orden_compra_detalle
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}