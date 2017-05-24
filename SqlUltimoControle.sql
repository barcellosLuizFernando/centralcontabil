SELECT 
    aa.id AS id_pasta, MAX(a.controle) AS controle, max(a.id) as maior_id
FROM
    cad_arquivo_pastas_detalhe a
        LEFT JOIN
    cad_arquivo_pastas aa ON (aa.id_empresa = a.id_empresa
        AND aa.id_departamento = a.id_departamento
        AND aa.sequencia = a.sequencia)
GROUP BY 1