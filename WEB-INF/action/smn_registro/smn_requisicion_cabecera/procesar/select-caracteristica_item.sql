SELECT 
	smn_item_rf, cit_proveedor_exclusivo, cit_almacenado
FROM 
	smn_inventario.smn_caracteristica_item
INNER JOIN 
	smn_compras.smn_requisicion_detalle
ON 
	smn_inventario.smn_caracteristica_item.smn_item_rf = smn_compras.smn_requisicion_detalle.smn_item_id
WHERE 
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
AND 
	smn_inventario.smn_caracteristica_item.cit_estatus = 'AC'