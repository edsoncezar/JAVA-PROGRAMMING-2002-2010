public aspect HelloAspect {
    pointcut pc() : call(* HelloWorld.sayHello(..));
    before() : pc() { System.out.println("Hi!"); }
    after() : pc() { System.out.println("Bye!"); }
}
