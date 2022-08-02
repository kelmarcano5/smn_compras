select
		smn_compras.smn_rel_usuario_documento.smn_rel_usuario_documento_id,
	 smn_base.smn_v_usuarios.usr_nombres||''|| smn_base.smn_v_usuarios.usr_apellidos as smn_usuario_id,
	 smn_compras.smn_documentos.dcc_codigo ||'-'|| smn_compras.smn_documentos.dcc_nombre as smn_documento_id
	
from
	smn_compras.smn_rel_usuario_documento
	left outer join smn_base.smn_v_usuarios on smn_base.smn_v_usuarios.smn_usuarios_id = smn_compras.smn_rel_usuario_documento.smn_usuario_id
	left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_rel_usuario_documento.smn_documento_id

