SELECT
	a.nome_empresa AS nome_empresa,
    a.id_empresa as id_empresa,
    a.nome_estabelecimento AS nome_estabelecimento,
    a.id_departamento AS id_departamento,
    a.nome_departamento AS nome_departamento,
    a.id_tipo AS id_tipo,
    a.nome_tipo AS nome_tipo,
    a.completa AS completa,
    a.id_estabelecimento AS id_estabelecimento,
    a.data_impressao AS data_impressao,
    a.id AS id_etiqueta,
    a.sequencia as sequencia,
    aa.id AS id_1,
    aa.nome_assunto AS nome_assunto_1,
    aa.data_inicial AS data_inicial_1,
    aa.data_final AS data_final_1,
    aa2.id AS id_2,
    aa2.nome_assunto AS nome_assunto_2,
    aa2.data_inicial AS data_inicial_2,
    aa2.data_final AS data_final_2,
    aa3.id AS id_3,
    aa3.nome_assunto AS nome_assunto_3,
    aa3.data_inicial AS data_inicial_3,
    aa3.data_final AS data_final_3,
    aa4.id AS id_4,
    aa4.nome_assunto AS nome_assunto_4,
    aa4.data_inicial AS data_inicial_4,
    aa4.data_final AS data_final_4
FROM
    cad_arquivo_pastas a
    
		LEFT JOIN rel_arquivo_controle c ON (c.id_pasta = a.id)
    
        /* PESQUISA ASSUNTO 1*/
        LEFT JOIN cad_arquivo_pastas_detalhe aa ON (aa.sequencia = a.sequencia
        AND aa.id_empresa = a.id_empresa
        AND aa.id_departamento = a.id_departamento
        AND aa.id = c.maior_id)
		
        /*PESQUISA ASSUNTO 2*/
        LEFT JOIN cad_arquivo_pastas_detalhe aa2 ON (aa2.sequencia = a.sequencia
        AND aa2.id_empresa = a.id_empresa
        AND aa2.id_departamento = a.id_departamento
        AND aa2.id = c.maior_id-1)
		
        /*PESQUISA ASSUNTO 3*/
        LEFT JOIN cad_arquivo_pastas_detalhe aa3 ON (aa3.sequencia = a.sequencia
        AND aa3.id_empresa = a.id_empresa
        AND aa3.id_departamento = a.id_departamento
        AND aa3.id = c.maior_id-2)
        
         /*PESQUISA ASSUNTO 4*/
        LEFT JOIN cad_arquivo_pastas_detalhe aa4 ON (aa4.sequencia = a.sequencia
        AND aa4.id_empresa = a.id_empresa
        AND aa4.id_departamento = a.id_departamento
        AND aa4.id = c.maior_id-3)
        
/*WHERE
    a.id = 7 AND aa.controle = c.controle*/