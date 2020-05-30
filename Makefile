###############################################################################
#                                  VARIABLES                                  #
###############################################################################

# UML #########################################################################

UML_DIR := UML
UML_LIB := $(UML_DIR)/lib
UML_SRC := $(UML_DIR)/src
UML_SRC_EXT := puml
UML_TARGET := $(UML_DIR)/target
UML_TARGET_EXT := png
UML_SOURCES := $(shell find $(UML_SRC) -type f -name *.$(UML_SRC_EXT))
UML_TARGETS := $(UML_SOURCES:$(UML_SRC)/%.$(UML_SRC_EXT)=$(UML_TARGET)/%.$(UML_TARGET_EXT))

PLANTUML := plantuml.jar
PLANTUML_JAR := $(UML_LIB)/$(PLANTUML)
PLANTUML_ARGS := -p
ifeq ($(UML_TARGET_EXT), tex)
	PLANTUML_ARGS += -tlatex
else
	PLANTUML_ARGS += -t$(UML_TARGET_EXT)
endif

# Java ########################################################################

JAVA_DIR := Java
ifeq ($(OS),Windows_NT)
	SEP := ;
else
	SEP := :
endif

COMMON_DIR := $(JAVA_DIR)/Common
COMMON_SRC := $(COMMON_DIR)/src
COMMON_BIN := $(COMMON_DIR)/bin
COMMON_TARGET := $(COMMON_DIR)/target
COMMON_JAR := $(COMMON_TARGET)/common.jar
COMMON_SOURCES := $(shell find $(COMMON_SRC) -type f -name *.java)

SERVER_DIR := $(JAVA_DIR)/Server
SERVER_SRC := $(SERVER_DIR)/src
SERVER_LIB := $(SERVER_DIR)/lib
SERVER_BIN := $(SERVER_DIR)/bin
SERVER_TARGET := $(SERVER_DIR)/target
SERVER_JAR := $(SERVER_TARGET)/server.jar
SERVER_SOURCES := $(shell find $(SERVER_SRC) -type f -name *.java)

SQLITE := sqlite-jdbc-3.30.1.jar
SQLITE_JAR := $(SERVER_LIB)/$(SQLITE)

USER_DIR := $(JAVA_DIR)/User
USER_LIB := $(USER_DIR)/lib
USER_SRC := $(USER_DIR)/src
USER_BIN := $(USER_DIR)/bin
USER_TARGET := $(USER_DIR)/target
USER_JAR := $(USER_TARGET)/user.jar
USER_SOURCES := $(shell find $(USER_SRC) -type f -name *.java)
DATETIMEPICKER_JAR := $(USER_LIB)/LGoodDatePicker-10.4.1.jar

SOURCE_DIR := $(JAVA_DIR)/Source
SOURCE_SRC := $(SOURCE_DIR)/src
SOURCE_BIN := $(SOURCE_DIR)/bin
SOURCE_TARGET := $(SOURCE_DIR)/target
SOURCE_JAR := $(SOURCE_TARGET)/source.jar
SOURCE_SOURCES := $(shell find $(SOURCE_SRC) -type f -name *.java)

TESTS_DIR := $(JAVA_DIR)/Tests
TESTS_SRC := $(TESTS_DIR)/src
TESTS_LIB := $(TESTS_DIR)/lib
TESTS_BIN := $(TESTS_DIR)/bin
TESTS_TARGET := $(TESTS_DIR)/target
TESTS_JAR := $(TESTS_TARGET)/tests.jar
TESTS_SOURCES := $(shell find $(TESTS_SRC) -type f -name *.java)

JUNIT_JAR := $(TESTS_LIB)/junit-4.13.jar
HAMCREST_JAR := $(TESTS_LIB)/hamcrest-core-1.3.jar

# OTHER #######################################################################

join-cp = $(subst $(null) $(null),$(SEP),$(strip $1))

###############################################################################
#                                   RECEPIES                                  #
###############################################################################

.PHONY: all \
uml common server user source tests \
run-server run-user run-source run-tests \
junit pluntuml sqlite clean datetimepicker

all: uml common server user source

# UML #########################################################################

uml: $(UML_TARGETS)

$(UML_TARGET)/%.$(UML_TARGET_EXT): $(UML_SRC)/%.$(UML_SRC_EXT) $(PLANTUML_JAR)
	mkdir -p $(@D)
	java -cp "$(call join-cp,$(wildcard /opt/plantuml/*.jar))" -jar $(PLANTUML_JAR) $(PLANTUML_ARGS) <$< >$@

plantuml: $(UML_LIB)
	wget -O $(PLANTUML_JAR) "https://sourceforge.net/projects/plantuml/files/$(PLANTUML)/download"

# Java ########################################################################

