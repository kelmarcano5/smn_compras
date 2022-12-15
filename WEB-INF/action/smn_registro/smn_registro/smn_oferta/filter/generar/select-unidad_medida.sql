SELECT
	smn_unidad_medida_compra_rf
FROM
	smn_inventario.smn_caracteristica_item
WHERE
	smn_item_rf = ${fld:smn_item_compras_id}