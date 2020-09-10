package jm233333.dsv.io;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;

import javax.tools.JavaCompiler;
import com.sun.tools.javac.api.JavacTool;

import jm233333.dsv.Global;
import jm233333.dsv.visualized.VDSInstantiation;

/**
 * Class {@code ResourceReader} is a singleton class responsible for pre-loading and storing custom resources.
 */
public class ResourceReader {

    /**
     * The singleton of {@code ResourceReader}.
     */
    private static ResourceReader instance = new ResourceReader();

    private boolean isLoaded;
    private boolean isAllSucceeded;

    private ArrayList<String> menuItemsList;
    private HashMap<String, VDSInstantiation> vdsInstantiationMap;
    private HashMap<String, ArrayList<String>> vdsCodeMap;

    private IntegerProperty loadingProgressProperty;
    private int maxLoadingProgress;

    /**
     * Creates the singleton of {@code ResourceReader}.
     */
    private ResourceReader() {
        isLoaded = false;
        isAllSucceeded = false;

        menuItemsList = new ArrayList<>();
        vdsInstantiationMap = new HashMap<>();
        vdsCodeMap = new HashMap<>();

        loadingProgressProperty().setValue(0);
        maxLoadingProgress = 1;
    }

    /**
     * Gets the singleton of {@code ResourceReader}.
     *
     * @return the singleton of {@code ResourceReader}.
     */
    public static ResourceReader getInstance() {
        return instance;
    }

    /**
     * The only public interface that starts to read resources.
     *
     * @return if the tasks of reading resources are all succeeded.
     */
    public boolean getCustomResources() {
        // get the singleton
        ResourceReader reader = getInstance();
        // check if is loaded
        if (isLoaded) {
            System.out.println("The custom resources has been loaded. Process stopped.");
            return isAllSucceeded;
        }
        System.out.println("Start loading custom resources:");
        // loading
        reader.getMenuItems();
        final ArrayList<String> menuItems = getMenuItemsList();
        for (String menuItem : menuItems) {
            reader.getVDSInstantiations(menuItem);
        }
        // set flag
        isLoaded = true;
        // return
        if (reader.isAllSucceeded) {
            System.out.println("All loading tasks are finished. All custom resources (or corresponding default ones) are successfully loaded.\n");
        } else {
            System.err.println("All loading tasks are Finished. Some of the tasks are failed.");
            System.err.println("Please check the above output information and the file {err.log} carefully.\n");
        }
        return reader.isAllSucceeded;
    }
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
            isAllSucceeded = false;
            return;
        }
        try {
            while (in.ready()) {
                String str = in.readLine();
                if (!str.isEmpty()) {
                    getMenuItemsList().add(str);
                    setMaxLoadingProgress(getMaxLoadingProgress() + 1);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.format("    Failed: unexpected IOException. See detailed information in the file {err.log}.\n");
            e.printStackTrace();
            isAllSucceeded = false;
            return;
        }
        loadingProgressProperty().setValue(loadingProgressProperty().getValue() + 1);
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
            URL url = new URL("file:/" + Global.getRootPath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            vdsClassType = classLoader.loadClass("custom.visualized." + vdsClassName);
            System.out.format("    Finished. Custom definition of class %s is dynamic-compiled and loaded.\n", vdsClassName);
        } catch (ClassNotFoundException | MalformedURLException e1) {
            System.out.format("        %s: %s\n", e1.getClass().getName(), e1.getMessage());
            System.out.format("        Failed to get a custom definition.\n");
            System.out.format("        Try to find a default definition...\n");
            try {
                vdsClassType = Class.forName("jm233333.dsv.visualized." + vdsClassName);
            } catch (ClassNotFoundException e2) {
                System.out.format("        %s: %s\n", e2.getClass().getName(), e2.getMessage());
                System.out.format("        Failed. No default definition of class %s exists.\n", vdsClassName);
                System.err.format("Failed to get neither custom or default definition of class %s.\n", vdsClassName);
                System.err.format("    at %s (%s)\n", "ResourceReader", "getVDSInstantiations");
                isAllSucceeded = false;
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
            isAllSucceeded = false;
            return;
        }
        System.out.format("    Finished. Specified constructor of class %s is found.\n", vdsClassName);
        vdsInstantiation.setConstructor(constructor);
        // store VDS instantiation
        getVdsInstantiationMap().put(strInvocation, vdsInstantiation);
        loadingProgressProperty().setValue(loadingProgressProperty().getValue() + 1);
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
                getVdsCodeMap().put(vdsName, null);
                System.out.format("    Failed. Cannot find the native code file {%s.%s}.\n", vdsName, "cpp");
                System.err.format("Failed to find the native code file {%s.%s}.\n", vdsName, "cpp");
                System.err.format("    at %s (%s)\n", "ResourceReader", "getVDSCode");
                isAllSucceeded = false;
                return;
            }
        }
        try {
            ArrayList<String> codeList = new ArrayList<>();
            while (in.ready()) {
                codeList.add(in.readLine());
            }
            in.close();
            getVdsCodeMap().put(vdsName, codeList);
        } catch (IOException e) {
            System.out.format("    Failed: unexpected IOException. See detailed information in the file {err.log}.\n");
            e.printStackTrace();
            isAllSucceeded = false;
            return;
        }
        System.out.format("    Finished.\n");
    }

    /**
     * Gets the list of VDS shown on the {@code SceneMenu}.
     *
     * @return the list of VDS shown on the {@code SceneMenu}.
     */
    public ArrayList<String> getMenuItemsList() {
        return menuItemsList;
    }
    /**
     * Gets the storage of VDS instantiation information.
     *
     * @return the storage of VDS instantiation information.
     */
    public HashMap<String, VDSInstantiation> getVdsInstantiationMap() {
        return vdsInstantiationMap;
    }
    /**
     * Gets the storage of VDS to-be-tracked source codes.
     *
     * @return the storage of VDS to-be-tracked source codes.
     */
    public HashMap<String, ArrayList<String>> getVdsCodeMap() {
        return vdsCodeMap;
    }

    /**
     * An {@code IntegerProperty} that represents the progress of resource-loading tasks.
     */
    public final IntegerProperty loadingProgressProperty() {
        if (loadingProgressProperty == null) {
            loadingProgressProperty = new IntegerPropertyBase(0) {
                @Override
                public Object getBean() {
                    return this;
                }
                @Override
                public String getName() {
                    return "loadingProgress";
                }
            };
        }
        return loadingProgressProperty;
    }
    /**
     * Gets the current completion rate of resource-loading tasks.
     *
     * @return the current completion rate of resource-loading tasks.
     */
    public double getLoadingProgress() {
        if (maxLoadingProgress == 0) {
            return 0.0;
        }
        return ((double)loadingProgressProperty().getValue() / maxLoadingProgress);
    }
    /**
     * Asks if all resource-loading tasks are finished currently.
     *
     * @return if all resource-loading tasks are finished currently.
     */
    public boolean isAllLoaded() {
        return (loadingProgressProperty().getValue() == maxLoadingProgress);
    }

    private void setMaxLoadingProgress(int maxLoadingProgress) {
        this.maxLoadingProgress = maxLoadingProgress;
    }
    private int getMaxLoadingProgress() {
        return maxLoadingProgress;
    }
}
