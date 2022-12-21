INSERT INTO smn_compras.smn_rel_requisicion_f_entrega
(
	smn_rel_requisicion_f_entrega_id,
	smn_requisicion_detalle_id,
	cfe_consecutivo,
	cfe_cantidad,
	cfe_fecha_de_entrega,
	cfe_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_rel_requisicion_f_entrega},
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