run-server: $(SERVER_JAR) $(SQLITE_JAR) $(COMMON_JAR)
	java -cp "$(call join-cp,$^)" it.polimi.project14.CivilProtectionServer

run-user: $(USER_JAR) $(COMMON_JAR) 1$(DATETIMEPICKER_JAR)
	java -cp "$(COMMON_JAR)$(SEP)$(DATETIMEPICKER_JAR)" -jar "$(USER_JAR)"

run-source: $(SOURCE_JAR) $(COMMON_JAR)
	java -cp "$(COMMON_JAR)" -jar "$(SOURCE_JAR)"

# $(USER_JAR)
run-tests: $(TESTS_JAR) $(SQLITE_JAR) $(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(JUNIT_JAR) $(HAMCREST_JAR)
	java -cp "$(call join-cp,$^)" it.polimi.project14.CivilProtectionTests

common: $(COMMON_JAR)
server: $(SERVER_JAR)
user: $(USER_JAR)
source: $(SOURCE_JAR)
tests: $(TESTS_JAR)

$(COMMON_JAR): $(COMMON_SOURCES) $(COMMON_BIN) $(COMMON_TARGET)
	javac -target 8 -d $(COMMON_BIN) $(COMMON_SOURCES)
	jar cf $(COMMON_JAR) -C $(COMMON_BIN) .

$(SERVER_JAR): $(SERVER_SOURCES) $(SERVER_BIN) $(SERVER_TARGET) $(COMMON_JAR)
	javac -target 8 -d $(SERVER_BIN) -cp $(COMMON_JAR) $(SERVER_SOURCES)
	jar cfe $(SERVER_JAR) it.polimi.project14.CivilProtectionServer -C $(SERVER_BIN) .

$(USER_JAR): $(USER_SOURCES) $(USER_BIN) $(USER_TARGET) $(COMMON_JAR) $(DATETIMEPICKER_JAR)
	javac -target 8 -d $(USER_BIN) -cp "$(COMMON_JAR)$(SEP)$(DATETIMEPICKER_JAR)" $(USER_SOURCES)
	jar cfe $(USER_JAR) it.polimi.project14.CivilProtectionUser -C $(USER_BIN) .

$(SOURCE_JAR): $(SOURCE_SOURCES) $(SOURCE_BIN) $(SOURCE_TARGET) $(COMMON_JAR)
	javac -target 8 -d $(SOURCE_BIN) -cp $(COMMON_JAR) $(SOURCE_SOURCES)
	jar cfe $(SOURCE_JAR) it.polimi.project14.CivilProtectionSource -C $(SOURCE_BIN) .

# $(USER_JAR)
$(TESTS_JAR): $(TESTS_SOURCES) $(TESTS_BIN) $(TESTS_TARGET) $(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(JUNIT_JAR) $(HAMCREST_JAR)
	javac -target 8 -d $(TESTS_BIN) -cp "$(COMMON_JAR)$(SEP)$(SERVER_JAR)$(SEP)$(SOURCE_JAR)$(SEP)$(JUNIT_JAR)$(SEP)$(HAMCREST_JAR)" $(TESTS_SOURCES)
	jar cfe $(TESTS_JAR) it.polimi.project14.CivilProtectionTests -C $(TESTS_BIN) .

sqlite: $(SERVER_LIB)
	wget -O $(SQLITE_JAR) "https://bitbucket.org/xerial/sqlite-jdbc/downloads/$(SQLITE)"

junit: $(TESTS_LIB)
	wget -O $(JUNIT_JAR) "https://search.maven.org/remotecontent?filepath=junit/junit/4.13/junit-4.13.jar"

hamcrest: $(TESTS_LIB)
	wget -O $(HAMCREST_JAR) "https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"

datetimepicker: $(USER_LIB)
	wget -O $(DATETIMEPICKER_JAR) "https://github.com/LGoodDatePicker/LGoodDatePicker/releases/download/v10.4.1-Standard/LGoodDatePicker-10.4.1.jar"

# CLEAN #######################################################################

clean:
	-rm -r $(UML_TARGET) $(COMMON_BIN) $(COMMON_TARGET) $(SERVER_BIN) $(SERVER_TARGET) $(USER_BIN) $(USER_TARGET) $(SOURCE_BIN) $(SOURCE_TARGET) $(TESTS_BIN) $(TESTS_TARGET)

# DIRECTORIES #################################################################

$(TESTS_LIB) $(TESTS_BIN) $(TESTS_TARGET) $(UML_LIB) $(COMMON_BIN) $(COMMON_TARGET) $(SERVER_BIN) $(SERVER_TARGET) $(SERVER_LIB) $(USER_BIN) $(USER_TARGET) $(SOURCE_BIN) $(SOURCE_TARGET) $(USER_LIB):
	mkdir -p $@
