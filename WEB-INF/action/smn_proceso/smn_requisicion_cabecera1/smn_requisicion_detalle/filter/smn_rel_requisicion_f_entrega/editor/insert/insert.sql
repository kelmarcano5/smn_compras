INSERT INTO smn_compras.smn_rel_requisicion_f_entrega
(
	smn_rel_requisicion_f_entrega_id,
	smn_requisicion_detalle_id,
	cfe_consecutivo,
	cfe_cantidad,
	cfe_fecha_de_entrega,
	cfe_idioma,
	cfe_usuario,
	cfe_fecha_registro,
	cfe_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_requisicion_f_entrega},
	${fld:smn_requisicion_detalle_id},
	${fld:cfe_consecutivo},
	${fld:cfe_cantidad},
	${fld:cfe_fecha_de_entrega},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
