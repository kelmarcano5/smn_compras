select
	${smn:schema}.${smn:table}.${smn:table}_id,
	${field}
from
	${smn:schema}.${smn:table}
where
    ${smn:schema}.${smn:table}.${smn:table}_id is not null
    ${filter}


