package jm233333.io;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import jm233333.Director;
import jm233333.visualized.VDSInstantiation;

import javax.tools.JavaCompiler;
import com.sun.tools.javac.api.JavacTool;

public class ResourceReader {

    private boolean isAllFinished;

    public static boolean getCustomResources() {
        System.out.println("Start loading custom resources:");
        ResourceReader reader = new ResourceReader();
        reader.isAllFinished = true;
        reader.getMenuItems();
        final ArrayList<String> menuItems = Director.getInstance().getMenuItems();
        for (String menuItem : menuItems) {
            reader.getVDSInstantiations(menuItem);
        }
        if (reader.isAllFinished) {
            System.out.println("Finished. All custom resources (or corresponding default ones) are successfully loaded.\n");
        } else {
            System.out.println("Finished. Resource-loading process is partly failed.");
            System.out.println("Please check the above outputs and the file {err.log} carefully.\n");
        }
        return reader.isAllFinished;
    }

    private ResourceReader() {}

    private void getMenuItems() {
        System.out.format("    Try to find a text file (custom first) for the menu list:\n");
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("custom/ui/menu.txt"));
            System.out.format("    Finished. Custom menu list is found.\n");
        } catch (FileNotFoundException e) {
            in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/default/ui/menu.txt")));
            System.out.format("    Finished. Default menu list is found.\n");
        } catch (NullPointerException e) {
            System.out.format("    Failed. Cannot find any text file for the menu list.\n");
            System.err.format("Failed to get neither custom or default text file for the menu list.\n");
            System.err.format("    at %s (%s)\n", "ResourceReader", "getMenuItems");
            isAllFinished = false;
            return;
        }
        try {
            while (in.ready()) {
                String str = in.readLine();
                if (!str.isEmpty()) {
                    Director.getInstance().addMenuItem(str);
                    Director.getInstance().setMaxLoadingProgress(Director.getInstance().getMaxLoadingProgress() + 1);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.format("    Failed: unexpected IOException. See detailed information in the file {err.log}.\n");
            e.printStackTrace();
            isAllFinished = false;
            return;
        }
        Director.getInstance().loadingProgressProperty().setValue(Director.getInstance().loadingProgressProperty().getValue() + 1);
    }

    private void getVDSInstantiations(final String strInvocation) {
        // split input string
        String[] args = strInvocation.split(" ");
        // initialize VDS instantiation
        VDSInstantiation vdsInstantiation = new VDSInstantiation();
        // get class name
        String vdsName = args[0];
        String vdsClassName = "Visualized" + vdsName;
        vdsInstantiation.setName(vdsName);
        vdsInstantiation.setClassName(vdsClassName);
        // get class type
        System.out.format("    Try to get a definition (custom first) of class %s:\n", vdsClassName);
        Class<?> vdsClassType;
        try {
            // dynamic compile
            getCustomVDSClassType(vdsClassName);
            // dynamic load
            URL url = new URL("file:/" + Director.getInstance().getRootPath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            vdsClassType = classLoader.loadClass("custom.visualized." + vdsClassName);
            System.out.format("    Finished. Custom definition of class %s is dynamic-compiled and loaded.\n", vdsClassName);
        } catch (ClassNotFoundException | MalformedURLException e1) {
            System.out.format("        %s: %s\n", e1.getClass().getName(), e1.getMessage());
            System.out.format("        Failed to get a custom definition.\n");
            System.out.format("        Try to find a default definition...\n");
            try {
                vdsClassType = Class.forName("jm233333.visualized." + vdsClassName);
            } catch (ClassNotFoundException e2) {
                System.out.format("        %s: %s\n", e2.getClass().getName(), e2.getMessage());
                System.out.format("        Failed. No default definition of class %s exists.\n", vdsClassName);
                System.err.format("Failed to get neither custom or default definition of class %s.\n", vdsClassName);
                System.err.format("    at %s (%s)\n", "ResourceReader", "getVDSInstantiations");
                isAllFinished = false;
                return;
            }
            System.out.format("    Finished. Default definition of class %s is found.\n", vdsClassName);
        }
        vdsInstantiation.setClassType(vdsClassType);
        // get argument list and parameter list (lazy ver.)
        int argc = args.length - 1;
        Class<?>[] parameterTypes = new Class<?>[argc];
        for (int i = 0; i < argc; i ++) {
            parameterTypes[i] = int.class;
            vdsInstantiation.getArgumentList().add(Integer.parseInt(args[i + 1]));
        }
        // get constructor
        System.out.format("    Try to find the specified constructor of class %s:\n", vdsClassName);
        Constructor constructor;
        try {
            constructor = vdsClassType.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            System.out.format("        %s: %s\n", e.getClass().getName(), e.getMessage());
            System.out.format("    Failed.\n");
            System.err.format("Failed to find the specified constructor of class %s.\n", vdsClassName);
            System.err.format("    at %s (%s)\n", "ResourceReader", "getVDSInstantiations");
            isAllFinished = false;
            return;
        }
        System.out.format("    Finished. Specified constructor of class %s is found.\n", vdsClassName);
        vdsInstantiation.setConstructor(constructor);
        // store VDS instantiation
        Director.getInstance().getVDSInstantiationMap().put(strInvocation, vdsInstantiation);
        Director.getInstance().loadingProgressProperty().setValue(Director.getInstance().loadingProgressProperty().getValue() + 1);
        // get code text used by code tracker
        getVDSCode(vdsName);
    }

    private void getCustomVDSClassType(final String vdsClassName) {
        System.out.format("        Try a dynamic-compilation for class %s:\n", vdsClassName);
//        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        JavaCompiler javac = new JavacTool();
        OutputStream _out = new ByteArrayOutputStream(200);
        OutputStream _err = new ByteArrayOutputStream(200);
        int status = javac.run(null, _out, _err, "custom/visualized/" + vdsClassName + ".java", "-parameters");
        if (status == 2) {
            System.out.format("            Status %d: Failed to find java source file.\n", status);
            System.out.format("        Failed.\n");
        } else if (status != 0) {
            System.out.format("            Status %d: Compile error.\n", status);
            System.out.format("            Error information from the javac compiler:\n");
            System.out.format("\n");
            System.out.println(_err.toString());
            System.out.format("\n");
            System.out.format("        Failed.\n");
        } else {
            System.out.format("            Status %d: Compile successfully.\n", status);
            System.out.format("        Finished.\n");
        }
    }

    private void getVDSCode(final String vdsName) {
        System.out.format("    Try to find the native code file {%s.%s} used by CodeTracker:\n", vdsName, "cpp");
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("custom/code/" + vdsName + ".cpp"));
        } catch (FileNotFoundException ex) {
            try {
                in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/default/code/" + vdsName + ".cpp")));
//                in = new BufferedReader(new FileReader(this.getClass().getResource("/default/code/" + vdsName + ".cpp").getFile()));
            } catch (NullPointerException e) {
                Director.getInstance().getVdsCodeMap().put(vdsName, null);
                System.out.format("    Failed. Cannot find the native code file {%s.%s}.\n", vdsName, "cpp");
                System.err.format("Failed to find the native code file {%s.%s}.\n", vdsName, "cpp");
                System.err.format("    at %s (%s)\n", "ResourceReader", "getVDSCode");
                isAllFinished = false;
                return;
            }
        }
        try {
            ArrayList<String> codeList = new ArrayList<>();
            while (in.ready()) {
                codeList.add(in.readLine());
            }
            in.close();
            Director.getInstance().getVdsCodeMap().put(vdsName, codeList);
        } catch (IOException e) {
            System.out.format("    Failed: unexpected IOException. See detailed information in the file {err.log}.\n");
            e.printStackTrace();
            isAllFinished = false;
            return;
        }
        System.out.format("    Finished.\n");
    }
}
