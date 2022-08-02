INSERT INTO smn_inventario.smn_movimiento_cabecera 
(
	smn_movimiento_cabecera_id,
	smn_modulo_rf,
	smn_documento_origen_rf,
	mca_numero_documento_origen,
	smn_documento_id,
	mca_numero,
	smn_tipo_documento_pago_id,
	smn_proveedor_rf,
	smn_orden_compra_rf,
	smn_almacen_rf,
	smn_sucursal_rf,
	smn_entidad_rf,
	mca_recibo,
	mca_monto_documento_ml,
	mca_monto_documento_ma,
	mca_monto_diminucion_ml,
	mca_monto_diminucion_ma,
	mca_monto_valor_agregado_ml,
	mca_monto_valor_agregado_ma,
	mca_monto_neto_ml,
	mcc_monto_neto_ma,
	smn_moneda_rf,
	smn_tasa_rf,
	mca_fecha_recibida,
	mca_estatus_proceso,
	mca_estatus_financiero,
	mca_idioma,
	mca_usuario,
	mca_fecha_registro,
	mca_hora
)
VALUES
(
	nextval('smn_inventario.seq_smn_movimiento_cabecera'), --smn_movimiento_cabecera_id
	${fld:smn_modulos_id},--smn_modulo_rf
	${fld:smn_documento_id}, --smn_documento_origen_rf
	${fld:occ_orden_compra_numero},  --mca_numero_documento_origen
	${fld:smn_documentos_generales_id},--smn_documento_id
	${fld:secuencia}, --mca_numero
	${fld:smn_tipo_documento_id}, --smn_tipo_documento_pago_id
	${fld:smn_proveedor_id},--smn_proveedor_rf
	${fld:smn_orden_compra_cabecera_id}, --smn_orden_compra_rf
	${fld:smn_almacen_rf}, --smn_almacen_rf 
	${fld:smn_sucursal_rf}, --smn_sucursal_rfç
	${fld:smn_entidad_rf},  --smn_entidad_rf
	'0', --mca_recibo
	${fld:occ_monto_ml}, --mca_monto_documento_ml
	${fld:occ_monto_ma}, --mca_monto_documento_ma
	${fld:occ_monto_desc_rete_ml}, --mca_monto_diminucion_ml
	'0', --mca_monto_valor_agregado_ma
	${fld:occ_monto_impuesto_ml}, --mca_monto_valor_agregado_ml
	${fld:occ_monto_impuesto_ma}, --mca_monto_valor_agregado_ma
	${fld:occ_monto_neto_ml}, --mca_monto_neto_ml
	'0', --mcc_monto_neto_ma
	${fld:smn_moneda_rf}, --smn_moneda_rf
	${fld:smn_tasa_rf}, --smn_tasa_rf
	${fld:occ_fecha_orde_compra}, --mca_fecha_operacion
	'GE', --mca_estatus_proceso
	'PE', --mca_estatus_financiero
	'${def:locale}', --mca_idioma
    '${def:user}', --mca_usuario
    {d '${def:date}'}, --mca_fecha_registro
    '${def:time}' --mca_hora
)

RETURNING smn_movimiento_cabecera_id; 	