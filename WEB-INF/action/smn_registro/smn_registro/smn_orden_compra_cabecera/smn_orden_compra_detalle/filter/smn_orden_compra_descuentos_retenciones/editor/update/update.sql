UPDATE smn_compras.smn_orden_compra_descuentos_retenciones SET
	smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id},
	smn_codigo_descuento_rf=${fld:smn_codigo_descuento_rf},
	ocd_monto_base=${fld:ocd_monto_base},
	ocd_porcentaje=${fld:ocd_porcentaje},
	ocd_monto_descuento=${fld:ocd_monto_descuento},
	smn_moneda_rf=${fld:smn_moneda_rf},
	smn_tasa_rf=${fld:smn_tasa_rf},
	ocd_monto_base_ma=${fld:ocd_monto_base_ma},
	ocd_monto_descuento_ma=${fld:ocd_monto_descuento_ma},
	ocd_idioma='${def:locale}',
	ocd_usuario='${def:user}',
	ocd_fecha_registro={d '${def:date}'},
	ocd_hora='${def:time}'

WHERE
	smn_descuento_retencion_oc_id=${fld:id}

