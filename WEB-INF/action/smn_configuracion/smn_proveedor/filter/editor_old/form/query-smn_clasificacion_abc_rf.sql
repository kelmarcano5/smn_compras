select smn_base.smn_clasificacion_abc.smn_clasificacion_abc_id as id,
smn_base.smn_clasificacion_abc.clf_codigo||'-'||smn_base.smn_clasificacion_abc.clf_descripcion as item 
from smn_base.smn_clasificacion_abc