select
		smn_compras.smn_orden_compra_descuentos_retenciones.smn_descuento_retencion_oc_id,
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_orden_compra_detalle_id,
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_codigo_descuento_rf,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_base,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_porcentaje,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento,
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_moneda_rf,
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_tasa_rf,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_base_ma,
	smn_compras.smn_orden_compra_descuentos_retenciones.ocd_monto_descuento_ma,
	smn_compras.smn_orden_compra_descuentos_retenciones.fecha_registro
from
	smn_compras.smn_orden_compra_descuentos_retenciones 
where
	smn_compras.smn_orden_compra_descuentos_retenciones.smn_orden_compra_descuentos_retenciones_id = ${fld:id}
