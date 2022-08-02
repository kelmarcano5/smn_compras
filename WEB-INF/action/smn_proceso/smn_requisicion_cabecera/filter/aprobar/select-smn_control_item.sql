SELECT
	control_item.*
FROM
	smn_inventario.smn_control_item AS control_item
INNER JOIN
	smn_base.smn_almacen AS almacen
	ON
	control_item.smn_almacen_id = almacen.smn_almacen_id
WHERE
	almacen.alm_empresa = ${fld:smn_entidad_id}
	AND
	control_item.smn_item_id = ${fld:smn_item_id}
	
ORDER BY control_item.coi_fecha_movimiento DESC LIMIT 1