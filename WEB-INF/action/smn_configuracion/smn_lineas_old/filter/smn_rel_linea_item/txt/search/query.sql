select
		smn_compras.smn_rel_linea_item.smn_rel_linea_item_id,
	smn_compras.smn_lineas.lin_codigo||'-'||smn_compras.smn_lineas.lin_nombre as smn_lineas_id, 
	smn_base.smn_item.itm_codigo||'-'||smn_base.smn_item.itm_nombre as smn_item_id
	
from
	smn_compras.smn_lineas,
	smn_base.smn_item,
	smn_compras.smn_rel_linea_item
where
	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_linea_item.smn_lineas_id

