UPDATE smn_compras.smn_impto_deduc_oferta SET
	smn_oferta_id=${fld:smn_oferta_id},
	rio_monto_base_ml=${fld:rio_monto_base_ml},
	rio_cod_impuesto_deduc_id=${fld:rio_cod_impuesto_deduc_id},
	rio_porcentaje_deduc=${fld:rio_porcentaje_deduc},
	rio_sustraendo=${fld:rio_sustraendo},
	rio_monto_impuesto_ml=${fld:rio_monto_impuesto_ml},
	rio_moneda_rf=${fld:rio_moneda_rf},
	rio_tasa_rf=${fld:rio_tasa_rf},
	rio_monto_impuesto_ma=${fld:rio_monto_impuesto_ma},
	rio_cod_descuento=${fld:rio_cod_descuento},
	rio_monto_base_ml=${fld:rio_monto_base_ml},
	rio_monto_base_ma=${fld:rio_monto_base_ma},
	rio_porcentaje_descuento=${fld:rio_porcentaje_descuento},
	rio_monto_descuento_ml=${fld:rio_monto_descuento_ml},
	rio_monto_descuento_ma=${fld:rio_monto_descuento_ma}

WHERE
	smn_impuest_deducc_oferta_id=${fld:id}

