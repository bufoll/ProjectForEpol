public class InfoFile {
    private String NameCopyFile;
    private String TimeCopyFile;

    public InfoFile(){};
    public InfoFile(String NameCopyFile, String TimeCopyFile){

        this.NameCopyFile = NameCopyFile;
        this.TimeCopyFile = TimeCopyFile;

    }
    @Override
    public String toString(){
        return "Name file: " + NameCopyFile + "\t" + "Date and time: " + TimeCopyFile;
    }
}
