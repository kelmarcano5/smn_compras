select smn_compras.smn_roles.smn_roles_id as id, 
case
when smn_compras.smn_roles.rol_tipo = 'SO' then '${lbl:b_solicitante}'
when smn_compras.smn_roles.rol_tipo = 'AP' then '${lbl:b_services}'
end as item from smn_compras.smn_roles