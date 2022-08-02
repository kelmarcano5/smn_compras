INSERT INTO smn_compras.smn_orden_compra_descuentos_retenciones
(
	smn_descuento_retencion_oc_id,
	smn_orden_compra_detalle_id,
	smn_codigo_descuento_rf,
	ocd_monto_base,
	ocd_porcentaje,
	ocd_monto_descuento,
	smn_moneda_rf,
	smn_tasa_rf,
	ocd_monto_base_ma,
	ocd_monto_descuento_ma,
	idioma,
	usuario,
	fecha_registro,
	hora
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_orden_compra_descuentos_retenciones},
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	'${def:locale
}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
