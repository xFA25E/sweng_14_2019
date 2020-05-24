package it.polimi.project14;

import java.rmi.Naming;
import java.rmi.Remote;
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import it.polimi.project14.server.EventStorageImpl;
import it.polimi.project14.common.EventStorage;

public class CivilProtectionServer {
    public static void main(String[] args) {
        try {
            // Registration of object on RMI registry
            // Rebind instead of bind cause if it find something whit same
            // name it replace with this new object.(decrease bugs)
            // Registry host = LocateRegistry.getRegistry();
            System.setProperty("java.rmi.server.codebase", "file:///C:/Users/alesp/Desktop/sweng_14_2019%20-%20Copia/3%20-%20Java/CivilProtectionServer/build/it/polimi/project14/server/EventStorageImpl.class");
            System.setProperty("java.security.policy", "policy.all");
            System.out.println(System.getProperty("java.security.policy"));
            System.out.println("1");
            Remote remoteEventStorage = new EventStorageImpl();
            System.out.println("2");
            LocateRegistry.createRegistry(6666);
            System.out.println("2");
            Naming.rebind("rmi://localhost:6666/EVENT_STORAGE", remoteEventStorage);
            System.out.println("3");
        } catch (Exception e) {
            System.out.println("RMI error: " + e.getMessage());
        }
        System.out.println("Starting server");
    }
}
