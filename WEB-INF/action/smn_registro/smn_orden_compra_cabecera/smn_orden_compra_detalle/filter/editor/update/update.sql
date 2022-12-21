UPDATE smn_compras.smn_orden_compra_detalle SET
	smn_linea_id=${fld:smn_linea_id},
	smn_servicios_id=${fld:smn_servicios_id},
	smn_item_rf=${fld:smn_item_rf},
	smn_afijo_rf=${fld:smn_afijo_rf},
	smn_contrato_modulo_id=${fld:smn_contrato_modulo_id},
	ocd_descripcion=${fld:ocd_descripcion},
	ocd_cantidad_pedida=${fld:ocd_cantidad_pedida},
	smn_unidad_medida_rf=${fld:smn_unidad_medida_rf},
	ocd_monto_bruto_ml=${fld:ocd_monto_bruto_ml},
	ocd_monto_impuesto_ml=${fld:ocd_monto_impuesto_ml},
	ocd_monto_desc_reten_ml=${fld:ocd_monto_desc_reten_ml},
	smn_moneda_rf=${fld:smn_moneda_rf},
	smn_tasa_rf=${fld:smn_tasa_rf},
	ocd_monto_bruto_ma=${fld:ocd_monto_bruto_ma},
	ocd_monto_impuesto_ma=${fld:ocd_monto_impuesto_ma},
	ocd_monto_desc_reten_ma=${fld:ocd_monto_desc_reten_ma},
	ocd_costo_ml = ${fld:ocd_precio_ml},
	ocd_idioma='${def:locale}',
	ocd_usuario='${def:user}',
	ocd_fecha_registro={d '${def:date}'},
	ocd_hora='${def:time}',
	ocd_precio_ml=${fld:ocd_precio_ml},
	ocd_precio_ma=${fld:ocd_precio_ma}

WHERE
	smn_orden_compra_detalle_id=${fld:id};

WITH rows AS (
	SELECT 
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ml) AS total_monto_bruto_ml,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ml) AS total_monto_impuesto_ml,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ml) AS total_monto_desc_reten_ml,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ma) AS total_monto_bruto_ma,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ma) AS total_monto_impuesto_ma
	FROM 
		smn_compras.smn_orden_compra_detalle
	WHERE
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id=${fld:smn_orden_compra_cabecera_id}
)

UPDATE smn_compras.smn_orden_compra_cabecera SET
	occ_monto_ml=rows.total_monto_bruto_ml,
	occ_monto_impuesto_ml=rows.total_monto_impuesto_ml,
	occ_monto_desc_rete_ml=rows.total_monto_desc_reten_ml,
	occ_monto_ma=rows.total_monto_bruto_ma,
	occ_monto_impuesto_ma=rows.total_monto_impuesto_ma,
	occ_monto_neto_ml = (rows.total_monto_bruto_ml+rows.total_monto_impuesto_ml)-rows.total_monto_desc_reten_ml
FROM 
	rows
WHERE
	smn_compras.smn_orden_compra_cabecera.smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}

