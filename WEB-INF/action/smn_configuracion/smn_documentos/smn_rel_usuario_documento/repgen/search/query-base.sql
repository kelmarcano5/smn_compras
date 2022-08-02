select
		smn_compras.smn_rel_usuario_documento.smn_rel_usuario_documento_id,
	${field}
from
	smn_compras.smn_rel_usuario_documento
where
		smn_compras.smn_rel_usuario_documento.smn_rel_usuario_documento_id is not null
	${filter}
	
	
