SELECT 
    a.id AS id,
    a.sequencia AS sequencia,
    a.id_empresa AS id_empresa,
    a.id_capa AS id_capa,
    a.id_assunto AS id_assunto,
    a.nome_assunto AS nome_assunto,
    a.data_inicial AS data_inicial,
    a.data_final AS data_final,
    a.observacao AS observacao,
    a.data_inclusao AS data_inclusao,
    a.controle as controle,
    b.id_empresa as id_empresa,
    b.nome_empresa AS nome_empresa,
    b.nome_estabelecimento AS nome_estabelecimento,
    b.id_departamento AS id_departamento,
    b.nome_departamento AS nome_departamento,
    b.id_tipo AS id_tipo,
    b.nome_tipo AS nome_tipo,
    b.completa AS completa,
    b.id_estabelecimento AS id_estabelecimento,
    b.data_impressao AS data_impressao,
    b.id AS id_etiqueta,
    b.sequencia as etiqueta
FROM
    cad_arquivo_pastas_detalhe a
        LEFT JOIN
    cad_arquivo_pastas b ON (b.id_empresa = a.id_empresa
        AND a.id_departamento = b.id_departamento
        AND b.sequencia = a.sequencia)
        LEFT JOIN
    rel_arquivo_controle cc ON (cc.id_pasta = b.id)
WHERE
    a.controle = cc.controle
ORDER BY 3 , 2 , 14