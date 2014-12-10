package hidden.indev0r.game.debug;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by MrDeathJockey on 14/12/10.
 */
public class Util {

    public static void copyDirectory(final Path origin, final Path target) throws IOException {

        Files.walkFileTree(origin, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = origin.relativize(file);
                Path targetFile = target.resolve(relative);
                if (!Files.exists(targetFile.getParent()))
                    Files.createDirectory(targetFile.getParent());
                Files.copy(file, targetFile);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                exc.printStackTrace();
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Path relative = origin.relativize(dir);
                Path targetDir = target.resolve(relative);
                if (!Files.exists(targetDir))
                    Files.createDirectory(targetDir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void removeDirectory(final Path directory, final boolean emptyOnly) throws IOException {
        if(!Files.exists(directory)) return;
        Files.walkFileTree(directory, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                file = FileSystems.getDefault().getPath(file.toAbsolutePath().toString());
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                exc.printStackTrace();
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if(dir.equals(directory) && emptyOnly)
                    return FileVisitResult.CONTINUE;

                dir = FileSystems.getDefault().getPath(dir.toAbsolutePath().toString());
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
