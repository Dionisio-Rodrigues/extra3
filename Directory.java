import java.util.HashMap;

public class Directory extends BaseFile {
    public HashMap<String, Integer> data;

    public Directory() {
        this.type = 2;
        data = new HashMap<>();
    }

    public Directory(String name) {
        this.name = name;
        this.type = 2;
        data = new HashMap<>();
    }

    public Integer getFile(String name) {
        if (data.containsKey(name))
            return data.get(name);
        return null;
    }

    public void addFile(String name, int index) {
        this.data.put(name, index);
    }

    @Override
    public Object getData(){
        return this.data;
    }
}
