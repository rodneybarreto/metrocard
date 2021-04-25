  Feature: Adquirir um novo cartão

  Como um usuário do metrô
  Eu quero adquirir um novo cartão
  Para então viajar entre as estações

  Scenario Outline: Adquirir um novo cartão de zona
    Given que o usuário possue uma conta de número <accountId>
    When o usuário efetuar a solicitação de um novo cartão de zona <zone>
    Then o usuário receberá um novo cartão da zona solicitada

    Examples:
      | accountId | zone |
      | 1         | "A"  |
      | 2         | "B"  |