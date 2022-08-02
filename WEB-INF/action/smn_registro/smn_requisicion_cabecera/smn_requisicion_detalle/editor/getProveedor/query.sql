SELECT
	smn_compras.smn_proveedor.smn_proveedor_id AS id,
	smn_base.smn_auxiliar.aux_codigo||'-'||smn_base.smn_auxiliar.aux_descripcion AS item
FROM
	smn_compras.smn_servicio
	INNER JOIN
	smn_compras.smn_proveedor ON smn_compras.smn_servicio.sco_proveedor_exclusivo = smn_compras.smn_proveedor.smn_proveedor_id
	INNER JOIN
	smn_base.smn_auxiliar ON smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
WHERE
	smn_compras.smn_servicio.smn_servicio_id = ${fld:id}
