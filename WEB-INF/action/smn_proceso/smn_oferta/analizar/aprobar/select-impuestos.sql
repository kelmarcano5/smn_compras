SELECT
	SUM(oci_monto_impuesto_ml) AS monto_impuesto_ml,
	SUM(oci_monto_impuesto_ma) AS monto_impuesto_ma
FROM
	smn_compras.smn_orden_compra_impuesto
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}