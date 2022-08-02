INSERT INTO smn_compras.smn_proveedor
(
	smn_proveedor_id,
	smn_auxiliar_rf,
	smn_clasificacion_abc_rf,
	prv_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_proveedor},
	?,
	?,
	{d '${def:date}'}
)
