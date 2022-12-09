UPDATE smn_compras.smn_req_detalle_impuesto SET
	smn_requisicion_detalle_id=${fld:smn_requisicion_detalle_id},
	rim_monto_base=${fld:rim_monto_base},
	smn_cod_impuesto_deduc_rf=${fld:smn_cod_impuesto_deduc_rf},
	smn_porcentaje_impuesto=${fld:smn_porcentaje_impuesto},
	smn_sustraendo=${fld:smn_sustraendo},
	rim_monto_impuesto=${fld:rim_monto_impuesto},
	smn_moneda_rf=${fld:smn_moneda_rf},
	smn_tasa_rf=${fld:smn_tasa_rf},
	rim_monto_imp_moneda_alterna=${fld:rim_monto_imp_moneda_alterna},
	rim_idioma='${def:locale}',
	rim_usuario='${def:user}',
	rim_fecha_registro={d '${def:date}'},
	rim_hora='${def:time}'

WHERE
	smn_req_detalle_impuesto_id=${fld:id}

