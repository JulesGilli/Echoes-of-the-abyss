import java.lang.reflect.*;

public class TestRunner<T>{
    public void runTests(Class <T> classTarget){
        try {
            T instanceTarget = classTarget.getDeclaredConstructor().newInstance();


            for (Method beforeClass : classTarget.getDeclaredMethods())
            {
                if (beforeClass.isAnnotationPresent(BeforeClass.class))
                {
                    beforeClass.invoke(instanceTarget);
                }
            }

            for (Method targetMethod: classTarget.getDeclaredMethods())
            {
                if (targetMethod.isAnnotationPresent(Test.class))
                {
                    if(targetMethod.getAnnotation(Test.class).enabled()){

                        for (Method before : classTarget.getDeclaredMethods())
                        {
                            if (before.isAnnotationPresent(Before.class))
                            {
                                before.invoke(instanceTarget);
                            }
                        }
                        System.out.println(targetMethod.getAnnotation(Test.class).name());
                        targetMethod.invoke(instanceTarget);

                        for (Method after : classTarget.getDeclaredMethods())
                        {
                            if (after.isAnnotationPresent(After.class))
                            {
                                after.invoke(instanceTarget);
                            }
                        }
                    } 
                }
            }
            for (Method afterClass : classTarget.getDeclaredMethods())
            {
                if (afterClass.isAnnotationPresent(AfterClass.class))
                {
                    afterClass.invoke(instanceTarget);
                }
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException allError){
            System.out.println("Error: "+ allError.getMessage());
        }
    }
}
