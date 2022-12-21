SELECT 
	smn_proveedor_rf 
FROM 
	smn_compras.smn_rel_proveedor_producto
WHERE
	rpp_id_producto = 
	(SELECT
     	smn_item_id
     FROM 
        smn_compras.smn_requisicion_detalle
     WHERE 
        smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
     AND
     	rpp_tipo_producto = smn_naturaleza_id
    )	
OR
    smn_servicio_id = 
	(SELECT
     	smn_servicio_id
     FROM 
        smn_compras.smn_requisicion_detalle
     WHERE 
        smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
     AND
     	rpp_tipo_producto = smn_naturaleza_id
    )