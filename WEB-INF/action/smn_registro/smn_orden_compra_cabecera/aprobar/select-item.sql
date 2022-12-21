SELECT
	cit_estatus
FROM	
	smn_inventario.smn_caracteristica_item
WHERE
	smn_item_rf = ${fld:smn_item_id}
AND
	cit_estatus = 'AC'
	