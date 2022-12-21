UPDATE
	smn_compras.smn_orden_compra_detalle
SET
	ocd_monto_impuesto_ml   = ${fld:oci_monto_impuesto_ml},
	ocd_monto_desc_reten_ml = ${fld:ocd_monto_descuento_ml},
	ocd_monto_neto_ml       = ${fld:ocd_monto_neto_ml},
	ocd_monto_impuesto_ma   = ${fld:oci_monto_impuesto_ma},
	ocd_monto_desc_reten_ma = ${fld:ocd_monto_descuento_ma},
	ocd_monto_neto_ma	    = ${fld:ocd_monto_neto_ml}
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}

RETURNING smn_orden_compra_detalle_id;