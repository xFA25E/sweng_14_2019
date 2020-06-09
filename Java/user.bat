if exist Java/target/user-uberjar.jar (
    start java -jar Java/target/user-uberjar.jar
) else (
    start "" cmd /c "echo Java/trget/user-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)