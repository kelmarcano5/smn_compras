select
	smn_compras.smn_ruta.*
from 
	smn_compras.smn_ruta
where
	smn_ruta_id = ${fld:id}
