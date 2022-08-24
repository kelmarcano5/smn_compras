UPDATE smn_compras.smn_orden_compra_impuesto SET
	smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id},
	smn_cod_impuesto_deduc_rf=${fld:smn_cod_impuesto_deduc_rf},
	oci_monto_base_ml=${fld:oci_monto_base_ml},
	oci_porcentaje_impuesto=${fld:oci_porcentaje_impuesto},
	oci_sustraendo_ml=${fld:oci_sustraendo_ml},
	smn_tipo_impuesto_rf=${fld:smn_tipo_impuesto_rf},
	oci_monto_impuesto_ml=${fld:oci_monto_impuesto_ml},
	smn_moneda=${fld:smn_moneda},
	smn_tasa=${fld:smn_tasa},
	oci_monto_impuesto_ma=${fld:oci_monto_impuesto_ma},
	idioma='${def:locale}',
	usuario='${def:user}',
	fecha_registro={d '${def:date}'},
	hora='${def:time}'

WHERE
	smn_impuesto_oc_id=${fld:id}

