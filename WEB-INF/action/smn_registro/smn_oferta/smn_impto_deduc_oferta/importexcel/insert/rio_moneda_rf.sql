select 
	smn_base.smn_monedas.smn_monedas_id as rio_moneda_rf_ref 
from   
	smn_base.smn_monedas 
where  
	upper(smn_base.smn_monedas.mon_nombre) = upper(${fld:rio_moneda_rf_desc})
