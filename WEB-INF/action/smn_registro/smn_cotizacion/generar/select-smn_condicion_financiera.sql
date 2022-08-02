SELECT
	smn_condicion_financiera_rf
FROM 
	smn_compras.smn_proveedor
WHERE
	smn_compras.smn_proveedor.smn_proveedor_id = ${fld:smn_proveedor_id}