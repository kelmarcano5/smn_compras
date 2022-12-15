select
		smn_compras.smn_orden_compra_descuentos_retenciones.smn_codigo_descuento_rf
from
		smn_compras.smn_orden_compra_descuentos_retenciones
where
		upper(smn_compras.smn_orden_compra_descuentos_retenciones.smn_codigo_descuento_rf) = upper(${fld:smn_codigo_descuento_rf})
