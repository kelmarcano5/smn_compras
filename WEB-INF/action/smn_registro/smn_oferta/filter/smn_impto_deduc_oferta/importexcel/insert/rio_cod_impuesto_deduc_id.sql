select 
	smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id as rio_cod_impuesto_deduc_id_ref 
from   
	smn_base.smn_codigos_impuestos 
where  
	upper(smn_base.smn_codigos_impuestos.imp_descripcion) = upper(${fld:rio_cod_impuesto_deduc_id_desc})
