import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.alipay.sofa.rpc.registry.zk.ZookeeperRegistry;

import java.lang.reflect.Constructor;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    ApplicationConfig applicationConfig = new ApplicationConfig().setAppName("generic-server");

    public static void main(String[] args){

        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("zookeeper")
                .setAddress("192.168.21.241:2181");

        Class c = registryConfig.getClass();
        Class[] cArg = new Class[1];
        cArg[0] = RegistryConfig.class;

        try {
            Constructor constructor = c.getConstructor( cArg);
        }  catch(NoSuchMethodException e) {
            System.out.println(e.toString());
        }
        catch(SecurityException e) {
            System.out.println(e.toString());
        }

        //ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry();


        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt")
                .setPort(12200)
                .setDaemon(false);
                //.setSerialization("protobuf");

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl())
                .setServer(serverConfig)
                .setRegistry(registryConfig);

        providerConfig.export();
        LOGGER.warn("started at pid {}", RpcRuntimeContext.PID);

    }
}
