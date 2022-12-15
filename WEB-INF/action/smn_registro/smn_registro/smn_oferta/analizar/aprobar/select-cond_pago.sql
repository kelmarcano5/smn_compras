SELECT
	smn_base.smn_auxiliar.aux_cond_pago_rf
FROM
	smn_compras.smn_proveedor INNER JOIN smn_base.smn_auxiliar 
ON
	smn_compras.smn_proveedor.smn_auxiliar_rf = smn_base.smn_auxiliar.smn_auxiliar_id
WHERE
	smn_proveedor_id = ${fld:smn_proveedor_id}