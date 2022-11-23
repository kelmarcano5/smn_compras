UPDATE
	smn_compras.smn_orden_compra_cabecera
SET
	occ_monto_ml           = (SELECT SUM(ocd_monto_bruto_ml) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}),
	occ_monto_impuesto_ml  = (SELECT SUM(ocd_monto_impuesto_ml) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}),
	occ_monto_desc_rete_ml = (SELECT SUM(ocd_monto_desc_reten_ml) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}),
	occ_monto_neto_ml      = (SELECT SUM(ocd_monto_neto_ml) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}),
	occ_monto_ma           = (SELECT SUM(ocd_monto_bruto_ma) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}),
	occ_monto_impuesto_ma  = (SELECT SUM(ocd_monto_impuesto_ma) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}),
	occ_monto_neto_ma      = (SELECT SUM(ocd_monto_neto_ma) FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id})
WHERE
	smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}

RETURNING smn_orden_compra_cabecera_id;