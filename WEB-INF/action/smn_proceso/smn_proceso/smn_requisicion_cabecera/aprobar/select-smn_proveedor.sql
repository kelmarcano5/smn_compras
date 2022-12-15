SELECT 
	smn_proveedor_rf 
FROM 
	smn_compras.smn_rel_proveedor_producto
WHERE
	rpp_id_producto = 
	(SELECT
     	CASE
	  		WHEN smn_naturaleza_id = 'IT' THEN smn_item_id
	        WHEN smn_naturaleza_id = 'SE' THEN smn_servicio_id
	        ELSE smn_afijo_id
        END AS producto
     FROM 
        smn_compras.smn_requisicion_detalle
     WHERE 
        smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
     AND
     	rpp_tipo_producto = smn_naturaleza_id
    )	