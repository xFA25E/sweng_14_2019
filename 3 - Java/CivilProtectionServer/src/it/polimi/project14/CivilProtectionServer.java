package it.polimi.project14;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

import it.polimi.project14.server.EventStorageImpl;

public class CivilProtectionServer {
    public static void main(String[] args) {
        try {
            // Registration of object on RMI registry
            // Rebind instead of bind cause if it find something whit same
            // name it replace with this new object.(decrease bugs)
            System.setProperty("java.security.policy", "policy.all");
            System.out.println(System.getProperty("java.security.policy"));
            Remote remoteEventStorage = new EventStorageImpl();
            LocateRegistry.createRegistry(6666);
            Naming.rebind("rmi://localhost:6666/EVENT_STORAGE", remoteEventStorage);
            System.out.println("3");
        } catch (Exception e) {
            System.out.println("RMI error: " + e.getMessage());
        }
        System.out.println("Starting server");
    }
}
