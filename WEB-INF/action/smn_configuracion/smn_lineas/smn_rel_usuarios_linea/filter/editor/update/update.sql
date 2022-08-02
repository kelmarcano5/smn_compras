UPDATE smn_compras.smn_rel_usuarios_linea SET
	smn_usuarios_id=${fld:smn_usuarios_id},
	smn_lineas_id=${fld:smn_lineas_id}

WHERE
	smn_rel_usuario_linea_id=${fld:id}

