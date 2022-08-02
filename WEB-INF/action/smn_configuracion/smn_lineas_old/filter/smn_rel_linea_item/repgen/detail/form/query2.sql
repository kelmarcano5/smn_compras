select
		smn_compras.smn_rel_linea_item.smn_lineas_id,
	smn_compras.smn_rel_linea_item.smn_item_id
from
	smn_compras.smn_rel_linea_item 
where
	smn_compras.smn_rel_linea_item.smn_rel_linea_item_id = ${fld:id}
