select 
	smn_base.smn_transaccion_general.smn_transaccion_general_id as dcc_transaccion_id_ref 
from   
	smn_base.smn_transaccion_general 
where  
	upper(smn_base.smn_transaccion_general.trg_descripcion) = upper(${fld:dcc_transaccion_id_desc})
