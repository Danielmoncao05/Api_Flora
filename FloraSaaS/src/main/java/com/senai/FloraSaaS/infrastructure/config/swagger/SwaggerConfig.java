package com.senai.FloraSaaS.infrastructure.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI floraSaaSOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flora SaaS - Core API & Intelligence")
                        .description("API central da plataforma Flora SaaS, projetada para orquestrar a experiência de cultivo residencial inteligente. " +
                                "Atua como o **Cérebro Central** do ecossistema Flora. Esta é a API Principal (Core) responsável por orquestrar toda a lógica de negócios, funcionando como o *hub* de decisão que conecta as extremidades do sistema:\n\n" +
                                "1.  **Consumo de IoT**: Recebe e processa o fluxo contínuo de telemetria vindo da **API de IoT** (camada de hardware/sensores).\n" +
                                "2.  **Integração com IA**: Submete os dados ambientais à **API de Inteligência Artificial** para obter diagnósticos preditivos.\n" +
                                "3.  **Gestão Compartilhada**: Centraliza o controle de acesso e sincroniza as ações entre usuários em ambientes colaborativos.\n\n" +

                                "**Módulos Principais:**\n" +
                                "* **Ambientes Compartilhados**: Arquitetura flexível que permite o vínculo de múltiplos clientes a um único ambiente. Ideal para cooperativas, famílias ou gestão de espaços comuns, com sincronização de ações entre os usuários.\n" +
                                "* **Flora AI Analytics**: Módulo de inteligência que consome históricos de sensores para gerar diagnósticos de saúde e recomendações de manejo (via OpenAI Integration).\n" +
                                "* **Monitoramento 'Live State'**: Dashboard em tempo real que reflete instantaneamente o estado atual da estufa e as mudanças de estado para todos os usuários conectados ao ambiente compartilhado.\n" +
                                "* **Administração de Contas**: Gestão de ciclo de vida de usuários e controle de acesso aos recursos da plataforma.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento Flora")
                                .email("dev-core@florasaas.com")

                        ));
    }
}