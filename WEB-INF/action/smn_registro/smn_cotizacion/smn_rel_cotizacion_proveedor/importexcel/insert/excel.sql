INSERT INTO smn_compras.smn_rel_cotizacion_proveedor
(
	smn_rel_cotizacion_proveedor_id,
	smn_cotizacion_id,
	smn_proveedor_id
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_rel_cotizacion_proveedor},
	?,
	?
)
