import java.lang.reflect.InvocationTargetException;

import javax.management.InstanceAlreadyExistsException;

public class Inspector<T>{
    private Class<T> inspectedClass;

    public Inspector(Class<T> inspectedClass) {
        this.inspectedClass = inspectedClass;
    }

    public void displayInformations() {

        System.out.println("Information of the \"" + inspectedClass.getName() + "\" class:");

        if (inspectedClass.getSuperclass() != null) {
            System.out.println("Superclass: " + inspectedClass.getSuperclass().getName());
        }

        if (inspectedClass.getDeclaredMethods() != null) {
            System.out.println(inspectedClass.getDeclaredMethods().length + " methods:");
            var methods = inspectedClass.getDeclaredMethods();
            for (var method : methods) {
            System.out.println("- "+method.getName());
        }
        }

        if (inspectedClass.getDeclaredFields() != null) {
            System.out.println(inspectedClass.getDeclaredFields().length + " fields:");
            var fields = inspectedClass.getDeclaredFields();
            for (var field : fields) {
                System.out.println("- "+field.getName());
            }
        }

    }

    public T createInstance() {
        try {
            return inspectedClass.getDeclaredConstructor().newInstance();
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException allError)
        {
            return null;
        }
    }
}
