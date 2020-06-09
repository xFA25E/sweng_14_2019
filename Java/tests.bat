if exist target/tests-uberjar.jar (
    start java -jar target/tests-uberjar.jar
) else (
    start "" cmd /c "echo target/tests-uberjar.jar non trovato. Eseguire "make uberjar"&echo(&pause"
)
