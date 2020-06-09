if exist Java/target/server-uberjar.jar (
    start java -jar Java/target/server-uberjar.jar
) else (
    start "" cmd /c "echo Java/trget/server-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)