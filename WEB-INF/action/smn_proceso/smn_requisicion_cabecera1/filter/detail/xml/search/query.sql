select
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id,
	smn_compras.smn_requisicion_cabecera.req_numero,
	smn_compras.smn_requisicion_cabecera.req_descripcion,
	smn_compras.smn_requisicion_cabecera.req_fecha_requerido,
	smn_compras.smn_requisicion_cabecera.req_fecha_registro,
	case
		when smn_compras.smn_requisicion_cabecera.req_estatus='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='GE' then '${lbl:b_generated}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='AP' then '${lbl:b_aprobated}'
	end as req_estatus,
	smn_compras.smn_tipo_documento.tdc_codigo||'-'||smn_compras.smn_tipo_documento.tdc_nombre as smn_tipo_documento_id,
	smn_compras.smn_documentos.dcc_codigo|| ' - ' || smn_compras.smn_documentos.dcc_nombre as smn_documento_id,
	case
		when smn_compras.smn_requisicion_cabecera.req_estatus_ruta='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_requisicion_cabecera.req_estatus_ruta='AP' then '${lbl:b_aprobated}'
	end as req_estatus_ruta,
	smn_base.smn_entidades.ent_codigo|| ' - ' || smn_base.smn_entidades.ent_descripcion_corta  as smn_entidad_id,
	smn_base.smn_sucursales.suc_codigo|| ' - ' || smn_base.smn_sucursales.suc_nombre as smn_sucursal_id
from 
	smn_compras.smn_requisicion_cabecera
	left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_requisicion_cabecera.smn_documento_id
	left outer join smn_compras.smn_tipo_documento on smn_compras.smn_tipo_documento.smn_tipo_documento_id = smn_compras.smn_documentos.smn_tipo_documento_id
	left outer join smn_base.smn_entidades on smn_base.smn_entidades.smn_entidades_id = smn_compras.smn_requisicion_cabecera.smn_entidad_id
	left outer join smn_base.smn_sucursales on smn_base.smn_sucursales.smn_sucursales_id = smn_compras.smn_requisicion_cabecera.smn_sucursal_id
where
	smn_requisicion_cabecera_id = ${fld:id}
