package client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;

/**
 * Created by wqs on 16/12/23.
 */
public class Server {



    public static void main(String args[]){
        Server server = new Server();
        server.startServer();
    }
    public void startServer(){
        try{

            TServerSocket serverTransport = new TServerSocket(8089);
            TDJob.Processor processor =  new TDJob.Processor(new TDJobImpl());
            Factory protocolFactory = new TBinaryProtocol.Factory();

            TServer.Args simpleArgs = new TServer.Args(serverTransport);
            simpleArgs.processor(processor);
            simpleArgs.protocolFactory(protocolFactory);
            TServer server = new TSimpleServer(simpleArgs);
            server.serve();


        }catch (Exception e){

            System.out.println("start:"+e.getMessage());
        }


    }

}


