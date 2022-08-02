INSERT INTO smn_compras.smn_rel_proveedor_producto
(
	smn_rel_proveedor_producto_id,
	smn_proveedor_rf,
	rpp_id_producto,
	rpp_tipo_producto,
	rpp_producto_alterno,
	rpp_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_rel_proveedor_producto},
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
