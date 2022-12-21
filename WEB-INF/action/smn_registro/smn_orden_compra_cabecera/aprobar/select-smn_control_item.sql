SELECT 
* 
FROM 
	smn_inventario.smn_control_item 
WHERE
	smn_item_id = ${fld:smn_item_id} 
	AND
	smn_almacen_id = ${fld:smn_almacen_rf}
ORDER BY coi_fecha_movimiento DESC
LIMIT 1