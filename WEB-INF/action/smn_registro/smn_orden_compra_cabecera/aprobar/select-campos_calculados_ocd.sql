SELECT
	SUM(ocd_monto_bruto_ml) AS monto_bruto_ml,
	SUM(ocd_monto_impuesto_ml) AS monto_impuesto_ml,
	SUM(ocd_monto_desc_reten_ml) AS monto_desc_reten_ml,
	SUM(ocd_monto_bruto_ma) AS monto_bruto_ma,
	SUM(ocd_monto_impuesto_ma) AS monto_impuesto_ma,
	SUM(ocd_monto_desc_reten_ma) AS monto_desc_reten_ma
FROM
	smn_compras.smn_orden_compra_detalle
WHERE
	smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}
AND
	(smn_servicios_id != 0 OR smn_servicios_id != null)