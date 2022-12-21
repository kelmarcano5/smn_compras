INSERT INTO smn_compras.smn_req_detalle_impuesto
(
	smn_req_detalle_impuesto_id,
  	smn_requisicion_detalle_id,
  	rim_monto_base,
  	smn_cod_impuesto_deduc_rf,
  	smn_porcentaje_impuesto,
  	smn_sustraendo,
 	rim_monto_impuesto,
  	smn_moneda_rf,
  	smn_tasa_rf,
  	rim_monto_imp_moneda_alterna,
 	rim_idioma,
 	rim_usuario,
  	rim_fecha_registro,
  	rim_hora
)
VALUES
(
	nextval('smn_compras.seq_smn_req_detalle_impuesto'),--smn_req_detalle_impuesto_id
  	${fld:req_detalle_id}, --smn_requisicion_detalle_id
  	${fld:rim_monto_base}, --rim_monto_base
  	${fld:smn_cod_impuesto_deduc_rf}, --smn_cod_impuesto_deduc_rf
  	${fld:smn_porcentaje_impuesto}, --smn_porcentaje_impuesto
  	${fld:smn_sustraendo}, --smn_sustraendo
 	${fld:rim_monto_impuesto}, --rim_monto_impuesto
  	${fld:smn_moneda_rf}, --smn_moneda_rf
  	${fld:smn_tasa_rf}, --smn_tasa_rf
  	${fld:rim_monto_imp_moneda_alterna}, --rim_monto_imp_moneda_alterna
  	'${def:locale}', --rim_idioma
    '${def:user}', --rim_usuario
    {d '${def:date}'}, --rim_fecha_registro
    '${def:time}' --rim_hora
)

RETURNING smn_req_detalle_impuesto_id