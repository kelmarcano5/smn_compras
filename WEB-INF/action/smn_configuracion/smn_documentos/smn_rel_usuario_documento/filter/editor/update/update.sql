UPDATE smn_compras.smn_rel_usuario_documento SET
	smn_usuario_id=${fld:smn_usuario_id},
	smn_documento_id=${fld:smn_documento_id}

WHERE
	smn_rel_usuario_documento_id=${fld:id}

