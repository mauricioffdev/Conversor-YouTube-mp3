package com.mauricioffdev;

import com.mauricioffdev.service.ConverterService;

import java.util.Scanner;

// Mantive essa classe como recordação do projeto antes de virar Spring Boot
public class AntigoMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConverterService converter = new ConverterService();

        System.out.println("=========================================");
        System.out.println("🎸 Java Tube Converter - By Mauricioffdev");
        System.out.println("=========================================");
        System.out.println("Qualidade padrão: 192 kbps");

        while (true) {
            System.out.print("\nCole a URL do vídeo (ou 'sair' para fechar): ");
            String url = scanner.nextLine().trim();

            if (url.trim().equalsIgnoreCase("sair")) {
                break;
            }

            if (!url.isEmpty()) {
                String quality = "192K"; // Valor padrão

                System.out.println("Escolha a qualidade:");
                System.out.println("[1] 128 kbps");
                System.out.println("[2] 192 kbps");
                System.out.println("[3] 256 kbps");
                System.out.print("Opção: ");

                String option = scanner.nextLine().trim();

                switch (option) {
                    case "1":
                        quality = "128K";
                        break;
                    case "3":
                        quality = "256K";
                        break;
                    // Caso seja qualquer outra coisa, mantém 192K
                }

                System.out.println("🚀 Baixando em " + quality + "...");
                converter.downloadAndConvert(url, quality);
            }
        } // <--- O loop while fecha aqui

        // Tudo aqui fora só acontece depois do 'break'
        scanner.close();
        System.out.println("Programa encerrado.");
    }
}