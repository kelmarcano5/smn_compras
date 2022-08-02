select
	${smn:sql_columns}
from
	${smn:schema}.${smn:table} 
where
	${smn:schema}.${smn:table}.${smn:table}_id = ${fld:id}
