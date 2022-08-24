select
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.req_estatus as req_estatus_pl0,
	smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id,
	smn_compras.smn_lineas.lin_codigo||' - '|| smn_compras.smn_lineas.lin_nombre as smn_linea_id,
	smn_base.smn_contrato_base.ctr_codigo||' - '||smn_base.smn_contrato_base.ctr_nombre as smn_contrato_id,
	smn_compras.smn_requisicion_detalle.rss_cantidad,
	smn_compras.smn_requisicion_detalle.rrs_precio,
	smn_compras.smn_requisicion_detalle.rrs_monto,
	smn_compras.smn_requisicion_detalle.rrs_fecha_registro
	
from
	smn_compras.smn_requisicion_detalle
	left OUTER join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	left outer join smn_compras.smn_lineas on smn_compras.smn_lineas.smn_lineas_id = smn_compras.smn_requisicion_detalle.smn_linea_id
	left outer join smn_base.smn_contrato_base on smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_requisicion_detalle.smn_contrato_id

where
	smn_requisicion_detalle_id is not null
	and 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id=smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	${filter}
order by
		smn_requisicion_detalle_id
