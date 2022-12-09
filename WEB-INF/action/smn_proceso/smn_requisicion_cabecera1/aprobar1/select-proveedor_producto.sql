SELECT 
	smn_compras.smn_proveedor.smn_proveedor_id,
	smn_compras.smn_proveedor.smn_condicion_financiera_rf,
	smn_compras.smn_proveedor.prv_anticipo,
	smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno,
	smn_compras.smn_rel_proveedor_producto.rpp_precio_ml,
	smn_compras.smn_rel_proveedor_producto.rpp_precio_ma
FROM 
	smn_compras.smn_proveedor 
	INNER JOIN
	smn_compras.smn_rel_proveedor_producto
	ON 
	smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_proveedor.smn_proveedor_id
WHERE
	smn_compras.smn_rel_proveedor_producto.smn_item_rf = ${fld:smn_item_id}
AND
	rpp_fecha_valides<=current_date
	
	