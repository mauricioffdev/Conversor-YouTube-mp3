package com.mauricioffdev.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;

@Service
public class ConverterService {

    // Caminhos relativos para manter a portabilidade
    private static final String BIN_FOLDER = "bin";
    private static final String DOWNLOAD_FOLDER = "downloads";
    private static final String YT_DLP_EXE = "yt-dlp.exe";
    private static final String FFMPEG_EXE = "ffmpeg.exe";

    public void downloadAndConvert(String videoUrl, String quality) {
        try {
            // Garante que a pasta de downloads existe
            // Deixei um mp3 de exemplo
            File downloadDir = new File(DOWNLOAD_FOLDER);
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            // Resolve os caminhos absolutos baseados na raiz do projeto
            String ytDlpPath = Paths.get(BIN_FOLDER, YT_DLP_EXE).toAbsolutePath().toString();
            String ffmpegPath = Paths.get(BIN_FOLDER, FFMPEG_EXE).toAbsolutePath().toString();
            String outputTemplate = Paths.get(DOWNLOAD_FOLDER, "%(title)s.%(ext)s").toAbsolutePath().toString();

            ProcessBuilder builder = new ProcessBuilder(
                    ytDlpPath,
                    "-x",
                    "--audio-format", "mp3",
                    "--audio-quality", quality, // <--- AQUI entra a variável (ex: "256K")
                    "--ffmpeg-location", ffmpegPath,
                    "-o", outputTemplate,
                    videoUrl
            );

            // Redireciona o erro para a saída padrão para vermos o log do yt-dlp
            builder.redirectErrorStream(true);

            Process process = builder.start();

            // Lê a saída do console do yt-dlp e imprime no console do Java
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[yt-dlp]: " + line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("✅ Sucesso! Arquivo salvo em: " + DOWNLOAD_FOLDER);
            } else {
                System.err.println("❌ Erro durante o processo. Código: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}