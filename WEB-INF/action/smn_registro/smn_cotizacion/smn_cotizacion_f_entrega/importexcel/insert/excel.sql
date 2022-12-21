INSERT INTO smn_compras.smn_cotizacion_f_entrega
(
	smn_cotizacion_f_entrega_id,
	smn_cotizacion_id,
	cfe_consecutivo,
	cfe_cantidad,
	cfe_fecha_entrega,
	cfe_status
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_cotizacion_f_entrega},
	?,
	?,
	?,
	?,
	?
)
