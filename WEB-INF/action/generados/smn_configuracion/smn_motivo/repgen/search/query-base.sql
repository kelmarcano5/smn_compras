select
		smn_compras.smn_motivo.smn_motivo_id,
	${field}
from
	smn_compras.smn_motivo
where
		smn_compras.smn_motivo.smn_motivo_id is not null
	${filter}
	
	
