/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  luiz.barcellos
 * Created: 25/08/2017
 */

--25/08/2017
-- TABELA ACEITARÁ APENAS UM NÚMERO DE OTC.
ALTER TABLE `controladoria`.`produtor_baixa_credito` 
ADD UNIQUE INDEX `otc_UNIQUE` (`otc` ASC);

-- CRIA PROCEDURE QUE VERIFICA SALDO
DROP procedure IF EXISTS `icms_credito_disponivel`;

DELIMITER $$

CREATE PROCEDURE `icms_credito_disponivel`(in x int, in y double, in xOtc bigint)
begin

declare xRecebido double default 0.00;
declare xLiberado double default 0.00;


if (xOtc is null) then
SELECT sum(valor) into xRecebido FROM controladoria.produtor_baixa_credito
WHERE id = x; else
SELECT sum(valor) into xRecebido FROM controladoria.produtor_baixa_credito
WHERE id = x and otc != xOtc;
end if;

if (xRecebido is null) then 
set xRecebido = 0.00;

end if;

end $$

delimiter ;


-- CRIA TRIGGER QUE CHAMA PROCEDURE PARA VERIFICAR SALDO
DROP TRIGGER IF EXISTS `controladoria`.`verifica_saldo`;

DELIMITER $$
CREATE DEFINER = CURRENT_USER TRIGGER `verifica_saldo` BEFORE INSERT ON `produtor_baixa_credito` FOR EACH ROW
BEGIN

call controladoria.icms_credito_disponivel(new.id,new.valor, null);

END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `controladoria`.`verifica_saldo_U`;

DELIMITER $$
USE `controladoria`$$
CREATE DEFINER = CURRENT_USER TRIGGER `verifica_saldo_U` BEFORE UPDATE ON `produtor_baixa_credito` FOR EACH ROW
BEGIN

call controladoria.icms_credito_disponivel(new.id, new.valor, old.otc);

END$$
DELIMITER ;

-- CRIA VIEW ICMS disponível
drop view if exists `rel_credito_disp`;

create view `rel_credito_disp` as

SELECT A.ID, 
C.inscri_est, 
P.nome, 
case when A.credito_liberado is null then 0.00 else A.credito_liberado end as credito_liberado,
case when D.valor is null then 0.00 else D.valor end as credito_recebido,
case when D.VALOR = A.credito_liberado then TRUE else FALSE end as quitado,
a.processo, 
case when d.qtd is null then 0 else d.qtd end as qtd

FROM controladoria.produtor_icms A
LEFT JOIN controladoria.cad_pessoas P on (P.id = A.id_produtor)
LEFT JOIN controladoria.icms_estabelecimentos C on (C.id = A.id_estabelecimento and C.id_pessoa = a.id_produtor)
LEFT JOIN (SELECT id, otc, sum(valor) as valor, count(id) as qtd
			FROM controladoria.produtor_baixa_credito
			group by id, otc) D ON (D.id = A.id)