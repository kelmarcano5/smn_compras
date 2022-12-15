select 
	smn_base.smn_monedas.smn_monedas_id as ofe_moneda_id_ref 
from   
	smn_base.smn_monedas 
where  
	upper(smn_base.smn_monedas.mon_nombre) = upper(${fld:ofe_moneda_id_desc})
