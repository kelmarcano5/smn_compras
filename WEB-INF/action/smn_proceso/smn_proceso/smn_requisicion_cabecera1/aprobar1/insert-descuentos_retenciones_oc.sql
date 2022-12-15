INSERT INTO smn_compras.smn_orden_compra_descuentos_retenciones
(
	smn_descuento_retencion_oc_id,
	smn_orden_compra_detalle_id,
	smn_codigo_descuento_rf,
	ocd_monto_base,
	ocd_porcentaje,
	ocd_monto_descuento,
	ocd_idioma,
	ocd_usuario,
	ocd_fecha_registro,
	ocd_hora
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_descuentos_retenciones'),
	${fld:smn_orden_compra_detalle_id},
	${fld:smn_codigo_descuento_rf},
	${fld:drc_monto_base},
	${fld:drc_porcentaje},
	${fld:drc_monto_descuento},
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)
RETURNING smn_descuento_retencion_oc_id;