SELECT 
	des_monto_impuesto_ml,
	des_monto_descuento_ml
FROM
	smn_inventario.smn_despacho
WHERE
	smn_despacho_id = ${fld:smn_despacho_id}