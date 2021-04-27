Feature: Adquirir um novo cartão
  Como um usuário do metrô
  Eu quero adquirir um novo cartão
  Para então viajar entre as estações

  Background: Lista de contas e zonas
    Given que o sistema possui contas previamente cadastradas
    And que o sistema possui zonas previamente cadastradas

  Scenario Outline: Adquirir um novo cartão de zona
    Given que eu possuo uma conta de número <accountId>
    When eu efetuar a solicitação de um novo cartão de zona <zone>
    Then eu espero receber um novo cartão da zona solicitada

    Examples:
      | accountId | zone |
      | 1         | "A"  |
      | 2         | "B"  |