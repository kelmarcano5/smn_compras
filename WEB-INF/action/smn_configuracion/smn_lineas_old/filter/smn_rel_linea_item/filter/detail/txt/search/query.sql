select
	smn_compras.smn_lineas.smn_lineas_id,
	smn_compras.smn_lineas.lin_codigo as lin_codigo_pl0,
	smn_compras.smn_rel_linea_item.*
from
	smn_compras.smn_lineas,
	smn_compras.smn_rel_linea_item
where
	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_linea_item.smn_lineas_id
	and
	smn_rel_linea_item_id = ${fld:id}
