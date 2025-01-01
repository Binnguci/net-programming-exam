package socket.rmi.ex19;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStudent extends Remote{
    String findByName(String name) throws RemoteException;
    String findById(String id) throws RemoteException;
} 