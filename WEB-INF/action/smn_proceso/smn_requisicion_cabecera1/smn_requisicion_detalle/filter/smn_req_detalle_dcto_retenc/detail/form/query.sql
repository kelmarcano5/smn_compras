select
	smn_compras.smn_req_detalle_dcto_retenc.smn_req_detalle_dcto_retenc_id,
	smn_compras.smn_req_detalle_dcto_retenc.smn_requisicion_detalle_id,
	smn_base.smn_descuentos_retenciones.dyr_codigo ||'-'|| smn_base.smn_descuentos_retenciones.dyr_descripcion as smn_codigo_descuento_rf,
	smn_compras.smn_req_detalle_dcto_retenc.drc_monto_base,
	smn_base.smn_descuentos_retenciones.dyr_porcentaje_base as drc_porcentaje,
	smn_compras.smn_req_detalle_dcto_retenc.drc_monto_descuento,
	smn_compras.smn_req_detalle_dcto_retenc.drc_fecha_registro
	
from
	smn_compras.smn_req_detalle_dcto_retenc
	inner join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = 	smn_compras.smn_req_detalle_dcto_retenc.smn_requisicion_detalle_id
	inner join smn_base.smn_descuentos_retenciones on smn_base.smn_descuentos_retenciones.smn_descuentos_retenciones_id = smn_compras.smn_req_detalle_dcto_retenc.smn_codigo_descuento_rf
where
	smn_req_detalle_dcto_retenc_id = ${fld:id}
