SELECT 
	smn_base.smn_auxiliar.aux_codigo ||' - '|| smn_base.smn_auxiliar.aux_descripcion AS proveedor_combo
FROM
	smn_compras.smn_proveedor
	INNER JOIN
	smn_base.smn_auxiliar ON smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
WHERE
	smn_compras.smn_proveedor.smn_proveedor_id = ${fld:id2}