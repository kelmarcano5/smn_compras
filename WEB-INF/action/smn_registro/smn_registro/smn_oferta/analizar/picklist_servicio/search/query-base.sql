select	
	*
from
	smn_compras.smn_servicio 
where
	smn_servicio_id is not null
	${filter}