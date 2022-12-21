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
	${seq:nextval@smn_compras.seq_smn_oferta_f_entrega},
	?,
	?,
	?,
	?,
	?
)
