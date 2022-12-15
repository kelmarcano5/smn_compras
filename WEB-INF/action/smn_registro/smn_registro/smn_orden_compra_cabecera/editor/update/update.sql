UPDATE smn_compras.smn_orden_compra_cabecera SET
	smn_entidad_rf=${fld:smn_entidad_rf},
	smn_sucursal_rf=${fld:smn_sucursal_rf},
	smn_documento_id=${fld:smn_documento_id},
	occ_orden_compra_numero=${fld:occ_orden_compra_numero},
	occ_descripcion=${fld:occ_descripcion},
	smn_proveedor_id=${fld:smn_proveedor_id},
	smn_auxiliar_rf=${fld:smn_auxiliar_rf},
	occ_fecha_elaboracion=${fld:occ_fecha_elaboracion},
	occ_fecha_orde_compra=${fld:occ_fecha_orde_compra},
	smn_forma_pago_rf=${fld:smn_forma_pago_rf},
	smn_condicion_pago_rf=${fld:smn_condicion_pago_rf},
	occ_observacion=${fld:occ_observacion},
	occ_monto_ml=${fld:occ_monto_ml},
	occ_monto_impuesto_ml=${fld:occ_monto_impuesto_ml},
	occ_monto_desc_rete_ml=${fld:occ_monto_desc_rete_ml},
	smn_moneda_rf=${fld:smn_moneda_rf},
	smn_tasa_rf=${fld:smn_tasa_rf},
	occ_monto_ma=${fld:occ_monto_ma},
	occ_monto_impuesto_ma=${fld:occ_monto_impuesto_ma},
	occ_idioma='${def:locale}',
	occ_usuario='${def:user}',
	occ_fecha_registro={d '${def:date}'},
	occ_hora='${def:time}'

WHERE
	smn_orden_compra_cabecera_id=${fld:id}

