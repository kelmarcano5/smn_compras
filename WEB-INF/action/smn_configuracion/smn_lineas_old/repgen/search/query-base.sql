select
		smn_compras.smn_lineas.smn_lineas_id,
	${field}
from
	smn_compras.smn_lineas
where
		smn_compras.smn_lineas.smn_lineas_id is not null
	${filter}
	
	
