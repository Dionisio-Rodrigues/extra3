import java.util.Scanner;

public class Extra3sh {
    public FileSystemSimulator fileSystemSimulator;

    public Extra3sh() {
        this.fileSystemSimulator = new FileSystemSimulator();
    }

    public void execCommand(String input) {
        String[] splited = input.split(" ");
        String command = null;
        
        command = splited[0];

        switch (command) {
            case "touch":
                this.fileSystemSimulator.createFile(splited[1]);
                break;
            
            case "cp":
                this.fileSystemSimulator.copyFile(splited[1], splited[2]);
                break;
            
            case "rm":
                this.fileSystemSimulator.removeFile(splited[1]);
                break;

            case "mkdir":
                this.fileSystemSimulator.createDir(splited[1]);
                break;
            
            case "rmdir":
                this.fileSystemSimulator.removeDir(splited[1]);
                break;
            
            case "rnm":
                this.fileSystemSimulator.renameFile(splited[1], splited[2]);
                break;
            
            case "ls":
                this.fileSystemSimulator.listDirectory(splited[1]);
                break;

            default:
                System.out.println(String.format("extra3sh: command not found: %s", command));
        }
    }

    public void init(){
        Scanner scan = new Scanner(System.in);
        while (true) {
            String command = scan.nextLine();
            if (command.equals("exit")) break;
            execCommand(command);
        }
    }
}
