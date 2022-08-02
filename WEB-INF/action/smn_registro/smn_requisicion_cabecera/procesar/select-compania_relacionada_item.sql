SELECT 
	DISTINCT(smn_compras.smn_proveedor.prv_empresa_relacionada)
FROM 
	smn_compras.smn_proveedor
WHERE 
	smn_compras.smn_proveedor.smn_proveedor_id = ${fld:smn_proveedor_id}
AND
	smn_compras.smn_proveedor.prv_empresa_relacionada = 'N'