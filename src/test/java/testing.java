import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testing {

    public static void main(String[] a) {
        String path = "/";
        String[] args = {"set", "IkeVoodoo", "45"};
        List<String> paths = List.of("/", "/set/", "/set/group/");
        List<String> remaining = null;
        for (int i = 0; i < args.length; i++) {
            String testPath = path + args[i] + "/";
            for (String p : paths) {
                if (p.startsWith(testPath)) {
                    path = testPath;
                    break;
                }
            }
            if (!path.equals(testPath)) {
                remaining = Arrays.asList(args).subList(i, args.length);
                break;
            }
        }
        System.out.println(path + " | " + remaining);
    }

}
