if exist target/user-uberjar.jar (
    start java -jar target/user-uberjar.jar
) else (
    start "" cmd /c "echo target/user-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)