package it.polimi.project14;

import java.rmi.Naming;
import java.rmi.Remote;

import it.polimi.project14.server.EventStorageImpl;

public class CivilProtection {
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "server":
                    try {
                        Remote remoteEventStorage = new EventStorageImpl();
                        Naming.rebind("EVENT_STORAGE", remoteEventStorage);
                    } catch (Exception e) {
                        System.out.println("RMI error");
                    }
                    System.out.println("Starting server");
                    break;
                case "user":
                    System.out.println("Starting user");
                    break;
                case "source":
                    System.out.println("Starting source");
                    break;
                default:
                    System.out.println("Usage: $0 server|user|source");
            }
        } else {
            System.out.println("Usage: $0 server|user|source");
        }
    }
}
