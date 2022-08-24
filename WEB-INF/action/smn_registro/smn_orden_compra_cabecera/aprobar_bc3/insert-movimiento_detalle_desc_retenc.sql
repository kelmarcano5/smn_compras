INSERT INTO smn_inventario.smn_movimiento_detalle_desc_ret
(
	smn_movimiento_detalle_desc_ret_id,
	smn_movimiento_detalle_id,
	smn_codigo_descuento_rf,
	mdd_monto_base,
	smn_porcentaje_rf,
	mdd_monto_descuento,
	smn_moneda_rf,
	smn_tasa_rf,
	mdd_monto_base_ma,
	mdd_monto_descuento_ma,
	mdd_idioma,
	mdd_usuario,
	mdd_fecha_registro,
	mdd_hora
)
VALUES
(
	nextval('smn_inventario.seq_smn_movimiento_detalle_desc_ret'), --smn_movimiento_detalle_desc_ret_id
	${fld:smn_movimiento_detalle_id}, --smn_movimiento_detalle_id
	${fld:smn_codigo_descuento_rf}, --smn_codigo_descuento_rf
	${fld:mdd_monto_base_ml}, --ocd_monto_base
	${fld:smn_porcentaje_rf}, --smn_porcentaje_rf
	${fld:mdd_monto_descuento_ml}, --ocd_monto_descuento
	${fld:smn_moneda_rf}, --smn_moneda_rf
	${fld:smn_tasa_rf}, --smn_tasa_rf
	${fld:mdd_monto_base_ma}, --ocd_monto_base_ma
	${fld:mdd_monto_descuento_ma}, --ocd_monto_descuento_ma
	'${def:locale}', --mdd_idioma
	'${def:user}', --mdd_usuario
	{d '${def:date}'}, --mdd_fecha_registro
  	'${def:time}' --mdd_hora	
)

RETURNING smn_movimiento_detalle_desc_ret_id;