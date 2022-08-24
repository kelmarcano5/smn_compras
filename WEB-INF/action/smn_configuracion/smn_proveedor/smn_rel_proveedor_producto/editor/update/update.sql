UPDATE smn_compras.smn_rel_proveedor_producto SET
	smn_proveedor_id=${fld:smn_proveedor_rf},
	smn_item_rf=${fld:rpp_id_producto},
	smn_servicios_compras_id=${fld:smn_servicio_id},
	smn_afijos_rf=${fld:smn_afijos_rf},
	rpp_tipo_producto=${fld:rpp_tipo_producto},
	rpp_producto_alterno=${fld:rpp_producto_alterno},
	rpp_codigo_proveedor=${fld:rpp_codigo_proveedor},
	rpp_descripcion_proveedor=${fld:rpp_descripcion_proveedor},
	rpp_existencia_proveedor=${fld:rpp_existencia_proveedor},
	rpp_precio_ml=${fld:rpp_precio_ml},
	rpp_precio_ma=${fld:rpp_precio_ma},
	smn_moneda_id=${fld:smn_moneda_id},
	rpp_fecha_valides=${fld:rpp_fecha_valides},
	rpp_estatus=${fld:rpp_estatus},
	rpp_idioma='${def:locale}',
	rpp_usuario='${def:user}',
	rpp_fecha_registro={d '${def:date}'},
	rpp_hora='${def:time}'
WHERE
	smn_rel_proveedor_producto_id=${fld:id}

