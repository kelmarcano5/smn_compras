SELECT 
	smn_compras.smn_proveedor.smn_proveedor_id 
FROM 
	smn_compras.smn_proveedor 
	INNER JOIN
	smn_compras.smn_rel_proveedor_producto
	ON 
	smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf = smn_compras.smn_proveedor.smn_proveedor_id
WHERE
	smn_compras.smn_rel_proveedor_producto.rpp_id_producto = ${fld:rpp_id_producto}
	
	