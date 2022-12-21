select DISTINCT smn_base.smn_item.smn_item_id as id, smn_base.smn_item.itm_codigo|| ' - ' || smn_base.smn_item.itm_nombre as item from smn_base.smn_item
inner join smn_compras.smn_rel_linea_item on smn_compras.smn_rel_linea_item.smn_item_id = smn_base.smn_item.smn_item_id
where 
smn_compras.smn_rel_linea_item.smn_lineas_id=${fld:id}
