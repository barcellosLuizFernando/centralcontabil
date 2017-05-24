TYPE=VIEW
query=select `aa`.`id` AS `id_pasta`,max(`a`.`controle`) AS `controle`,max(`a`.`id`) AS `maior_id` from (`controladoria`.`cad_arquivo_pastas_detalhe` `a` left join `controladoria`.`cad_arquivo_pastas` `aa` on(((`aa`.`id_empresa` = `a`.`id_empresa`) and (`aa`.`id_departamento` = `a`.`id_departamento`) and (`aa`.`sequencia` = `a`.`sequencia`)))) group by `aa`.`id`
md5=62ddcd58148cc6a27dbc3634cc08f536
updatable=0
algorithm=0
definer_user=root
definer_host=localhost
suid=1
with_check_option=0
timestamp=2016-10-12 14:23:43
create-version=1
source=SELECT \n    aa.id AS id_pasta, MAX(a.controle) AS controle, max(a.id) as maior_id\nFROM\n    cad_arquivo_pastas_detalhe a\n        LEFT JOIN\n    cad_arquivo_pastas aa ON (aa.id_empresa = a.id_empresa\n        AND aa.id_departamento = a.id_departamento\n        AND aa.sequencia = a.sequencia)\nGROUP BY 1
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select `aa`.`id` AS `id_pasta`,max(`a`.`controle`) AS `controle`,max(`a`.`id`) AS `maior_id` from (`controladoria`.`cad_arquivo_pastas_detalhe` `a` left join `controladoria`.`cad_arquivo_pastas` `aa` on(((`aa`.`id_empresa` = `a`.`id_empresa`) and (`aa`.`id_departamento` = `a`.`id_departamento`) and (`aa`.`sequencia` = `a`.`sequencia`)))) group by `aa`.`id`
