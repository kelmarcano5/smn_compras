SELECT 
	smn_requisicion_cabecera_id, 
	req_cabecera_version
 FROM 
 	smn_compras.smn_requisicion_cabecera 
 WHERE 
 	smn_cabecera_version_id = ${fld:smn_requisicion_cabecera_id}
 AND 
 	req_cabecera_version = ${fld:req_cabecera_version}