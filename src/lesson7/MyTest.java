package lesson7;

public class MyTest {
    @AfterSuite
    public static void testAfter() {
        System.out.println("Отработал метод testAfter с аннотацией AfterSuite!");
    }

    @BeforeSuite
    public static void testBefore() {
        System.out.println("Отработал метод testBefore с аннотацией BeforeSuite!");
    }

//    Для проверки исключений
//    @AfterSuite
//    public static void testAfter1() {
//        System.out.println("Отработал метод testAfter с аннотацией AfterSuite!");
//    }
//
//    @BeforeSuite
//    public static void testBefore1() {
//        System.out.println("Отработал метод testBefore с аннотацией BeforeSuite!");
//    }

    @Test(priority = 5)
    public static void testTestPriority5() {
        System.out.println("Отработал метод testTestPriority5 с аннотацией Test!");
    }

    @Test(priority = 2)
    public static void testTestPriority2() {
        System.out.println("Отработал метод testTestPriority2 с аннотацией Test!");
    }

    @Test(priority = 1)
    public static void testTestPriority1() {
        System.out.println("Отработал метод testTestPriority1 с аннотацией Test!");
    }

    @Test(priority = 2)
    public static void testTestPriority2Repeat() {
        System.out.println("Отработал метод testTestPriority2Repeat с аннотацией Test!");
    }

    @Test(priority = 10)
    public static void testTestPriority9() {
        System.out.println("Отработал метод testTestPriority10 с аннотацией Test!");
    }
}
