SELECT DISTINCT 
	smn_base.smn_item.smn_item_id AS id, 
	smn_base.smn_item.itm_codigo|| ' - ' || smn_base.smn_item.itm_nombre AS item 
FROM 
	smn_base.smn_item
INNER JOIN 
    smn_inventario.smn_caracteristica_item on smn_inventario.smn_caracteristica_item.smn_item_rf= smn_base.smn_item.smn_item_id
INNER JOIN 
    smn_compras.smn_rel_linea_item on smn_compras.smn_rel_linea_item.smn_item_id = smn_inventario.smn_caracteristica_item.smn_item_rf
where 
	smn_compras.smn_rel_linea_item.smn_lineas_id=${fld:id}