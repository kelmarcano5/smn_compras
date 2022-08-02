UPDATE smn_compras.smn_rel_linea_servicio SET
	smn_lineas_id=${fld:smn_lineas_id},
	smn_servicio_id=${fld:smn_servicio_id}

WHERE
	smn_rel_linea_servicio_id=${fld:id}

