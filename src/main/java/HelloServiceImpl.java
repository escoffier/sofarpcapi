public class HelloServiceImpl implements HelloService {
    public String say(String str) {
        return "Hello " + str + " [" + Thread.currentThread().getName() + "]";
    }

}
