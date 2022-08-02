UPDATE smn_compras.smn_rel_linea_item SET
	smn_lineas_id=${fld:smn_lineas_id},
	smn_item_id=${fld:smn_item_id}

WHERE
	smn_rel_linea_item_id=${fld:id}

