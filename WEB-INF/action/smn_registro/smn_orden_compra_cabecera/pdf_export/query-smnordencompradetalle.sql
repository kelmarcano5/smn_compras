select
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id,
	(select smn_compras.smn_servicio.sco_nombre|| ' - ' || smn_compras.smn_servicio.sco_codigo from  smn_compras.smn_servicio where smn_compras.smn_servicio.smn_servicio_id is not null and smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_orden_compra_detalle.smn_servicios_id) as smn_servicios_id_combo,
	(select smn_base.smn_item.itm_nombre|| ' - ' || smn_base.smn_item.itm_codigo from  smn_base.smn_item where smn_base.smn_item.smn_item_id is not null and smn_base.smn_item.smn_item_id=smn_compras.smn_orden_compra_detalle.smn_item_rf) as smn_item_rf_combo,
	(select smn_base.smn_monedas.mon_codigo|| ' - ' || smn_base.smn_monedas.mon_nombre from smn_base.smn_monedas where smn_base.smn_monedas.smn_monedas_id=smn_compras.smn_orden_compra_detalle.smn_moneda_rf) as smn_moneda_rf_combo,
	(select smn_base.smn_tasas_de_cambio.tca_moneda_referencia|| ' - ' || smn_base.smn_tasas_de_cambio.tca_tasa_cambio from smn_base.smn_tasas_de_cambio where smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id=smn_compras.smn_orden_compra_detalle.smn_tasa_rf) as smn_tasa_rf_combo,
	smn_compras.smn_orden_compra_detalle.ocd_cantidad_pedida,
	smn_compras.smn_orden_compra_detalle.ocd_cantidad_recibida,
	smn_compras.smn_orden_compra_detalle.ocd_costo_ml,
	smn_compras.smn_orden_compra_detalle.ocd_costo_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ma, 
	smn_compras.smn_orden_compra_fecha_entrega.ocf_cantidad,	
	smn_compras.smn_orden_compra_fecha_entrega.ocf_fecha_entrega	
from
	smn_compras.smn_orden_compra_detalle LEFT OUTER JOIN smn_compras.smn_orden_compra_fecha_entrega ON 
		smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id = smn_compras.smn_orden_compra_fecha_entrega.smn_orden_compra_detalle_id
where
smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id = ${fld:id}
order by 
	smn_compras.smn_orden_compra_fecha_entrega.ocf_fecha_entrega desc