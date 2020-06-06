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
PLANTUML_URL := https://sourceforge.net/projects/plantuml/files/$(PLANTUML)/download
PLANTUML_ARGS := -p
ifeq ($(UML_TARGET_EXT), tex)
	PLANTUML_ARGS += -tlatex
else
	PLANTUML_ARGS += -t$(UML_TARGET_EXT)
endif

# Java ########################################################################

JAVA_ARGS := -Dfile.encoding=UTF-8
JAVAC_ARGS := -encoding UTF-8
ifeq ($(OS),Windows_NT)
	SEP := ;
	JAVAC_ARGS += --release 8
else
	SEP := :
	JAVAC_ARGS += -target 8
endif

JAVA := java $(JAVA_ARGS)
JAVAC := javac $(JAVAC_ARGS)

JAVA_DIR := Java
JAVA_TARGET := $(JAVA_DIR)/target
UBERJAR_SUFFIX := -uberjar.jar
SERVER_UBERJAR_BIN := $(JAVA_TARGET)/server
SERVER_UBERJAR := $(SERVER_UBERJAR_BIN)$(UBERJAR_SUFFIX)
USER_UBERJAR_BIN := $(JAVA_TARGET)/user
USER_UBERJAR := $(USER_UBERJAR_BIN)$(UBERJAR_SUFFIX)
SOURCE_UBERJAR_BIN := $(JAVA_TARGET)/source
SOURCE_UBERJAR := $(SOURCE_UBERJAR_BIN)$(UBERJAR_SUFFIX)

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
SERVER_MAIN := it.polimi.project14.CivilProtectionServer

SQLITE := sqlite-jdbc-3.30.1.jar
SQLITE_JAR := $(SERVER_LIB)/$(SQLITE)
SQLITE_URL := https://bitbucket.org/xerial/sqlite-jdbc/downloads/$(SQLITE)

USER_DIR := $(JAVA_DIR)/User
USER_LIB := $(USER_DIR)/lib
USER_SRC := $(USER_DIR)/src
USER_BIN := $(USER_DIR)/bin
USER_DATA := $(USER_DIR)/data
USER_TARGET := $(USER_DIR)/target
USER_JAR := $(USER_TARGET)/user.jar
USER_SOURCES := $(shell find $(USER_SRC) -type f -name *.java)
USER_MAIN := it.polimi.project14.CivilProtectionUser

DATETIMEPICKER_JAR := $(USER_LIB)/LGoodDatePicker-10.4.1.jar
DATETIMEPICKER_URL := https://github.com/LGoodDatePicker/LGoodDatePicker/releases/download/v10.4.1-Standard/LGoodDatePicker-10.4.1.jar

SOURCE_DIR := $(JAVA_DIR)/Source
SOURCE_SRC := $(SOURCE_DIR)/src
SOURCE_BIN := $(SOURCE_DIR)/bin
SOURCE_TARGET := $(SOURCE_DIR)/target
SOURCE_JAR := $(SOURCE_TARGET)/source.jar
SOURCE_SOURCES := $(shell find $(SOURCE_SRC) -type f -name *.java)
SOURCE_MAIN := it.polimi.project14.CivilProtectionSource

TESTS_DIR := $(JAVA_DIR)/Tests
TESTS_SRC := $(TESTS_DIR)/src
TESTS_LIB := $(TESTS_DIR)/lib
TESTS_BIN := $(TESTS_DIR)/bin
TESTS_TARGET := $(TESTS_DIR)/target
TESTS_JAR := $(TESTS_TARGET)/tests.jar
TESTS_SOURCES := $(shell find $(TESTS_SRC) -type f -name *.java)
TESTS_MAIN := it.polimi.project14.CivilProtectionTests

JUNIT_JAR := $(TESTS_LIB)/junit-4.13.jar
JUNIT_URL := https://search.maven.org/remotecontent?filepath=junit/junit/4.13/junit-4.13.jar
HAMCREST_JAR := $(TESTS_LIB)/hamcrest-core-1.3.jar
HAMCREST_URL := https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

# OTHER #######################################################################

join-cp = $(subst $(null) $(null),$(SEP),$(strip $1))

###############################################################################
#                                   RECEPIES                                  #
###############################################################################

.PHONY: \
all clean-java clean-uml common datetimepicker hamcrest junit libs pluntuml \
run-server run-source run-tests run-uber-server run-uber-source run-uber-user \
run-user server source sqlite tests uberjar uml user

all: uml common server user source

# DEPS ########################################################################

libs: plantuml datetimepicker hamcrest junit sqlite

plantuml: JAR := $(PLANTUML_JAR)
plantuml: URL := $(PLANTUML_URL)
plantuml: $(UML_LIB)
sqlite: JAR := $(SQLITE_JAR)
sqlite: URL := $(SQLITE_URL)
sqlite: $(SERVER_LIB)
junit: JAR := $(JUNIT_JAR)
junit: URL := $(JUNIT_URL)
junit: $(TESTS_LIB)
hamcrest: JAR := $(HAMCREST_JAR)
hamcrest: URL := $(HAMCREST_URL)
hamcrest: $(TESTS_LIB)
datetimepicker: JAR := $(DATETIMEPICKER_JAR)
datetimepicker: URL := $(DATETIMEPICKER_URL)
datetimepicker: $(USER_LIB)

