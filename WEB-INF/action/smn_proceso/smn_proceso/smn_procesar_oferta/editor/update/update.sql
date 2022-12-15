UPDATE smn_compras.smn_oferta SET
	smn_cotizacion_id=${fld:smn_cotizacion_id},
	smn_documento_id=${fld:smn_documento_id},
	ofe_numero_documento=${fld:ofe_numero_documento},
	smn_item_compras_id=${fld:smn_item_compras_id},
	smn_servicios_compras_id=${fld:smn_servicios_compras_id},
	smn_activo_fijo_compras_id=${fld:smn_activo_fijo_compras_id},
	smn_condicion_financiera_rf=${fld:smn_condicion_financiera_rf},
	ofe_producto_alterno=${fld:ofe_producto_alterno},
	ofe_item_alter_prov=${fld:ofe_item_alter_prov},
	ofe_activo_fijo_alter_prov=${fld:ofe_activo_fijo_alter_prov},
	ofe_cantidad=${fld:ofe_cantidad},
	ofe_precio_ml=${fld:ofe_precio_ml},
	ofe_monto_ml=${fld:ofe_monto_ml},
	ofe_moneda_id=${fld:ofe_moneda_id},
	ofe_tasa=${fld:ofe_tasa},
	ofe_precio_ma=${fld:ofe_precio_ma},
	ofe_monto_ma=${fld:ofe_monto_ma},
	ofe_aplica_anticipo=${fld:ofe_aplica_anticipo},
	ofe_porcentaje_anticipo=${fld:ofe_porcentaje_anticipo},
	ofe_monto_anticipo_ml=${fld:ofe_monto_anticipo},
	ofe_monto_anticipo_ma=${fld:ofe_monto_anticipo_ma},
	ofe_observaciones=${fld:ofe_observaciones},
	ofe_fecha_de_requerido=${fld:ofe_fecha_de_requerido},
	ofe_estatus=${fld:ofe_estatus},
	ofe_idioma='${def:locale}',
	ofe_usuario='${def:user}',
	ofe_fecha_registro='${def:date}',
	ofe_hora='${def:time}'

WHERE
	smn_oferta_id=${fld:id}

