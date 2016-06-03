# Web Pedidos
> Gerenciamento de pedidos para vendas.

Sistema de gerenciamento de pedidos de vendas acessível via web. Desenvolvido com tecnologias Java: JSF, PrimeFaces, JPA (Hibernate), Spring Security, Java Mail, Apache Velocity e log4j. Planejado para uso com banco de dados MySQL.

![alt text](https://raw.githubusercontent.com/marcelothebuilder/webpedidos/master/extra/docs/app_home.png "Imagem de demonstração do sistema")

## O que faz?

Cadastra produtos com código, usuários, clientes, endereços, endereços de entrega, vendedores, categorias. Também emite pedidos por e-mail, mantém estados dos pedidos e gerencia permissões de acesso.

## O que não faz?

Não recebe pagamentos e não permite acesso direto do cliente.

## Demonstração

Uma demonstração do sistema pode ser conferida online no link <http://webpedidos-marcelobuilder.rhcloud.com>. Você pode usar as credenciais abaixo para entrar no sistema, mas não poderá alterar os dados.

```
Usuário: demo@demo.com
Senha: demonstracao
```

A demonstração está hospedada em um servidor gratuito, oferecido pela RedHat, e entra em modo "hibernação" quando não existe tráfego. **Se a página demorar a carregar, por favor aguarde até que o serviço seja iniciado**, não demora mais que alguns segundos.

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

