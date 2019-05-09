package pl.parser.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AspectForNbp {
    private final static org.apache.log4j.Logger log = Logger.getLogger(AspectForNbp.class);


    @Pointcut("execution( * pl.parser.nbp.ApiParser.listOfCodes(..))")
    private void forListOfCodes() {
    }
    @Pointcut("execution( * pl.parser.nbp.MainClass.searchedListOfCodes(..))")
    private void forSearchedListOfCodes() {
    }
    @Pointcut("execution( * pl.parser.nbp.ApiParser.returnListWithCodes(..))")
    private void forListOfMappedObjects() {
    }
    @Pointcut("execution( * pl.parser.nbp.ApiParser.getInputStreamList(..))")
    private void forListOfInputStreams() {
    }
    @Pointcut(" forListOfCodes() || forListOfInputStreams()|| forListOfMappedObjects()||forSearchedListOfCodes()")
    private void forMain(){


    }

    @Before("forMain()")
    public void before(JoinPoint joinPoint) {
        String theMethod = joinPoint.getSignature().toShortString();
        log.info("Calling=====>  " + theMethod);


        Object[] args = joinPoint.getArgs();

        for (Object tempArg : args) {
            log.info("Arguments: " + tempArg);
        }
    }

    @AfterReturning(
            pointcut = "forMain()",
            returning = "theResult"
    )
    public void afterReturning(JoinPoint joinPoint,Object theResult){

        String method = joinPoint.getSignature().toShortString();
        log.debug("========> in @AFTER "+ method);

        log.debug("=====>> result"+ theResult);
    }

    @Pointcut("execution( * pl.parser.nbp.DateParser.*(..))")
    private void forNarzedziaDaty() {
    }

    @Before("forNarzedziaDaty()")
    public void beforeNarzedziadaty(JoinPoint joinPoint) {
        String theMethod = joinPoint.getSignature().toShortString();
        log.debug("Calling method :" + theMethod);


        Object[] args = joinPoint.getArgs();

        for (Object tempArg : args) {
            log.debug("Arguments" + tempArg);
        }
    }
    @AfterReturning(
            pointcut = "forNarzedziaDaty()",
            returning = "theResult"
    )
    public void afterReturningNarzedziadaty(JoinPoint joinPoint,Object theResult){

        String method = joinPoint.getSignature().toShortString();
        log.debug("========> in @AFTER "+ method);

        log.debug("=====>> result"+ theResult);
    }

    @Pointcut("execution( * pl.parser.nbp.MathClass.*(..))")
    private void forNarzedziaKalkulacji() {
    }

    @Before("forNarzedziaKalkulacji()")
    public void beforeNarzedziaKalkulacji(JoinPoint joinPoint) {
        String theMethod = joinPoint.getSignature().toShortString();
        log.debug("Calling method :" + theMethod);


        Object[] args = joinPoint.getArgs();

        for (Object tempArg : args) {
            log.debug("Arguments" + tempArg);
        }
    }
    @AfterReturning(
            pointcut = "forNarzedziaKalkulacji()",
            returning = "theResult"
    )
    public void afterReturningNarzedziaKalkulacji(JoinPoint joinPoint,Object theResult){

        String method = joinPoint.getSignature().toShortString();
        log.debug("========> in @AFTER "+ method);

        log.debug("=====>> result"+ theResult);
    }
}
