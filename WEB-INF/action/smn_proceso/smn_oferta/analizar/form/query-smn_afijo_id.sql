select 
	smn_base.smn_activo_fijo.smn_afijo_id as id, 
	smn_base.smn_activo_fijo.acf_codigo || ' - ' || smn_base.smn_activo_fijo.acf_nombre as item 
from 
	smn_base.smn_activo_fijo