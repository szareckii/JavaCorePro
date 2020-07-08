package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    static final int min_priority = 1;
    static final int max_priority = 10;

    public static void main(String[] args)  {
//      параметр - объект типа Class
        Class myTest = MyTest.class;
        try {
            start(myTest);
        } catch (Exception e) {
            e.printStackTrace();
        }

//      параметр - имя класса (для проверки расскоментировать)
//        String nameClass = "lesson7.MyTest";
//        try {
//            start(nameClass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static void start(Class testClass) throws Exception {
        universalTest(testClass);
    }

    public static void start(String testClass) throws Exception {
        universalTest(Class.forName(testClass));
    }

    private static void universalTest(Class testClass) throws IllegalAccessException, InvocationTargetException {
        Method[] methods = testClass.getDeclaredMethods();

//        проверяем на количество начальных и конечных аннотаций
        int countBefore = 0;
        int countAfter = 0;

//      проверка на количество аннотаций
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                countBefore += 1;
            }

            if (m.isAnnotationPresent(AfterSuite.class)) {
                countAfter += 1;
            }
            if (countAfter > 1 || countBefore > 1) {
                throw new RuntimeException("Count annotations 'BeforeSuite' or 'AfterSuite' more than 1!");
            }
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                System.out.println("Запускаю метод: " + m.getAnnotation(BeforeSuite.class).description());
                m.invoke(null);
            }
        }

        for (int i = min_priority; i <= max_priority; i++) {
            for (Method m : methods) {
                if (m.isAnnotationPresent(Test.class)) {
                    if (m.getAnnotation(Test.class).priority() == i) {
                        System.out.println("Запускаю метод: " + m.getAnnotation(Test.class).description());
                        m.invoke(null);
                    }
                }
            }
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                System.out.println("Запускаю метод: " + m.getAnnotation(AfterSuite.class).description());
                m.invoke(null);
            }
        }

//  переевод массива методов в структуру дерево. (упорядоченно, но ключ - в единственном экземпляре может быть,
//  т.е. не получится в тестируемом классе выполнить два метода с одинаковым приоритетом)
//        Map<Integer, Method> methodsSorted = new TreeMap<>();
//
//        for (Method m : methods) {
//            if (m.isAnnotationPresent(BeforeSuite.class)) {
//                methodsSorted.put(min_priority - 1, m);
//            }
//
//            if (m.isAnnotationPresent(AfterSuite.class)) {
//                methodsSorted.put(max_priority + 1, m);
//            }
//
//            if (m.isAnnotationPresent(Test.class)) {
//                methodsSorted.put(m.getAnnotation(Test.class).priority(), m);
//            }
//
//        }
//
//        for (Map.Entry<Integer, Method> entry : methodsSorted.entrySet()) {
//            System.out.println("Запускаю метод: " + entry.getValue().getName());
//            entry.getValue().invoke(null);
//        }
    }
}
