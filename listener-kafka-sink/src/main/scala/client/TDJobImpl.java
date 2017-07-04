package client;

import org.apache.thrift.TException;

/**
 * Created by wqs on 16/12/23.
 */
public class TDJobImpl implements TDJob.Iface {

    @Override
    public void sendJob() throws TException {
        System.out.println("--------------sendJob-----------------");
    }
}
