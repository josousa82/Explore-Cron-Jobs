package first_cron_job;

/**
 * Created by sousaJ on 16/10/2020
 * in package - first_cron_job
 **/
public enum FileExtensionEnum {
    XML(".xml"),
    TXT(".txt"),
    JAVA(".java");

    private String fileExtension;

    FileExtensionEnum(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
