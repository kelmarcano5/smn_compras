UPDATE smn_compras.smn_rel_linea_afijo SET
	smn_lineas_id=${fld:smn_lineas_id},
	smn_afijo_id=${fld:smn_afijo_id}

WHERE
	smn_rel_linea_afijo_id=${fld:id}

