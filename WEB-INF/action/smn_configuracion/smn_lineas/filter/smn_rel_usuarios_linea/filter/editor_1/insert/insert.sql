INSERT INTO smn_compras.smn_rel_usuarios_linea
(
	smn_rel_usuario_linea_id,
	smn_usuarios_id,
	smn_lineas_id
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_usuarios_linea},
	${fld:smn_usuarios_id},
	${fld:smn_lineas_id}
)
