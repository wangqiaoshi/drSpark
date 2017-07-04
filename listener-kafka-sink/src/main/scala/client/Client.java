package client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by wqs on 16/12/24.
 */
public class Client {


    public void send(){

    }


    public static void main(String args[]){

        try {
            TTransport transport = new TSocket("127.0.0.1", 8089);
            TProtocol protocol = new TBinaryProtocol(transport);
            TDJob.Client client = new TDJob.Client(protocol);
            transport.open();
            client.sendJob();
            Thread.sleep(100000);
            transport.close();
        }catch (Exception e){

            System.out.printf(e.getMessage());
        }




    }
}
