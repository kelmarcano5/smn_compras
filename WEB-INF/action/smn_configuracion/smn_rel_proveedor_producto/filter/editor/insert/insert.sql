INSERT INTO smn_compras.smn_rel_proveedor_producto
(
	smn_rel_proveedor_producto_id,
	smn_proveedor_rf,
	rpp_id_producto,
	rpp_tipo_producto,
	rpp_producto_alterno,
	rpp_idioma,
	rpp_usuario,
	rpp_fecha_registro,
	rpp_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_proveedor_producto},
	${fld:smn_proveedor_rf},
	${fld:rpp_id_producto},
	${fld:rpp_tipo_producto},
	${fld:rpp_producto_alterno},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
