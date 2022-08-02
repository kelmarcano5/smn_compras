UPDATE smn_compras.smn_rel_proveedor_producto SET
	smn_proveedor_rf=${fld:smn_proveedor_rf},
	rpp_id_producto=${fld:rpp_id_producto},
	rpp_tipo_producto=${fld:rpp_tipo_producto},
	rpp_producto_alterno=${fld:rpp_producto_alterno},
	rpp_idioma='${def:locale}',
	rpp_usuario='${def:user}',
	rpp_fecha_registro={d '${def:date}'},
	rpp_hora='${def:time}'

WHERE
	smn_rel_proveedor_producto_id=${fld:id}

