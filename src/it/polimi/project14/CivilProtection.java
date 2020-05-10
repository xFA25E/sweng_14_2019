package it.polimi.project14;

public class CivilProtection {
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
            case "server":
                System.out.println("Starting server");
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
