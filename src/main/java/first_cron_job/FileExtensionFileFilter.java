package first_cron_job;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by sousaJ on 15/10/2020
 * in package - first_cron_job
 **/
public class FileExtensionFileFilter implements FileFilter {

    private String extension;

    public FileExtensionFileFilter(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File file) {
        return file.isFile() && (file.getName().toLowerCase().indexOf(extension) > 0);
    }
}
