select
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_descuento_retencion_oc_id,
	(select smn_base.smn_descuentos_retenciones.dyr_codigo|| ' - ' || smn_base.smn_descuentos_retenciones.dyr_descripcion from smn_base.smn_descuentos_retenciones where smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id=smn_compras.smn_orden_compra_descuentos_retenciones.smn_codigo_descuento_rf) as smn_codigo_descuento_rf_combo,
	(select smn_base.smn_monedas.mon_codigo|| ' - ' || smn_base.smn_monedas.mon_nombre from smn_base.smn_monedas where smn_base.smn_monedas.smn_monedas_id=smn_compras.smn_orden_compra_descuentos_retenciones.smn_moneda_rf) as smn_moneda_rf_combo,
	(select smn_base.smn_tasas_de_cambio.tca_moneda_referencia|| ' - ' || smn_base.smn_tasas_de_cambio.tca_tasa_cambio from smn_base.smn_tasas_de_cambio where smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id=smn_compras.smn_orden_compra_descuentos_retenciones.smn_tasa_rf) as smn_tasa_rf_combo,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_base,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_porcentaje,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_fecha_registro,
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_orden_compra_detalle_id
	
from
	smn_compras.smn_orden_compra_descuentos_retenciones
where
	smn_descuento_retencion_oc_id is not null and smn_compras.smn_orden_compra_descuentos_retenciones.smn_orden_compra_detalle_id = ${fld:id2}
	${filter} 

order by
		smn_descuento_retencion_oc_id desc
