INSERT INTO smn_compras.smn_orden_compra_detalle
(
	smn_orden_compra_detalle_id,
	smn_orden_compra_cabecera_id,
	smn_linea_id,
	smn_servicios_id,
	smn_item_rf,
	smn_afijo_rf,
	smn_contrato_modulo_id,
	ocd_descripcion,
	ocd_cantidad_pedida,
	smn_unidad_medida_rf,
	ocd_monto_bruto_ml,
	ocd_monto_impuesto_ml,
	ocd_monto_desc_reten_ml,
	smn_moneda_rf,
	smn_tasa_rf,
	ocd_monto_bruto_ma,
	ocd_monto_impuesto_ma,
	ocd_monto_desc_reten_ma,
	ocd_costo_ml,
	ocd_monto_neto_ml,
	ocd_estatus,
	ocd_idioma,
	ocd_usuario,
	ocd_fecha_registro,
	ocd_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_orden_compra_detalle},
	${fld:smn_orden_compra_cabecera_id},
	${fld:smn_linea_id},
	${fld:smn_servicios_id},
	${fld:smn_item_rf},
	${fld:smn_afijo_rf},
	${fld:smn_contrato_modulo_id},
	${fld:ocd_descripcion},
	${fld:ocd_cantidad_pedida},
	${fld:smn_unidad_medida_rf},
	${fld:ocd_monto_bruto_ml},
	${fld:ocd_monto_impuesto_ml},
	${fld:ocd_monto_desc_reten_ml},
	${fld:smn_moneda_rf},
	${fld:smn_tasa_rf},
	${fld:ocd_monto_bruto_ma},
	${fld:ocd_monto_impuesto_ma},
	${fld:ocd_monto_desc_reten_ma},
	5000,
	5000,
	'GE',
	'${def:locale}',
	'${def:user}',
	'${def:date}',
	'${def:time}'
)