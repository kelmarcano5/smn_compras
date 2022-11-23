select	
	smn_base.smn_activo_fijo.*
from
	smn_base.smn_activo_fijo 
where
	smn_afijo_id is not null
	${filter}