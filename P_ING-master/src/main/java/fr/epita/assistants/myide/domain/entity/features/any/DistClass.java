package fr.epita.assistants.myide.domain.entity.features.any;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.java.log.Logged;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DistClass extends FeatureClass implements Logged {

    private static DistClass instance;
    private DistClass() { super(Mandatory.Features.Any.DIST); }

    public static DistClass getInstance(){
        if (instance == null){
            instance = new DistClass();
        }
        return instance;
    }

    public ExecutionReport distFeature (Project project) throws IOException {
        File projectFile = new File(project.getRootNode().getPath().toString());
        var cleanupReport = CleanupClass.getInstance().cleanupFeature(project);
        if (!cleanupReport.isSuccess()){
            logger().info("CleanUp Failed.");
            return cleanupReport;
        }
        // try with resources - creating outputstream and ZipOutputSttream
        String zipPath = projectFile.getParentFile().getPath().concat("/").concat(projectFile.getName()).concat(".zip");
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            Path sourcePath = Paths.get(project.getRootNode().getPath().toAbsolutePath().toString());
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
                    if (!sourcePath.equals(dir)) {
                        zos.putNextEntry(new ZipEntry(projectFile.getName() + '/' + sourcePath.relativize(dir) + "/"));
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(projectFile.getName() + '/' + sourcePath.relativize(file).toString()));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return () -> false;
        }
        return () -> true;
    }

    @SneakyThrows
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        return distFeature(project);
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.DIST;
    }
}
