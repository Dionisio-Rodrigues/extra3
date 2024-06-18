public abstract class BaseFile {
    public String name;
    protected int type;

    public int getTypeFile() {
        return this.type;
    }

    public abstract Object getData();
}
