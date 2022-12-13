SELECT DISTINCT
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.req_cabecera_version,
--smn_base.smn_auxiliar.aux_codigo ||'-'|| smn_base.smn_auxiliar.aux_descripcion as smn_auxiliar_id,
	smn_compras.smn_requisicion_cabecera.req_numero,
	smn_compras.smn_requisicion_cabecera.req_descripcion,
	smn_compras.smn_requisicion_cabecera.req_fecha_requerido,
	smn_compras.smn_requisicion_cabecera.req_fecha_registro,
CASE
		
		WHEN smn_compras.smn_requisicion_cabecera.req_estatus = 'SO' THEN
		'${lbl:b_solicited}' 
		WHEN smn_compras.smn_requisicion_cabecera.req_estatus = 'GE' THEN
		'${lbl:b_generated}' 
		WHEN smn_compras.smn_requisicion_cabecera.req_estatus = 'AP' THEN
		'${lbl:b_aprobated}' 
		WHEN smn_compras.smn_requisicion_cabecera.req_estatus = 'VE' THEN
		'${lbl:b_versioned}' 
	END AS req_estatus,
	smn_compras.smn_requisicion_cabecera.req_estatus as estatus,
	smn_compras.smn_tipo_documento.tdc_codigo || '-' || smn_compras.smn_tipo_documento.tdc_nombre AS smn_tipo_documento_id,
	smn_compras.smn_documentos.dcc_codigo || ' - ' || smn_compras.smn_documentos.dcc_nombre AS smn_documento_id,
CASE
		
		WHEN smn_compras.smn_requisicion_cabecera.req_estatus_ruta = 'SO' THEN
		'${lbl:b_solicited}' 
		WHEN smn_compras.smn_requisicion_cabecera.req_estatus_ruta = 'AP' THEN
		'${lbl:b_aprobated}' 
	END AS req_estatus_ruta,
	smn_base.smn_entidades.ent_codigo || ' - ' || smn_base.smn_entidades.ent_descripcion_corta AS smn_entidad_id,
	smn_base.smn_sucursales.suc_codigo || ' - ' || smn_base.smn_sucursales.suc_nombre AS smn_sucursal_id,
	smn_base.smn_entidades.smn_entidades_id AS empresa,
	smn_base.smn_modulos.mod_codigo AS smn_modulo_rf,
	smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id,
	smn_compras.smn_documentos.dcc_recurrente,
	CASE
		when smn_compras.smn_requisicion_cabecera.req_version_descripcion is null then 'Descripcion de la version sin definir' else smn_compras.smn_requisicion_cabecera.req_version_descripcion
	end as req_version_descripcion,
	smn_compras.smn_requisicion_cabecera.req_usuario
FROM
	smn_compras.smn_requisicion_cabecera
	LEFT OUTER JOIN smn_compras.smn_documentos ON smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_requisicion_cabecera.smn_documento_id
	LEFT OUTER JOIN smn_compras.smn_tipo_documento ON smn_compras.smn_tipo_documento.smn_tipo_documento_id = smn_compras.smn_documentos.smn_tipo_documento_id
	LEFT OUTER JOIN smn_base.smn_entidades ON smn_base.smn_entidades.smn_entidades_id = smn_compras.smn_requisicion_cabecera.smn_entidad_id
	LEFT OUTER JOIN smn_base.smn_sucursales ON smn_base.smn_sucursales.smn_sucursales_id = smn_compras.smn_requisicion_cabecera.smn_sucursal_id
	LEFT JOIN smn_base.smn_modulos ON smn_base.smn_modulos.smn_modulos_id = smn_compras.smn_requisicion_cabecera.smn_modulo_rf
	INNER JOIN smn_seguridad.s_user ON smn_seguridad.s_user.userlogin = '${def:user}'
	INNER JOIN smn_base.smn_usuarios ON smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
	INNER JOIN smn_compras.smn_roles ON smn_compras.smn_roles.smn_usuarios_id = smn_base.smn_usuarios.smn_usuarios_id 
WHERE
	smn_compras.smn_requisicion_cabecera.req_estatus IN ('GE', 'SO') 
	AND smn_compras.smn_roles.rol_tipo IN ('SO') 
	AND smn_compras.smn_requisicion_cabecera.req_usuario = '${def:user}' 
ORDER BY
	req_fecha_registro DESC, req_numero DESC