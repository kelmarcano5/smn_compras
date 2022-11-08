select 
	smn_base.smn_descuento.smn_descuento_id as rio_cod_descuento_ref 
from   
	smn_base.smn_descuento 
where  
	upper(smn_base.smn_descuento.dct_nombre) = upper(${fld:rio_cod_descuento_desc})
