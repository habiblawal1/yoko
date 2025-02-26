package test.rmi.exceptionhandling;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

public class MyClientRequestInterceptor extends LocalObject implements ClientRequestInterceptor, ORBInitializer {

    @Override
    public void receive_exception(ClientRequestInfo arg0) throws ForwardRequest {
        System.out.printf("%08x: ", Thread.currentThread().getId());
        System.out.println("receive_exception(" + arg0.operation() + ")");
    }

    @Override
    public void receive_other(ClientRequestInfo arg0) throws ForwardRequest {
        System.out.printf("%08x: ", Thread.currentThread().getId());
        System.out.println("receive_other(" + arg0.operation() + ")");
    }

    @Override
    public void receive_reply(ClientRequestInfo arg0) {
        System.out.printf("%08x: ", Thread.currentThread().getId());
        System.out.println("receive_reply(" + arg0.operation() + ")");
    }

    @Override
    public void send_poll(ClientRequestInfo arg0) {
        System.out.printf("%08x: ", Thread.currentThread().getId());
        System.out.println("send_poll(" + arg0.operation() + ")");
    }

    @Override
    public void send_request(ClientRequestInfo arg0) throws ForwardRequest {
        System.out.printf("%08x: ", Thread.currentThread().getId());
        System.out.println("send_request(" + arg0.operation() + ")");
    }

    @Override
    public void destroy() {
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public void post_init(ORBInitInfo arg0) {
        try {
            arg0.add_client_request_interceptor(this);
        } catch (DuplicateName e) {
            throw new Error(e);
        }
    }

    @Override
    public void pre_init(ORBInitInfo arg0) {
    }
}
