if exist Java/target/source-uberjar.jar (
    start java -jar Java/target/source-uberjar.jar
) else (
    start "" cmd /c "echo Java/trget/source-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)