select
		smn_compras.smn_regla.smn_regla_id,
	${field}
from
	smn_compras.smn_regla
where
		smn_compras.smn_regla.smn_regla_id is not null
	${filter}
	
	
