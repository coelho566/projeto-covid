package com.unicesumar.covid.controller;

import com.unicesumar.covid.model.DayDeath;
import com.unicesumar.covid.service.CovidService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CovidController {

    private CovidService covidService;

    public CovidService getService() {
        covidService = new CovidService();
        return covidService;
    }

    public void menu() {

        Scanner menu = new Scanner(System.in);
        int opcao;

        do {
            System.out.print("\n##-- Casos de COVID-19 em Curitiba --##\n\n");
            System.out.print("|-----------------------------------------|\n");
            System.out.print("| Opção 1 - Busca todos os casos          |\n");
            System.out.print("| Opção 2 - Dias que mais tiveram óbitos  |\n");
            System.out.print("| Opção 3 - Média de idades dos óbitos    |\n");
            System.out.print("| Opção 4 - Idade mais atingida           |\n");
            System.out.print("| Opção 5 - Sair                          |\n");
            System.out.print("|-----------------------------------------|\n");

            System.out.print("Digite uma opção: ");

            opcao = menu.nextInt();
            switch (opcao) {
                case 1:

                    getService().searchDataByAge("")
                            .forEach(System.out::println);

                    break;

                case 2:
                     getService().getDayDeath()
                             .forEach(System.out::println);

                    break;

                case 3:
                    menuAverage();
                    break;

                case 4:
                    menuMode();
                    break;
                default:
                    System.out.print("\nOpção Inválida!");
                    break;
            }
        } while (opcao != 5);
    }

    private void menuAverage() {

        Scanner menu = new Scanner(System.in);
        int opcao;

        do {
            System.out.print("\n##--------  Idade mais atingida  --------##\n");
            System.out.print("|-----------------------------------------|\n");
            System.out.print("| Opção 1 - Média de casos sexo masculino |\n");
            System.out.print("| Opção 2 - Média de casos sexo feminino  |\n");
            System.out.print("| Opção 3 - Média de todos                |\n");
            System.out.print("| Opção 4 - Voltar                        |\n");
            System.out.print("|-----------------------------------------|\n");
            System.out.print("Digite uma opção: ");

            opcao = menu.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\nMédia de idade entre homens é " + getService().getAverage("M") + " anos");
                    break;
                case 2:
                    System.out.println("\nMédia de idade entre mulheres é " + getService().getAverage("F") + " anos");
                    break;
                case 3:
                    System.out.println("\nMédia de idade geral é " + getService().getAverage("") + " anos");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 4);
    }

    private void menuMode() {

        Scanner menu = new Scanner(System.in);
        int opcao;

        do {
            System.out.print("\n4##---- Idade mais atingida  ----##\n\n");
            System.out.print("|--------------------------------|\n");
            System.out.print("| Opção 1 - Sexo masculino       |\n");
            System.out.print("| Opção 2 - Sexo feminino        |\n");
            System.out.print("| Opção 3 - Todos                |\n");
            System.out.print("| Opção 4 - Voltar               |\n");
            System.out.print("|--------------------------------|\n");
            System.out.print("Digite uma opção: ");

            opcao = menu.nextInt();

            List<Long> list = new ArrayList<>();
            switch (opcao) {
                case 1:

                    list = getService().getMode("M");
                    System.out.println("\nIdade mais atingida entre homens: " + list.get(0) + " anos");
                    System.out.println("Numeros de casos: " + list.get(1));

                    break;
                case 2:

                    list = getService().getMode("F");
                    System.out.println("\nIdade mais atingida entre mulheres: " + list.get(0));
                    System.out.println("Numeros de casos: " + list.get(1));

                    break;
                case 3:

                    list = getService().getMode("");
                    System.out.println("\nIdade mais atingida: " + list.get(0));
                    System.out.println("Numeros de casos: " + list.get(1));

                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 4);
    }

}
