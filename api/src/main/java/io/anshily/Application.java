package io.anshily;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
//public class Application implements EmbeddedServletContainerCustomizer {
public class Application extends SpringBootServletInitializer {

//     @Bean
//     public SocketIOServer socketIOServer() throws UnknownHostException {
// //        InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
// //        String hostAddress = address.getHostAddress();//192.168.0.121
//         Configuration config = new Configuration();

// //        config.setHostname("172.31.49.184");
//         config.setHostname("localhost");
//         config.setPort(8081);

//         //该处可以用来进行身份验证
//         config.setAuthorizationListener(new AuthorizationListener() {
//             @Override
//             public boolean isAuthorized(HandshakeData data) {
//                 System.out.println(data);
//                 //http://localhost:8081?username=test&password=test
//                 //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
// //              String username = data.getSingleUrlParam("username");
// //              String password = data.getSingleUrlParam("password");
//                 return true;
//             }
//         });
//         return new SocketIOServer(config);
//     }
//     @Bean
//     public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
//         return new SpringAnnotationScanner(socketServer);
//     }

    //ssl配置

    /**
     * it's for set http url auto change to https
     */
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");//confidential
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
////
//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
//    //这里设置默认端口为443，即https的，如果这里不设置，会https和http争夺80端口
//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.setPort(443);
//    }

    //拦截所有请求
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }





//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(8081);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(8443);
//        return connector;
//    }


    //这里设置默认端口为443，即https的，如果这里不设置，会https和http争夺80端口
//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.setPort(8443);
//    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

