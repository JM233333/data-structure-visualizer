package jm233333.io;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import jm233333.Director;
import jm233333.ui.PanelConsole;
import jm233333.visualized.VDSInstantiation;

import javax.tools.JavaCompiler;
import com.sun.tools.javac.api.JavacTool;

public class ResourceReader {

    public static void getCustomResources() {
        ResourceReader reader = new ResourceReader();
        reader.getMenuItems();
        final ArrayList<String> menuItems = Director.getInstance().getMenuItems();
        for (String menuItem : menuItems) {
            reader.getVDSInstantiations(menuItem);
        }
    }

    private ResourceReader() {}

    private void getMenuItems() {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("custom/ui/menu.txt"));
        } catch (FileNotFoundException e) {
            in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/default/ui/menu.txt")));
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
            e.printStackTrace();
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
        Class<?> vdsClassType;
        try {
            // dynamic compile
            getCustomVDSClassType(vdsClassName);
            // dynamic load
            URL url = new URL("file:/" + Director.getInstance().getRootPath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            vdsClassType = classLoader.loadClass("custom.visualized." + vdsClassName);
        } catch (ClassNotFoundException | MalformedURLException ex) {
            try {
                vdsClassType = Class.forName("jm233333.visualized." + vdsClassName);
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
                return;
            }
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
        Constructor constructor;
        try {
            constructor = vdsClassType.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            System.err.println(e.getMessage());
            return;
        }
        vdsInstantiation.setConstructor(constructor);
        // store VDS instantiation
        Director.getInstance().getVDSInstantiationMap().put(strInvocation, vdsInstantiation);
        Director.getInstance().loadingProgressProperty().setValue(Director.getInstance().loadingProgressProperty().getValue() + 1);
        // get code text used by code tracker
        getVDSCode(vdsName);
    }

    private void getCustomVDSClassType(final String vdsClassName) {
//        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        JavaCompiler javac = new JavacTool();
        int status = javac.run(null, null, null, "custom/visualized/" + vdsClassName + ".java", "-parameters");
        if (status == 2) {
            System.err.println("Java Source File Not Found for class " + vdsClassName);
        } else if (status != 0) {
            System.err.println("Dynamic Compile Error for class " + vdsClassName);
        } else {
            System.out.println("Dynamic Compile Successfully for class " + vdsClassName);
        }
    }

    private void getVDSCode(final String vdsName) {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("custom/code/" + vdsName + ".cpp"));
        } catch (FileNotFoundException ex) {
            try {
                in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/default/code/" + vdsName + ".cpp")));
//                in = new BufferedReader(new FileReader(this.getClass().getResource("/default/code/" + vdsName + ".cpp").getFile()));
            } catch (NullPointerException e) {
                Director.getInstance().getVdsCodeMap().put(vdsName, null);
                System.err.println("Native Code File Not Found for " + vdsName);
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
            e.printStackTrace();
        }
    }
}
