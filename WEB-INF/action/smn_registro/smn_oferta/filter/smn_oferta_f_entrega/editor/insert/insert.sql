INSERT INTO smn_compras.smn_oferta_f_entrega
(
	smn_oferta_f_entrega_id,
	smn_oferta_id,
	ofe_consecutivo,
	ofe_cantidad,
	ofe_fecha_entrega,
	ofe_status
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_oferta_f_entrega},
	${fld:smn_oferta_id},
	${fld:ofe_consecutivo},
	${fld:ofe_cantidad},
	${fld:ofe_fecha_entrega},
	${fld:ofe_status}
)
