INSERT INTO smn_inventario.smn_movimiento_detalle
(
	smn_movimiento_detalle_id,
	smn_movimiento_cabecera_id,
	smn_item_rf,
	smn_centro_costo_rf,
	mde_es_bonificacion,
	mde_cantidad_recibida,
	mde_cantidad_solicitada,
	mde_cantidad_por_recibir,
	mde_lote,
	mde_fecha_vencimiento_lote,
	mde_tipo_movimiento,
	mde_descripcion,
	smn_unidad_medida_rf,
	mde_valor_unitario_ml,
	smn_tasa_rf,
	smn_moneda_rf,
	mde_valor_unitario_ma,
	mde_monto_bruto_ml,
	mde_monto_bruto_ma,
	mde_monto_disminucion_ml,
	mde_monto_disminucion_ma,
	mde_monto_valor_agregado_ml,
	mde_monto_valor_agregado_ma,
	mde_monto_neto_ml,
	mde_monto_neto_ma,
	mde_estatus,
	mde_idioma,
	mde_usuario,
	mde_fecha_registro,
  	mde_hora,
  	mde_estatus_existencia
)
VALUES
(
	nextval('smn_inventario.seq_smn_movimiento_detalle'),--smn_movimiento_detalle_id
	${fld:smn_movimiento_cabecera_id}, --smn_movimiento_cabecera_id
	${fld:smn_item_id}, --smn_item_rf
	'0',--smn_centro_costo_rf
	'N', --mde_es_bonificacion
	0, --mde_cantidad_recibida
	${fld:ocd_cantidad_recibida}, --mde_cantidad_solicitada
	0, --mde_cantidad_por_recibir
	null, --mde_lote
	null, --mde_fecha_vencimiento_lote
	'EN', --mde_tipo_movimiento
	${fld:ocd_descripcion}, --mde_descripcion
	${fld:smn_unidad_medida_rf}, --smn_unidad_medida_rf
	${fld:ocd_costo_ml}, --mde_valor_unitario_ml
	${fld:smn_tasa_rf}, --smn_tasa_rf
	${fld:smn_moneda_rf}, --smn_moneda_rf
	${fld:ocd_costo_ma}, --mde_valor_unitario_ma
	${fld:ocd_monto_bruto_ml}, --mde_monto_documento_ml
	${fld:ocd_monto_bruto_ma}, --mde_monto_documento_ma
	${fld:ocd_monto_desc_reten_ml}, --mde_monto_disminucion_ml
	${fld:ocd_monto_desc_reten_ma}, --mde_monto_disminucion_ma
	${fld:ocd_monto_impuesto_ml}, --mde_monto_valor_agregado_ml
	${fld:ocd_monto_impuesto_ma}, --mde_monto_valor_agregado_ma
	${fld:ocd_monto_neto_ml}, --mde_monto_neto_ml
	${fld:ocd_monto_neto_ma}, --mde_monto_neto_ma
	'RE', --mde_estatus
	'${def:locale}', --mde_idioma
	'${def:user}', --mde_usuario
	{d '${def:date}'}, --mde_fecha_registro
  	'${def:time}', --mde_hora	
  	${fld:mde_estatus_existencia} --mde_estatus_existencia
)

RETURNING smn_movimiento_detalle_id;