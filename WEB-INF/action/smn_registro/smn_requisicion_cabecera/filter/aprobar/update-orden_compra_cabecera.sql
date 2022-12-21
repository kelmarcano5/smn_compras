UPDATE 
	smn_compras.smn_orden_compra_cabecera
SET
	 occ_monto_ml = ${fld:ocd_monto_bruto_ml}, 
	 occ_monto_impuesto_ml = ${fld:ocd_monto_impuesto_ml},
	 occ_monto_desc_rete_ml = ${fld:ocd_monto_desc_reten_ml},  
	 occ_monto_neto_ml = ${fld:ocd_monto_neto_ml},  
	 smn_moneda_rf = ${fld:smn_moneda_rf}, 
	 smn_tasa_rf = ${fld:smn_tasa_rf}, 
	 occ_monto_ma = ${fld:ocd_monto_bruto_ma}, 
	 occ_monto_impuesto_ma = ${fld:ocd_monto_impuesto_ma}, 
	 occ_saldo_oc_ml = ${fld:ocd_monto_neto_ml},
	 occ_saldo_oc_ma = ${fld:ocd_monto_neto_ma}
WHERE
	smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}
	
RETURNING smn_orden_compra_cabecera_id;
	