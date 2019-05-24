package com.zzy.aop.easyscheduler.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/****************************************************************************************
 实现AOP的切面主要有以下几个要素：

 使用@Aspect注解将一个java类定义为切面类
 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
 根据需要在切入点不同位置的切入内容
 使用@Before在切入点开始处切入内容
 使用@After在切入点结尾处切入内容
 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
 使用@Order(i)注解来标识切面的优先级。i的值越小，优先级越高
 ****************************************************************************************/
//@Aspect 定义类为切入类
@Aspect
@Component
@Order(2)
public class PointAspect {

    //JoinPoint 这个参数可以获取被切入方法的名称和参数
    private void soutPointParam(JoinPoint joinPoint) {
        System.out.println("连接点方法为:" + joinPoint.getSignature().getName());
        System.out.println("连接点方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("连接点方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("连接点方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }


    //@Pointcut 声明一个切入策略供 @Before @After @ Around @ AfterReturning选择
    @Pointcut("execution(* com.zzy.aop.easyscheduler.controller.sendController.send*(..))")
    public void excudeService() {
        //只供声明策略使用，暂无发现其它用处
    }

    /*
     * 通过连接点切入
     */
    @Before(value = "excudeService()")
    public void before(JoinPoint point) {
        System.out.println("-----------------------------进入before1方法---------------------------------");
        soutPointParam(point);
        System.out.println("-----------------------------结束before1方法---------------------------------");
    }


    @After("execution(public * com.zzy.aop.easyscheduler.controller.sendController.send(..))")
    public void after(JoinPoint point) {
        System.out.println("-----------------------------进入after方法---------------------------------");
        soutPointParam(point);
        System.out.println("-----------------------------结束after方法---------------------------------");
    }


    /**
     * AspectJ使用org.aspectj.lang.JoinPoint接口表示目标类连接点对象，如果是环绕增强时，使用org.aspectj.lang.ProceedingJoinPoint表示连接点对象，该类是JoinPoint的子接口。任何一个增强方法都可以通过将第一个入参声明为JoinPoint访问到连接点上下文的信息。我们先来了解一下这两个接口的主要方法：
     * 1)JoinPoint
     *    java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；
     *    Signature getSignature() ：获取连接点的方法签名对象；
     *    java.lang.Object getTarget() ：获取连接点所在的目标对象；
     *    java.lang.Object getThis() ：获取代理对象本身；
     * 2)ProceedingJoinPoint
     * ProceedingJoinPoint继承JoinPoint子接口，它新增了两个用于执行连接点方法的方法：
     *    java.lang.Object proceed() throws java.lang.Throwable：通过反射执行目标对象的连接点处的方法；
     *    java.lang.Object proceed(java.lang.Object[] args) throws java.lang.Throwable：通过反射执行目标对象连接点处的方法，不过使用新的入参替换原来的入参。
     *
     */
    //环绕通知=前置+目标方法执行+后置通知
    //ProceedingJoinPoint 是JoinPoint的子接口，只支持在 around 中使用
    @Around("excudeService()")
    public Object around(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println("-----------------------------进入around方法---------------------------------");
            //proceed()方法的作用是让目标方法执行,如果该方法不执行，也就是目标方法不执行，around方法也就直接结束了,before方法也不会执行，直接进入after方法
            Object proceed = thisJoinPoint.proceed();
            System.out.println("目标方法返回值为：" + proceed.toString());
            proceed = "在around可以修改返回值";
            System.out.println("-----------------------------结束around方法---------------------------------");
            return proceed;
    }

    //  通过returning属性指定连接点方法返回的结果放置在result变量中，在返回通知方法中可以从result变量中获取连接点方法的返回结果了。
    @AfterReturning(value="excudeService()",returning="result")
    public void afterReturning(JoinPoint point, Object result){
        System.out.println("-----------------------------进入AfterReturning方法---------------------------------");
        soutPointParam(point);
        System.out.println("-----------------------------结束AfterReturning方法---------------------------------");
    }


    /*通过throwing属性指定连接点方法出现异常信息存储在ex变量中，在异常通知方法中就可以从ex变量中获取异常信息了*/
    @AfterThrowing(value="excudeService()", throwing="ex")
    public void afterReturning(JoinPoint point, Exception ex){
        System.out.println("-----------------------------进入AfterThrowing方法---------------------------------");
        soutPointParam(point);
        System.out.println("扑捉到了异常：" + ex.getMessage());
        System.out.println("-----------------------------结束AfterThrowing方法---------------------------------");
    }

}