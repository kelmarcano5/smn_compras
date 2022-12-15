select
	${field},
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.req_cabecera_version,
	--smn_base.smn_auxiliar.aux_codigo ||'-'|| smn_base.smn_auxiliar.aux_descripcion as smn_auxiliar_id,
	smn_compras.smn_requisicion_cabecera.req_numero,
	smn_compras.smn_requisicion_cabecera.req_descripcion,
	smn_compras.smn_requisicion_cabecera.req_fecha_requerido,
	smn_compras.smn_requisicion_cabecera.req_fecha_registro,
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.req_estatus as req_estatus_pl0,
	smn_compras.smn_requisicion_cabecera.req_descripcion as smn_requisicion_cabecera_id,
	smn_compras.smn_lineas.lin_codigo||' - '|| smn_compras.smn_lineas.lin_nombre as smn_linea_id,
	smn_base.smn_contrato_base.ctr_codigo||' - '||smn_base.smn_contrato_base.ctr_nombre as smn_contrato_id,
	smn_compras.smn_requisicion_detalle.rss_cantidad,
	smn_compras.smn_requisicion_detalle.rrs_precio,
	smn_compras.smn_requisicion_detalle.rrs_monto,
	smn_compras.smn_requisicion_detalle.rrs_fecha_registro,
	case
		when smn_compras.smn_requisicion_cabecera.req_estatus='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='GE' then '${lbl:b_generated}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='AP' then '${lbl:b_aprobated}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='VE' then '${lbl:b_versioned}'
	end as req_estatus,
	smn_compras.smn_tipo_documento.tdc_codigo||' - '||smn_compras.smn_tipo_documento.tdc_nombre as smn_tipo_documento_id,
	smn_compras.smn_documentos.dcc_codigo||' - ' || smn_compras.smn_documentos.dcc_nombre as smn_documento_id,
	case
		when smn_compras.smn_requisicion_cabecera.req_estatus_ruta='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_requisicion_cabecera.req_estatus_ruta='AP' then '${lbl:b_aprobated}'
	end as req_estatus_ruta,
	smn_base.smn_entidades.ent_codigo|| ' - ' || smn_base.smn_entidades.ent_descripcion_corta  as smn_entidad_id,
	smn_base.smn_sucursales.suc_codigo|| ' - ' || smn_base.smn_sucursales.suc_nombre as smn_sucursal_id,
    smn_base.smn_entidades.smn_entidades_id as empresa,
	smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id,
 	smn_compras.smn_documentos.dcc_recurrente,
   case
        when smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'IT' THEN smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre
        when smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'SE' THEN smn_compras.smn_servicio.sco_codigo|| ' - ' || smn_compras.smn_servicio.sco_nombre
        when smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'AF' THEN smn_base.smn_activo_fijo.acf_codigo ||' - '|| smn_base.smn_activo_fijo.acf_nombre
    end as smn_item_id
from
   smn_compras.smn_requisicion_cabecera
   left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_requisicion_cabecera.smn_documento_id
   left outer join smn_compras.smn_tipo_documento on smn_compras.smn_tipo_documento.smn_tipo_documento_id = smn_compras.smn_documentos.smn_tipo_documento_id
   left outer join smn_base.smn_entidades on smn_base.smn_entidades.smn_entidades_id = smn_compras.smn_requisicion_cabecera.smn_entidad_id
   left outer join smn_base.smn_sucursales on smn_base.smn_sucursales.smn_sucursales_id = smn_compras.smn_requisicion_cabecera.smn_sucursal_id
   left outer join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id= smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id
   left outer join smn_compras.smn_lineas on smn_compras.smn_lineas.smn_lineas_id = smn_compras.smn_requisicion_detalle.smn_linea_id
   left outer join smn_base.smn_contrato_base on smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_requisicion_detalle.smn_contrato_id
   left outer join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_requisicion_detalle.smn_item_id
   left outer join smn_compras.smn_servicio on smn_compras.smn_servicio.smn_servicio_id = smn_compras.smn_requisicion_detalle.smn_servicio_id
   left outer join smn_base.smn_activo_fijo on smn_base.smn_activo_fijo.smn_afijo_id = smn_compras.smn_requisicion_detalle.smn_afijo_id
where
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id is not null
	${filter}