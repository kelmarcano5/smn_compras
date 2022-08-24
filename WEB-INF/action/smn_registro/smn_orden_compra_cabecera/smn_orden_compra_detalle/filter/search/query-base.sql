select
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id,
		(select smn_compras.smn_lineas.lin_nombre|| ' - ' || smn_compras.smn_lineas.lin_codigo from  smn_compras.smn_lineas where smn_compras.smn_lineas.smn_lineas_id is not null  and smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_orden_compra_detalle.smn_linea_id) as smn_linea_id_combo,
	(select smn_compras.smn_servicio.sco_nombre|| ' - ' || smn_compras.smn_servicio.sco_codigo from  smn_compras.smn_servicio where smn_compras.smn_servicio.smn_servicio_id is not null and smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_orden_compra_detalle.smn_servicios_id) as smn_servicios_id_combo,
	(select smn_base.smn_item.itm_nombre|| ' - ' || smn_base.smn_item.itm_codigo from  smn_base.smn_item where smn_base.smn_item.smn_item_id is not null and smn_base.smn_item.smn_item_id=smn_compras.smn_orden_compra_detalle.smn_item_rf) as smn_item_rf_combo,
	(select smn_base.smn_monedas.mon_codigo|| ' - ' || smn_base.smn_monedas.mon_nombre from smn_base.smn_monedas where smn_base.smn_monedas.smn_monedas_id=smn_compras.smn_orden_compra_detalle.smn_moneda_rf) as smn_moneda_rf_combo,
	(select smn_base.smn_tasas_de_cambio.tca_moneda_referencia|| ' - ' || smn_base.smn_tasas_de_cambio.tca_tasa_cambio from smn_base.smn_tasas_de_cambio where smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id=smn_compras.smn_orden_compra_detalle.smn_tasa_rf) as smn_tasa_rf_combo,
	(select smn_base.smn_unidad_medida.unm_codigo|| ' - ' || smn_base.smn_unidad_medida.unm_descripcion from smn_base.smn_unidad_medida where smn_base.smn_unidad_medida.smn_unidad_medida_id=smn_compras.smn_orden_compra_detalle.smn_unidad_medida_rf) as smn_unidad_medida_rf_combo,
	(select smn_base.smn_activo_fijo.acf_codigo|| ' - ' || smn_base.smn_activo_fijo.acf_nombre from smn_base.smn_activo_fijo where smn_base.smn_activo_fijo.smn_afijo_id=smn_compras.smn_orden_compra_detalle.smn_afijo_rf) as smn_afijo_rf_combo,
	(select smn_compras.smn_contrato_modulo.ctm_cantidad|| ' - ' || smn_compras.smn_contrato_modulo.ctm_precio from smn_compras.smn_contrato_modulo where smn_compras.smn_contrato_modulo.smn_contrato_modulo_id=smn_compras.smn_orden_compra_detalle.smn_contrato_modulo_id) as smn_contrato_modulo_id_combo,
	smn_compras.smn_orden_compra_detalle.smn_contrato_modulo_id,
	smn_compras.smn_orden_compra_detalle.ocd_cantidad_pedida,
	smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ma,
	smn_compras.smn_orden_compra_detalle.ocd_descripcion,
	smn_compras.smn_orden_compra_detalle.ocd_fecha_registro
from
	smn_compras.smn_orden_compra_detalle
where
	smn_orden_compra_detalle_id is not null
	and smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id=${fld:id2}
	${filter}
order by
		smn_orden_compra_detalle_id
