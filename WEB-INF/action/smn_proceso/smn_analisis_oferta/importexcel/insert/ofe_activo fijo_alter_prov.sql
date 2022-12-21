select 
	smn_base.smn_activo_fijo.smn_activo_fijo_id as ofe_activo_fijo_alter_prov_ref 
from   
	smn_base.smn_activo_fijo 
where  
	upper(smn_base.smn_activo_fijo.acf_nombre) = upper(${fld:ofe_activo_fijo_alter_prov_desc})