plantuml sqlite junit hamcrest datetimepicker:
	wget -O $(JAR) "$(URL)"

# UML #########################################################################

uml: $(UML_TARGETS)

$(UML_TARGET)/%.$(UML_TARGET_EXT): $(UML_SRC)/%.$(UML_SRC_EXT) $(PLANTUML_JAR)
	mkdir -p $(@D)
	$(JAVA) -jar $(PLANTUML_JAR) $(PLANTUML_ARGS) <$< >$@

# Java ########################################################################

run-uber-server: $(SERVER_UBERJAR)
run-uber-user: $(USER_UBERJAR)
run-uber-source: $(SOURCE_UBERJAR)

run-uber-server run-uber-user run-uber-source:
	$(JAVA) -jar "$<"

uberjar: $(SERVER_UBERJAR) $(USER_UBERJAR) $(SOURCE_UBERJAR)

$(SERVER_UBERJAR) run-server: MAIN = $(SERVER_MAIN)
$(USER_UBERJAR) run-user: MAIN = $(USER_MAIN)
$(SOURCE_UBERJAR) run-source: MAIN = $(SOURCE_MAIN)
run-tests: MAIN = $(TESTS_MAIN)

$(SERVER_UBERJAR): $(SERVER_JAR) $(COMMON_JAR) $(SQLITE_JAR) | $(SERVER_UBERJAR_BIN)
$(USER_UBERJAR): $(USER_JAR) $(COMMON_JAR) $(DATETIMEPICKER_JAR) $(SQLITE_JAR) | $(USER_UBERJAR_BIN)
$(SOURCE_UBERJAR): $(SOURCE_JAR) $(COMMON_JAR) $(SQLITE_JAR) | $(SOURCE_UBERJAR_BIN)

$(SERVER_UBERJAR) $(USER_UBERJAR) $(SOURCE_UBERJAR):
	cp $^ $|
	cd $| && (for jar in $(^F) ; do jar xf "$${jar}" ; rm "$${jar}" ; done)
	jar cfe $@ $(MAIN) -C $| .

run-server: $(SERVER_JAR) $(SQLITE_JAR) $(COMMON_JAR)
run-user: $(USER_JAR) $(COMMON_JAR) $(DATETIMEPICKER_JAR) $(SQLITE_JAR)
run-source: $(SOURCE_JAR) $(COMMON_JAR) $(SQLITE_JAR)
run-tests: $(TESTS_JAR) $(SQLITE_JAR) $(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(USER_JAR) $(JUNIT_JAR) $(HAMCREST_JAR)

run-server run-user run-source run-tests:
	$(JAVA) -cp "$(call join-cp,$^)" $(MAIN)

common: $(COMMON_JAR)
server: $(SERVER_JAR)
user: $(USER_JAR)
source: $(SOURCE_JAR)
tests: $(TESTS_JAR)

$(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(TESTS_JAR): DATA_ARG =
$(USER_JAR): DATA_ARG = -C $(USER_DATA) .

$(COMMON_JAR): SOURCES = $(COMMON_SOURCES)
$(COMMON_JAR): $(COMMON_BIN) $(COMMON_TARGET)
$(SERVER_JAR): SOURCES = $(SERVER_SOURCES)
$(SERVER_JAR): $(SERVER_BIN) $(SERVER_TARGET) | $(COMMON_JAR)
$(USER_JAR): SOURCES = $(USER_SOURCES)
$(USER_JAR): $(USER_BIN) $(USER_TARGET) | $(COMMON_JAR) $(DATETIMEPICKER_JAR)
$(SOURCE_JAR): SOURCES = $(SOURCE_SOURCES)
$(SOURCE_JAR): $(SOURCE_BIN) $(SOURCE_TARGET) | $(COMMON_JAR)
$(TESTS_JAR): SOURCES = $(TESTS_SOURCES)
$(TESTS_JAR): $(TESTS_BIN) $(TESTS_TARGET) | $(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(USER_JAR) $(JUNIT_JAR) $(HAMCREST_JAR)

$(COMMON_JAR) $(SERVER_JAR) $(USER_JAR) $(SOURCE_JAR) $(TESTS_JAR):
	$(JAVAC) -d $< -cp "$(call join-cp,$|)" $(SOURCES)
	jar cf $@ -C $< . $(DATA_ARG)

# CLEAN #######################################################################

clean-uml:
	-rm -r $(UML_TARGET)

clean-java:
	-rm -r $(COMMON_BIN) $(COMMON_TARGET) $(SERVER_BIN) $(SERVER_TARGET) $(USER_BIN) $(USER_TARGET) $(SOURCE_BIN) $(SOURCE_TARGET) $(TESTS_BIN) $(TESTS_TARGET) $(JAVA_TARGET)

# DIRECTORIES #################################################################

$(TESTS_LIB) $(TESTS_BIN) $(TESTS_TARGET) $(UML_LIB) $(COMMON_BIN) $(COMMON_TARGET) $(SERVER_BIN) $(SERVER_TARGET) $(SERVER_LIB) $(USER_BIN) $(USER_TARGET) $(SOURCE_BIN) $(SOURCE_TARGET) $(USER_LIB) $(SERVER_UBERJAR_BIN) $(USER_UBERJAR_BIN) $(SOURCE_UBERJAR_BIN):
	mkdir -p $@
