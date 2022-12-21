SELECT
	smn_base.smn_descuentos_retenciones.*
FROM
	smn_base.smn_descuentos_retenciones
	INNER JOIN
	smn_base.smn_rel_item_desc_retenc ON smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id = smn_base.smn_rel_item_desc_retenc.smn_descuentos_retenciones_id
WHERE
	smn_base.smn_rel_item_desc_retenc.smn_item_id = ${fld:smn_item_compras_id}