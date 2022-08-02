select
	(select smn_base.smn_item.itm_codigo|| ' - ' || smn_base.smn_item.itm_nombre from  smn_base.smn_item where smn_base.smn_item.smn_item_id is not null  and smn_base.smn_item.smn_item_id=smn_compras.smn_lineas.smn_lineas_id) as smn_item_id,
	smn_compras.smn_rel_linea_item.*
from 
	smn_compras.smn_rel_linea_item,
	smn_compras.smn_lineas
where
	smn_compras.smn_rel_linea_item.smn_lineas_id=smn_compras.smn_lineas.smn_lineas_id and 
	smn_compras.smn_lineas.smn_lineas_id=${fld:id}
