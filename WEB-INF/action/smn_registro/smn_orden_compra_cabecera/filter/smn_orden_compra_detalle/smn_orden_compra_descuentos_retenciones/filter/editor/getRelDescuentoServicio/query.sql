SELECT
	smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id AS codigo_descuento_id,
	smn_base.smn_descuentos_retenciones.dyr_codigo AS tipo_descuento,
	smn_base.smn_descuentos_retenciones.dyr_codigo || ' - ' || smn_base.smn_descuentos_retenciones.dyr_descripcion AS item,
	smn_base.smn_descuentos_retenciones.dyr_porcentaje_base AS base,
	smn_base.smn_descuentos_retenciones.dyr_porcentaje_descuento AS calculo
FROM
	smn_base.smn_rel_serv_desc_retenc
	INNER JOIN
	smn_base.smn_descuentos_retenciones ON smn_base.smn_rel_serv_desc_retenc.smn_descuentos_retenciones_id = smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id 
WHERE
	smn_base.smn_rel_serv_desc_retenc.smn_servicios_id = ${fld:id}