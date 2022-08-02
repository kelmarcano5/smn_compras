select	
	smn_compras.smn_tipo_linea.smn_tipo_linea_id, 
	smn_compras.smn_tipo_linea.tlc_codigo as tlc_codigo_pl0,
	(select smn_compras.smn_tipo_linea.tlc_codigo|| ' - ' || smn_compras.smn_tipo_linea.tlc_nombre from  smn_compras.smn_tipo_linea where smn_compras.smn_tipo_linea.smn_tipo_linea_id is not null  and smn_compras.smn_tipo_linea.smn_tipo_linea_id=smn_compras.smn_lineas.smn_tipo_linea_id) as smn_tipo_linea_id,
	smn_compras.smn_lineas.*
from
	smn_compras.smn_tipo_linea,
	smn_compras.smn_lineas 
where
	smn_compras.smn_tipo_linea.smn_tipo_linea_id=smn_compras.smn_lineas.smn_tipo_linea_id 
 and 
	smn_lineas_id = ${fld:id}
