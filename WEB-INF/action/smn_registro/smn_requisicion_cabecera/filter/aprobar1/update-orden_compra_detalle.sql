UPDATE 
	smn_compras.smn_orden_compra_detalle
SET
	ocd_monto_neto_ml = ${fld:ocd_monto_neto_ml},
	ocd_monto_neto_ma = ${fld:ocd_monto_neto_ma}
WHERE
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}
	
RETURNING smn_orden_compra_detalle_id;