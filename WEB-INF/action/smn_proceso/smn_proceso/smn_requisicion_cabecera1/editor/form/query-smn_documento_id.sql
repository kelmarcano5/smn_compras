select distinct smn_compras.smn_documentos.smn_documentos_id as id, smn_compras.smn_documentos.dcc_codigo|| ' - ' || smn_compras.smn_documentos.dcc_nombre as item from smn_compras.smn_documentos
left outer join smn_compras.smn_rel_usuario_documento on smn_compras.smn_rel_usuario_documento.smn_documento_id = smn_compras.smn_documentos.smn_documentos_id
left outer join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_usuarios_id = smn_compras.smn_rel_usuario_documento.smn_usuario_id
left outer join smn_seguridad.s_user on smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
INNER JOIN smn_compras.smn_tipo_documento on smn_compras.smn_tipo_documento.smn_tipo_documento_id = smn_compras.smn_documentos.smn_tipo_documento_id
where smn_compras.smn_tipo_documento.tdc_codigo like '%RQ%'