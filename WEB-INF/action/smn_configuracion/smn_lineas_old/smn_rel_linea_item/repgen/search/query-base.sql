select
		smn_compras.smn_rel_linea_item.smn_rel_linea_item_id,
	${field}
from
	smn_compras.smn_rel_linea_item
where
		smn_compras.smn_rel_linea_item.smn_rel_linea_item_id is not null
	${filter}
	
	
