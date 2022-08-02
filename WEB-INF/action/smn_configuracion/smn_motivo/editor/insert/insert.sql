INSERT INTO smn_compras.smn_motivo
(
	smn_motivos_id,
	mtv_tipo,
	mtv_codigo,
	mtv_nombre,
	mtv_idioma,
	mtv_usuario_id,
	mtv_fecha_registro,
	mtv_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_motivo},
	${fld:mtv_tipo},
	${fld:mtv_codigo},
	${fld:mtv_nombre},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
