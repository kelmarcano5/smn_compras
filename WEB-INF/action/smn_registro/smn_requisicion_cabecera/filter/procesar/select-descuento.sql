SELECT
	smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id,
	smn_base.smn_descuentos_retenciones.dyr_porcentaje_descuento
FROM
	smn_base.smn_rel_item_desc_retenc
INNER JOIN
	smn_base.smn_descuentos_retenciones
ON
	smn_base.smn_rel_item_desc_retenc.smn_descuentos_retenciones_id = smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id
WHERE
	smn_item_id = ${fld:smn_item_rf}
UNION
SELECT
	smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id,
	smn_base.smn_descuentos_retenciones.dyr_porcentaje_descuento
FROM
	smn_base.smn_rel_serv_desc_retenc
INNER JOIN
	smn_base.smn_descuentos_retenciones
ON
	smn_base.smn_rel_serv_desc_retenc.smn_descuentos_retenciones_id = smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id
WHERE
	smn_servicios_id = ${fld:smn_servicio_id}
UNION
SELECT
	smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id,
	smn_base.smn_descuentos_retenciones.dyr_porcentaje_descuento
FROM
	smn_base.smn_rel_afijo_desc_retenc
INNER JOIN
	smn_base.smn_descuentos_retenciones
ON
	smn_base.smn_rel_afijo_desc_retenc.smn_descuentos_retenciones_id = smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id
WHERE
	smn_afijo_id = ${fld:smn_afijo_id}