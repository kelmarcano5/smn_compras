select smn_base.smn_documentos_generales.smn_documentos_generales_id as id, smn_base.smn_documentos_generales.dcg_codigo|| ' - ' || 
smn_base.smn_documentos_generales.dcg_descripcion as item 
from smn_base.smn_documentos_generales 