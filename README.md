# Web Pedidos
> Gerenciamento de pedidos para vendas.

Sistema de gerenciamento de pedidos de vendas acessível via web. Desenvolvido com tecnologias Java: JSF, PrimeFaces, JPA (Hibernate), Spring Security, Java Mail e log4j. Planejado para uso com banco de dados MySQL.

## O que faz?

Cadastra produtos com código, usuários, clientes, endereços, endereços de entrega, vendedores, categorias. Também emite pedidos por e-mail, mantém estados dos pedidos e gerencia permissões de acesso.

## O que não faz?

Não recebe pagamentos e não permite acesso direto do cliente.

## Instalação

* Clone o repositório para a sua máquina.
* Instale o servidor Apache Tomcat 8.0 ou superior.
* Instale o servidor mysql, crie um database novo e execute nele os scripts extra/install/sql_structure.sql e extra/install/minimal_sql_data.sql.
* Na pasta src/main/resources, remover a extensão .example dos arquivos mail.properties e META-INF/persistence.xml e configurá-los com as informações de database e envio de e-mails.
* Compile o projeto e faça o deploy no servidor Apache. O usuário padrão para primeiro acesso é admin@admin.com com a senha admin.

## Histórico
* 0.0.1
    * Em desenvolvimento

## Mais informações

Distributed under the GNU GENERAL PUBLIC LICENSE Version 3 license. See ``LICENSE`` for more information.

[https://github.com/marcelothebuilder/](https://github.com/marcelothebuilder/)

