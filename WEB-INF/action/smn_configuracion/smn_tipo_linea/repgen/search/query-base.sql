select
		smn_compras.smn_tipo_linea.smn_tipo_linea_id,
	${field}
from
	smn_compras.smn_tipo_linea
where
		smn_compras.smn_tipo_linea.smn_tipo_linea_id is not null
	${filter}
	
	
