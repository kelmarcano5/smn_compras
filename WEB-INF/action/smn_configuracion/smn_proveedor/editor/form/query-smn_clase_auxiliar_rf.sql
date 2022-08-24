select smn_base.smn_clase_auxiliar.smn_clase_auxiliar_id as id, 
smn_base.smn_clase_auxiliar.cla_codigo|| ' - ' ||smn_base.smn_clase_auxiliar.cla_nombre as item 
from smn_base.smn_clase_auxiliar
inner join smn_base.smn_auxiliar_clase on smn_base.smn_auxiliar_clase.smn_clase_auxiliar_id =  smn_base.smn_clase_auxiliar.smn_clase_auxiliar_id
GROUP BY id