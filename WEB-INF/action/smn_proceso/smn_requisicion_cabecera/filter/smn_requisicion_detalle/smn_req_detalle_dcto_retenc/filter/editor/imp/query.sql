select smn_base.smn_descuentos_retenciones.dyr_porcentaje_base as base, smn_base.smn_descuentos_retenciones.dyr_porcentaje_descuento as calculo from smn_base.smn_descuentos_retenciones
inner join smn_base.smn_rel_item_desc_retenc on smn_base.smn_rel_item_desc_retenc.smn_descuentos_retenciones_id = smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id
where smn_base.smn_rel_item_desc_retenc.smn_descuentos_retenciones_id=${fld:id}