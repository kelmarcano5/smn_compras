SELECT
	smn_codigo_impuesto_rf
FROM
	smn_inventario.smn_rel_item_cod_impuesto
INNER JOIN
	smn_inventario.smn_caracteristica_item
ON
	smn_inventario.smn_rel_item_cod_impuesto.smn_caracteristica_item_id = smn_inventario.smn_caracteristica_item.smn_caracteristica_item_id
WHERE
	smn_inventario.smn_caracteristica_item.smn_item_rf = ${fld:smn_item_compras_id}
