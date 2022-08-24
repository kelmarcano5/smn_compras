SELECT 
		smn_forma_pago_rf 
	 FROM 
		smn_compras.smn_proveedor
	 INNER JOIN
		 smn_compras.smn_rel_proveedor_producto
	 ON
		smn_compras.smn_proveedor.smn_proveedor_id =  smn_compras.smn_rel_proveedor_producto.smn_proveedor_id
	 WHERE
	 	smn_compras.smn_rel_proveedor_producto.smn_item_rf = 
	 	(SELECT
            smn_item_rf
         FROM 
         	smn_compras.smn_requisicion_detalle
         WHERE 
         	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
         AND
     		rpp_tipo_producto = smn_naturaleza_id
        )
        OR
        smn_compras.smn_rel_proveedor_producto.smn_servicios_compras_id = 
	 	(SELECT
            smn_servicio_id
         FROM 
         	smn_compras.smn_requisicion_detalle
         WHERE 
         	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
         AND
     		rpp_tipo_producto = smn_naturaleza_id
        )
        OR
        smn_compras.smn_rel_proveedor_producto.smn_afijos_rf = 
	 	(SELECT
            smn_afijo_id
         FROM 
         	smn_compras.smn_requisicion_detalle
         WHERE 
         	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
         AND
     		rpp_tipo_producto = smn_naturaleza_id
        )