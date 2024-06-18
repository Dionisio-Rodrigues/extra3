import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class FileSystemSimulator {
    public ArrayList<BaseFile> disk;
    public Directory root;
    public String journaling;

    public FileSystemSimulator() {
        this.root = new Directory("root");
        this.disk = new ArrayList<>();
        disk.add(root);
    }

    public File createFile(String path) {
        ArrayList<String> arrayPath = new ArrayList<>(Arrays.asList(path.split("/")));
        String fileName = arrayPath.remove(arrayPath.size()-1);
        
        Directory dir = getDirectory(arrayPath, root);
        if (dir == null) return null;

        File newFile = new File(fileName);

        this.disk.add(newFile);
        dir.addFile(newFile.name, this.disk.indexOf(newFile));

        this.journaling += "touch " + path + "\n";

        return newFile;
    }

    public Directory createDir(String path) {
        ArrayList<String> arrayPath = new ArrayList<>(Arrays.asList(path.split("/")));
        String dirName = arrayPath.remove(arrayPath.size()-1);
        
        Directory dir = getDirectory(arrayPath, root);
        if (dir == null) return null;

        Directory newDir = new Directory(dirName);

        this.disk.add(newDir);
        dir.addFile(newDir.name, this.disk.indexOf(newDir));

        this.journaling += "mkdir " + path + "\n";

        return newDir;
    }

    public void renameFile(String path, String name) {
        BaseFile file = findBaseFile(path);
        if (file == null) return;
        ArrayList<String> spltPath = new ArrayList<>(Arrays.asList(path.split("/")));
        spltPath.remove(spltPath.size()-1);
        Directory dir = getDirectory(spltPath, root);
        Integer value = dir.data.get(file.name);
        dir.data.remove(file.name);
        file.name = name;
        dir.addFile(file.name, value);
        this.journaling += "rnm " + path + " " + name +"\n";
    }

    public String listDirectory(String path) {
        Directory dir = findDirectory(path);
        Set<String> keys = dir.data.keySet();
        ArrayList<String> keysList = new ArrayList<>(keys);

        String result = "";
        for (String itemName : keysList) result += itemName + "\n";

        this.journaling += "ls " + path + "\n";

        return result;
    }

    public void copyFile(String path, String newFilePath) {
        File origin = findFile(path);

        if (origin == null) {
            System.out.println("");
            return;
        }

        File destiny = findFile(newFilePath);

        if (destiny == null) {
            if (nameExist(newFilePath)) return;

            destiny = createFile(newFilePath);
        }

        destiny.data = origin.data;

        this.journaling += "cp " + path + " "+ newFilePath +"\n";
    }

    public void removeFile(String path) {
        File file = findFile(path);

        if (file == null) return;

        this.disk.remove(file);
        ArrayList<String> listPath = new ArrayList<>(Arrays.asList(path.split("/")));
        String fileName = listPath.remove(listPath.size()-1);
        getDirectory(listPath, root).data.remove(fileName);

        this.journaling += "rm " + path + "\n";
    }

    public void removeDir(String path) {
        Directory dir = findDirectory(path);

        if (dir == null) return;

        this.disk.remove(dir);
        ArrayList<String> listPath = new ArrayList<>(Arrays.asList(path.split("/")));
        String dirName = listPath.remove(listPath.size()-1);
        getDirectory(listPath, root).data.remove(dirName);

        this.journaling += "rmDir " + path + "\n";
    }

    public Directory findDirectory(String path, Directory dir) {
        ArrayList<String> pathList = new ArrayList<>(Arrays.asList(path.split("/")));
        return getDirectory(pathList, dir);
    }

    public Directory findDirectory(String path) {
        return findDirectory(path, this.root);
    }

    public File findFile(String path, Directory dir) {
        ArrayList<String> pathList = new ArrayList<>(Arrays.asList(path.split("/")));
        String fileName = pathList.remove(pathList.size()-1);
        dir = getDirectory(pathList, dir);
        if (dir == null) return null;
        try {
            File file = (File) this.disk.get(dir.getFile(fileName));
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean nameExist(String path) {
        return findDirectory(path) == null || findFile(path) == null;
    }

    public File findFile(String path) {
        return findFile(path, this.root);
    }

    private Directory getDirectory(ArrayList<String> path, Directory dir) {
        String dirName = null;

        if (path.size() == 0) return dir;

        if (dir == null) return null;
        
        try {
            dirName = path.remove(0);
            dir = (Directory) this.disk.get(dir.getFile(dirName));
        }catch(Exception e) {
            return null;
        }

        return getDirectory(path, dir);
    }

    public BaseFile findBaseFile(String path) {
        BaseFile baseFile = findFile(path);

        if (baseFile == null) baseFile = findDirectory(path);

        return  baseFile;
    }
}