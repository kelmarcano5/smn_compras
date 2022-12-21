select 
	smn_base.smn_descuento.smn_descuento_id as drc_porcentaje_ref 
from   
	smn_base.smn_descuento 
where  
	upper(drc_porcentaje) = upper(${fld:drc_porcentaje_desc})
