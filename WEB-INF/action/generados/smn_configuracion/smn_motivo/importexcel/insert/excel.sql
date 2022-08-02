INSERT INTO smn_compras.smn_motivo
(
	smn_motivos_id,
	mtv_tipo,
	mtv_codigo,
	mtv_nombre
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_motivo},
	?,
	?,
	?
)
