SELECT
	smn_compras.smn_proveedor.smn_proveedor_id AS id,
	smn_base.smn_auxiliar.aux_codigo || ' - ' || smn_base.smn_auxiliar.aux_descripcion AS item
FROM
	smn_compras.smn_proveedor
INNER JOIN
	smn_base.smn_auxiliar ON smn_compras.smn_proveedor.smn_auxiliar_rf = smn_base.smn_auxiliar.smn_auxiliar_id