select smn_base.smn_atributo_item.smn_atributo_item_id as id, smn_base.smn_atributo_item.ati_codigo ||' - '|| smn_base.smn_atributo_item.ati_nombre as item from smn_base.smn_atributo_item
left outer join smn_base.smn_rel_nivel_estruc_atributo on smn_base.smn_rel_nivel_estruc_atributo.smn_atributo_item_id = smn_base.smn_atributo_item.smn_atributo_item_id
left outer join smn_base.smn_nivel_estructura on smn_base.smn_nivel_estructura.smn_nivel_estructura_id = smn_base.smn_rel_nivel_estruc_atributo.smn_nivel_estructura_id
where smn_base.smn_nivel_estructura.smn_nivel_estructura_id=${fld:id} and smn_base.smn_rel_nivel_estruc_atributo.rna_secuencia=2