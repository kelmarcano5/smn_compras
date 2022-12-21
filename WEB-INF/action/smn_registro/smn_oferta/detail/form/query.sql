	SELECT
	smn_compras.smn_oferta.smn_oferta_id,
	smn_compras.smn_cotizacion.smn_cotizacion_id,
	smn_compras.smn_documentos.dcc_codigo ||' - '|| smn_compras.smn_documentos.dcc_nombre as smn_documento_id,
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.req_cabecera_version,
	smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre as smn_item_compras_id,
	smn_compras.smn_oferta.ofe_numero_documento,
	smn_base.smn_servicios.svc_codigo ||'-'|| smn_base.smn_servicios.svc_descripcion as smn_servicios_compras_id,
	smn_compras.smn_oferta.ofe_fecha_registro,
	smn_compras.smn_oferta.ofe_cantidad,
	smn_compras.smn_oferta.ofe_activo_fijo_alter_prov,
	smn_compras.smn_oferta.ofe_fecha_de_requerido,
	smn_compras.smn_oferta.ofe_item_alter_prov,
	smn_base.smn_monedas.mon_codigo ||'-'|| smn_base.smn_monedas.mon_nombre as mon_nombre,
	smn_compras.smn_oferta.ofe_monto_ma,
	smn_compras.smn_oferta.ofe_monto_ml,
	smn_compras.smn_oferta.ofe_observaciones,
	smn_compras.smn_oferta.ofe_precio_ma,
	smn_compras.smn_oferta.ofe_precio_ml,
	smn_compras.smn_oferta.ofe_producto_alterno,
	smn_base.smn_tasas_de_cambio.tca_tasa_cambio as tca_tasa_cambio,
	case 
	when smn_compras.smn_oferta.ofe_estatus='RE' then '${lbl:b_received}'
	when smn_compras.smn_oferta.ofe_estatus='AN' then '${lbl:b_analyzed}'
	when smn_compras.smn_oferta.ofe_estatus='AP' then '${lbl:b_aprobated}'
	when smn_compras.smn_oferta.ofe_estatus='RZ' then '${lbl:b_rejected}'
	end as ofe_estatus,
	smn_compras.smn_oferta.smn_cotizacion_id,
	smn_compras.smn_cotizacion.cot_numero_documento,
	smn_base.smn_auxiliar.aux_codigo || ' - ' || smn_base.smn_auxiliar.aux_descripcion as smn_proveedor_id
from
	smn_compras.smn_oferta
	inner join  smn_compras.smn_cotizacion on smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_oferta.smn_cotizacion_id
	inner join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_oferta.smn_item_compras_id
	inner join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_oferta.smn_documento_id
	inner join smn_compras.smn_proveedor on smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_oferta.smn_proveedor_id
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
	inner join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_cotizacion.smn_requisicion_detalle_id
	inner join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	LEFT OUTER JOIN smn_base.smn_servicios on smn_base.smn_servicios.smn_servicios_id = smn_compras.smn_oferta.smn_servicios_compras_id
	LEFT OUTER JOIN smn_base.smn_monedas on smn_base.smn_monedas.smn_monedas_id = smn_compras.smn_oferta.ofe_moneda_id
	LEFT OUTER JOIN smn_base.smn_tasas_de_cambio on smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id = smn_compras.smn_oferta.ofe_tasa

where	
	smn_compras.smn_oferta.smn_oferta_id = ${fld:id}
