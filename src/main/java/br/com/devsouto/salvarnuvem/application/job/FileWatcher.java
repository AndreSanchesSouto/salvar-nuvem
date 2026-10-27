package br.com.devsouto.salvarnuvem.application.job;

import java.nio.file.*;

public class FileWatcher {

    public static void run(Path folder) throws Exception {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {

            // Registra a pasta informando os eventos de interesse.
            folder.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            System.out.println("Monitorando: " + folder.toAbsolutePath());

            while (true) {
                System.out.println("\n\n");
                final WatchKey key = watcher.take(); // bloqueia ate haver evento

                for (WatchEvent<?> evento : key.pollEvents()) {
                    WatchEvent.Kind<?> tipo = evento.kind();

                    final Path nome = (Path) evento.context();
                    final Path path = folder.resolve(nome);

                    System.out.println("nome: " + nome.getFileName());
                    System.out.println("caminho: " + path);
                    System.out.println("evento: " + tipo.name());

                    if (tipo == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("Arquivo editado: " + path);
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
