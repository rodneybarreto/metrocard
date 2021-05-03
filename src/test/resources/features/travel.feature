Feature: Realizar uma viagem
  Como um usuário do metrô
  Eu quero usar meu cartão de viagens
  Para que eu possa viajar entre as estações

  Scenario: Impossibilidade de realizar uma viagem
    Given que eu possuo um cartão de zona A com identificador 1
    When eu tentar realizar uma viagem usando a tarifa "ZONE_B_UNIC"
    Then eu serei informado que cartão de zona A não é permitido na zona B

  Scenario: Debitar da conta quando o cartão for usado
    Given que eu possuo um cartão de zona B com identificador 2
    When eu tentar realizar uma viagem na zona B usando a tarifa "ZONE_B_UNIC"
    Then eu terei o valor da tarifa debitado da minha conta