/**
 * Aspecto que otimiza o cálculo do fatorial através de um cache.
 *
 */
import java.util.*;

public aspect OtimizaFatorialAspect {
    pointcut factorialOperation(int n) :
        call(long *.fatorial(int)) && 
        args(n);

    pointcut topLevelFactorialOperation(int n) :
        factorialOperation(n)
        && !cflowbelow(factorialOperation(int));

    private Map _factorialCache = new HashMap();

    before(int n) : topLevelFactorialOperation(n) {
        System.out.println("Procurando um fatorial p/ " + n);
    }

    long around(int n) : factorialOperation(n) {
        Object cachedValue = _factorialCache.get(new Integer(n));
        if (cachedValue != null) {
            System.out.println("Encontrado um valor p/ " + n
                       + ": " + cachedValue);
            return ((Long)cachedValue).longValue();
        }
        return proceed(n);
    }

    after(int n) returning(long result) : 
        topLevelFactorialOperation(n) {
        _factorialCache.put(new Integer(n), new Long(result));
    }
}
