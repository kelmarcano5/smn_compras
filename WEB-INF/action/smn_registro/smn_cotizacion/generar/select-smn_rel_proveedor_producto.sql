SELECT
	rpp_precio_ml,
	rpp_precio_ma
FROM
	smn_compras.smn_rel_proveedor_producto
WHERE
	smn_proveedor_id = ${fld:smn_proveedor_id}
	AND
	smn_item_rf = ${fld:smn_item_id}