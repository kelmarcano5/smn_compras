select 
	smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id as rio_tasa_rf_ref 
from   
	smn_base.smn_tasas_de_cambio 
where  
	upper(smn_base.smn_tasas_de_cambio.tca_tasa_cambio) = upper(${fld:rio_tasa_rf_desc})
