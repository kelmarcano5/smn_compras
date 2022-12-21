SELECT 
	DISTINCT(smn_compras.smn_proveedor.prv_empresa_relacionada),
	smn_compras.smn_proveedor.smn_proveedor_id
FROM 
	smn_compras.smn_rel_proveedor_producto 
INNER JOIN 
	smn_compras.smn_proveedor
ON 
	smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf  = smn_compras.smn_proveedor.smn_proveedor_id
WHERE 
	smn_compras.smn_rel_proveedor_producto.rpp_id_producto = ${fld:smn_afijo_id}
AND
	smn_compras.smn_proveedor.prv_empresa_relacionada = 'N'