select
		smn_compras.smn_tipo_documento.smn_tipo_documento_id,
	${field}
from
	smn_compras.smn_tipo_documento
where
		smn_compras.smn_tipo_documento.smn_tipo_documento_id is not null
	${filter}
	
	
