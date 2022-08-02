INSERT INTO smn_compras.smn_rel_usuario_documento
(
	smn_rel_usuario_documento_id,
	smn_usuario_id,
	smn_documento_id
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_usuario_documento},
	${fld:smn_usuario_id},
	${fld:smn_documento_id}
)
