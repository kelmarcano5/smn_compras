INSERT INTO smn_compras.smn_orden_compra_impuesto
(
	smn_impuesto_oc_id,
	smn_orden_compra_detalle_id,
	smn_cod_impuesto_deduc_rf,
	oci_monto_base_ml,
	oci_porcentaje_impuesto,
	oci_sustraendo_ml,
	smn_tipo_impuesto_rf,
	oci_monto_impuesto_ml,
	smn_moneda,
	smn_tasa,
	oci_monto_impuesto_ma,
	oci_idioma,
	oci_usuario,
	oci_fecha_registro,
	oci_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_orden_compra_impuesto},
	${fld:smn_orden_compra_detalle_id},
	${fld:smn_cod_impuesto_deduc_rf},
	${fld:oci_monto_base_ml},
	${fld:oci_porcentaje_impuesto},
	${fld:oci_sustraendo_ml},
	${fld:smn_tipo_impuesto_rf},
	${fld:oci_monto_impuesto_ml},
	${fld:smn_moneda},
	${fld:smn_tasa},
	${fld:oci_monto_impuesto_ma},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
);

WITH impuestos AS (
	SELECT 
		CASE
			WHEN SUM(smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ml) IS NULL THEN 0
			WHEN SUM(smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ml) IS NOT NULL THEN SUM(smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ml)
		END AS total_monto_impuesto_ml,
		CASE
			WHEN SUM(smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ma) IS NULL THEN 0
			WHEN SUM(smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ma) IS NOT NULL THEN SUM(smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ma)
		END AS total_monto_impuesto_ma
	FROM 
		smn_compras.smn_orden_compra_impuesto
	WHERE
		smn_compras.smn_orden_compra_impuesto.smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id}
)

UPDATE smn_compras.smn_orden_compra_detalle SET
	ocd_monto_impuesto_ml=impuestos.total_monto_impuesto_ml,
	ocd_monto_impuesto_ma=impuestos.total_monto_impuesto_ma,
	ocd_idioma='${def:locale}',
	ocd_usuario='${def:user}',
	ocd_fecha_registro={d '${def:date}'},
	ocd_hora='${def:time}'
FROM 
	impuestos
WHERE
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id};

-- **** ACTUALIZA LOS MONTOS DE DESCUENTOS Y RETENCIONES EN LA TABLA -> smn_compras.smn_orden_compra_detalle****

WITH descuentos AS (
	SELECT 
		CASE
			WHEN SUM(smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento) IS NULL THEN 0
			WHEN SUM(smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento) IS NOT NULL THEN SUM(smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento)
		END AS total_monto_descuento_ml,
		CASE
			WHEN SUM(smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento_ma) IS NULL THEN 0
			WHEN SUM(smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento_ma) IS NOT NULL THEN SUM(smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento_ma)
		END AS total_monto_descuento_ma
	FROM 
		smn_compras.smn_orden_compra_descuentos_retenciones
	WHERE
		smn_compras.smn_orden_compra_descuentos_retenciones.smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id}
)

UPDATE smn_compras.smn_orden_compra_detalle SET
	ocd_monto_desc_reten_ml=descuentos.total_monto_descuento_ml,
	ocd_monto_desc_reten_ma=descuentos.total_monto_descuento_ma,
	ocd_idioma='${def:locale}',
	ocd_usuario='${def:user}',
	ocd_fecha_registro={d '${def:date}'},
	ocd_hora='${def:time}'
FROM 
	descuentos
WHERE
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id};	

-- **** ACTUALIZA LOS MONTOS NETO EN LA TABLA -> smn_compras.smn_orden_compra_detalle****

WITH ocd AS (
	SELECT 
		smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ml,
		smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ma,
		smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ml,
		smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ma,
		smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ml,
		smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ma
	FROM 
		smn_compras.smn_orden_compra_detalle
	WHERE
		smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id}
)

UPDATE smn_compras.smn_orden_compra_detalle SET
	ocd_monto_neto_ml=ocd.ocd_monto_bruto_ml+ocd.ocd_monto_impuesto_ml-ocd.ocd_monto_desc_reten_ml,
	ocd_monto_neto_ma=ocd.ocd_monto_bruto_ma+ocd.ocd_monto_impuesto_ma-ocd.ocd_monto_desc_reten_ma,
	ocd_idioma='${def:locale}',
	ocd_usuario='${def:user}',
	ocd_fecha_registro={d '${def:date}'},
	ocd_hora='${def:time}'
FROM 
	ocd
WHERE
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id};

-- **** ACTUALIZA LOS MONTOS EN LA TABLA -> smn_compras.smn_orden_compra_cabecera****

WITH ocd AS (
	SELECT 
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ml) AS total_monto_impuesto_ml,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ma) AS total_monto_impuesto_ma,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ml) AS total_monto_descuento_ml,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ma) AS total_monto_descuento_ma,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ml) AS total_monto_neto_ml,
		SUM(smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ma) AS total_monto_neto_ma
	FROM 
		smn_compras.smn_orden_compra_detalle
	WHERE
		smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id=${fld:smn_orden_compra_detalle_id}
)

UPDATE smn_compras.smn_orden_compra_cabecera SET
	occ_monto_impuesto_ml=ocd.total_monto_impuesto_ml,
	occ_monto_impuesto_ma=ocd.total_monto_impuesto_ma,
	occ_monto_desc_rete_ml=ocd.total_monto_descuento_ml,
	occ_monto_neto_ml=ocd.total_monto_neto_ml,
	occ_monto_neto_ma=ocd.total_monto_neto_ma,
	occ_idioma='${def:locale}',
	occ_usuario='${def:user}',
	occ_fecha_registro={d '${def:date}'},
	occ_hora='${def:time}'
FROM 
	ocd
WHERE
	smn_compras.smn_orden_compra_cabecera.smn_orden_compra_cabecera_id = (SELECT smn_orden_compra_cabecera_id FROM smn_compras.smn_orden_compra_detalle WHERE smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id});