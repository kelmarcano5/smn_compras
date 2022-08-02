select
	(select smn_base.smn_usuarios.usr_usuario|| ' - ' || smn_base.smn_usuarios.usr_nombres from  smn_base.smn_usuarios where smn_base.smn_usuarios.smn_usuarios_id is not null  and smn_base.smn_usuarios.smn_usuarios_id=smn_compras.smn_lineas.smn_lineas_id) as smn_usuarios_id,
	smn_compras.smn_rel_usuarios_linea.*
from 
	smn_compras.smn_rel_usuarios_linea,
	smn_compras.smn_lineas
where
	smn_compras.smn_rel_usuarios_linea.smn_lineas_id=smn_compras.smn_lineas.smn_lineas_id and 
	smn_compras.smn_lineas.smn_lineas_id=${fld:id}
