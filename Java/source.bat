if exist target/source-uberjar.jar (
    start java -jar target/source-uberjar.jar
) else (
    start "" cmd /c "echo target/source-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)