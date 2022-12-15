SELECT 
	smn_compras.smn_proveedor.smn_proveedor_id 
FROM 
	smn_compras.smn_proveedor 
	INNER JOIN
	smn_compras.smn_rel_proveedor_producto
	ON 
	smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_proveedor.smn_proveedor_id
WHERE
	smn_compras.smn_rel_proveedor_producto.smn_item_rf = ${fld:smn_item_id}
	
	