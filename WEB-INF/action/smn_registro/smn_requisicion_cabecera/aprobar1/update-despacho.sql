UPDATE 
	smn_inventario.smn_despacho
SET
	des_monto_pedido_ml = ${fld:des_monto_pedido_ml},
	des_monto_neto_ml   = ${fld:des_monto_neto_ml}
WHERE
	smn_despacho_id = ${fld:smn_despacho_id}
	
RETURNING smn_despacho_id;