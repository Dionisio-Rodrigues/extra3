public class File extends BaseFile {
    public String data;

    public File() {
        this.type = 1;
    }

    public File(String name) {
        this.name = name;
        this.type = 1;
    }

    @Override
    public Object getData(){
        return this.data;
    }
}
