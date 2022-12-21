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
	rim_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_req_detalle_impuesto},
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
