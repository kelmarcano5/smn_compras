select 
	smn_base.smn_item.smn_item_id as ofe_item_alter_prov_ref 
from   
	smn_base.smn_item 
where  
	upper(smn_base.smn_item.itm_nombre) = upper(${fld:ofe_item_alter_prov_desc})
