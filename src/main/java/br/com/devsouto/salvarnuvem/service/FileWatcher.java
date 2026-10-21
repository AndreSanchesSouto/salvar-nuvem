package br.com.devsouto.salvarnuvem.service;

import java.nio.file.*;

public class FileWatcher {

    public static void main(String[] args) throws Exception {

        final Path pasta = Path.of("dados-cotacoes", "entrada");
        Files.createDirectories(pasta);

        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {

            // Registra a pasta informando os eventos de interesse.
            pasta.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            System.out.println("Monitorando: " + pasta.toAbsolutePath());
            System.out.println("(Ctrl+C para sair)\n");

            while (true) {
                final WatchKey key = watcher.take(); // bloqueia ate haver evento
                System.out.println(key);
                for (var evento : key.pollEvents()) {
                    var tipo = evento.kind();
                    final Path nome = (Path) evento.context();
                    if (!nome.getFileName().toString().endsWith("~")) {
                        System.out.println(tipo.name() + " -> " + nome);
                    }
                }

                // Obrigatorio: rearma a key para continuar recebendo eventos.
                boolean valida = key.reset();
                if (!valida) {
                    System.out.println("Pasta inacessivel, encerrando.");
                    break;
                }
            }
        }
    }
}
