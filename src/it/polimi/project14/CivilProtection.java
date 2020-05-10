package it.polimi.project14;

import java.sql.SQLException;

import it.polimi.project14.server.Server;

public class CivilProtection {
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
            case "server":
                System.out.println("Starting server");

                try {
                    Server server = new Server();
                    try {
                        server.getEvents(null);
                        try {
                            server.storeEvents(null);
                        } catch (SQLException e) {
                            System.out.println("In store: " + e.getMessage());
                        }
                    } catch (SQLException e) {
                        System.out.println("In get: " + e.getMessage());
                    }
                } catch (SQLException e) {
                    System.out.println("In server: " + e.getMessage());
                }

                break;
            case "user":
                System.out.println("Starting server");
                break;
            case "source":
                System.out.println("Starting server");
                break;
            default:
                System.out.println("Usage: $0 server|user|source");
            }
        } else {
            System.out.println("Usage: $0 server|user|source");
        }
    }
}
