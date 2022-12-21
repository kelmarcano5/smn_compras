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
 	ocd_idioma,
  	ocd_usuario,
  	ocd_fecha_registro,
  	ocd_hora
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_descuentos_retenciones'), --smn_descuento_retencion_oc_id
  	${fld:smn_orden_compra_detalle_id}, --smn_orden_compra_detalle_id
  	${fld:smn_codigo_descuento_rf}, --smn_codigo_descuento_rf
  	${fld:ocd_monto_base}, --ocd_monto_base
  	${fld:ocd_porcentaje}, --ocd_porcentaje
  	${fld:ocd_monto_descuento}, --ocd_monto_descuento
  	${fld:rio_moneda_rf}, --smn_moneda_rf
  	${fld:rio_tasa_rf}, --smn_tasa_rf
  	${fld:ocd_monto_base_ma}, --ocd_monto_base_ma
  	${fld:ocd_monto_descuento_ma}, --ocd_monto_descuento_ma
 	'${def:locale}', --ocd_idioma
    '${def:user}', --ocd_usuario
    {d '${def:date}'}, --ocd_fecha_registro
    '${def:time}' --ocd_hora
)
RETURNING smn_descuento_retencion_oc_id;
