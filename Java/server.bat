if exist target/server-uberjar.jar (
    start java -jar target/server-uberjar.jar
) else (
    start "" cmd /c "echo target/server-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)