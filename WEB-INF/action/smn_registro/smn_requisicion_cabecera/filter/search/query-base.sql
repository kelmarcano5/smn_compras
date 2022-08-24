select distinct
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
		smn_compras.smn_requisicion_cabecera.req_cabecera_version,
	--smn_base.smn_auxiliar.aux_codigo ||'-'|| smn_base.smn_auxiliar.aux_descripcion as smn_auxiliar_id,
	smn_compras.smn_requisicion_cabecera.req_numero,
	smn_compras.smn_requisicion_cabecera.req_descripcion,
	smn_compras.smn_requisicion_cabecera.req_fecha_requerido,
	smn_compras.smn_requisicion_cabecera.req_fecha_registro,
	case
		when smn_compras.smn_requisicion_cabecera.req_estatus='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='GE' then '${lbl:b_generated}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='AP' then '${lbl:b_aprobated}'
		when smn_compras.smn_requisicion_cabecera.req_estatus='VE' then '${lbl:b_versioned}'
	end as req_estatus,
	smn_compras.smn_tipo_documento.tdc_codigo||'-'||smn_compras.smn_tipo_documento.tdc_nombre as smn_tipo_documento_id,
	smn_compras.smn_documentos.dcc_codigo|| ' - ' || smn_compras.smn_documentos.dcc_nombre as smn_documento_id,
	case
		when smn_compras.smn_requisicion_cabecera.req_estatus_ruta='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_requisicion_cabecera.req_estatus_ruta='AP' then '${lbl:b_aprobated}'
	end as req_estatus_ruta,
	smn_base.smn_entidades.ent_codigo|| ' - ' || smn_base.smn_entidades.ent_descripcion_corta  as smn_entidad_id,
	smn_base.smn_sucursales.suc_codigo|| ' - ' || smn_base.smn_sucursales.suc_nombre as smn_sucursal_id,
    smn_base.smn_entidades.smn_entidades_id as empresa,
    smn_base.smn_modulos.mod_codigo as smn_modulo_rf,
	smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id,
 	smn_compras.smn_documentos.dcc_recurrente,
 	CASE
		when smn_compras.smn_requisicion_cabecera.req_version_descripcion is null then 'Descripcion de la version sin definir' else smn_compras.smn_requisicion_cabecera.req_version_descripcion
	end as req_version_descripcion,
	smn_compras.smn_requisicion_cabecera.req_usuario
from
   smn_compras.smn_requisicion_cabecera
   left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_requisicion_cabecera.smn_documento_id
   left outer join smn_compras.smn_tipo_documento on smn_compras.smn_tipo_documento.smn_tipo_documento_id = smn_compras.smn_documentos.smn_tipo_documento_id
   left outer join smn_base.smn_entidades on smn_base.smn_entidades.smn_entidades_id = smn_compras.smn_requisicion_cabecera.smn_entidad_id
   left outer join smn_base.smn_sucursales on smn_base.smn_sucursales.smn_sucursales_id = smn_compras.smn_requisicion_cabecera.smn_sucursal_id
   left join smn_base.smn_modulos on smn_base.smn_modulos.smn_modulos_id = smn_compras.smn_requisicion_cabecera.smn_modulo_rf
   inner join smn_seguridad.s_user on smn_seguridad.s_user.userlogin = '${def:user}'
   inner join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
   inner join smn_compras.smn_roles on smn_compras.smn_roles.smn_usuarios_id = smn_base.smn_usuarios.smn_usuarios_id
where

	smn_requisicion_cabecera_id is not null --AND 
    --smn_compras.smn_requisicion_cabecera.req_estatus IN ('AP','GE', 'SO')
	${filter}
order by 
	req_fecha_registro 
desc
