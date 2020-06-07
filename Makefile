###############################################################################
#                                  VARIABLES                                  #
###############################################################################

# UML #########################################################################

UML_DIR := UML
UML_SRC := $(UML_DIR)/src
UML_SRC_EXT := puml
UML_TARGET := $(UML_DIR)/target
UML_TARGET_EXT := png
UML_SOURCES := $(shell find $(UML_SRC) -type f -name *.$(UML_SRC_EXT))
UML_TARGETS := $(UML_SOURCES:$(UML_SRC)/%.$(UML_SRC_EXT)=$(UML_TARGET)/%.$(UML_TARGET_EXT))

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
JAVA_BIN := $(JAVA_DIR)/bin
JAVA_TARGET := $(JAVA_DIR)/target
SERVER_UBERJAR := $(JAVA_TARGET)/server-uberjar.jar
USER_UBERJAR := $(JAVA_TARGET)/user-uberjar.jar
SOURCE_UBERJAR := $(JAVA_TARGET)/source-uberjar.jar

COMMON_DIR := $(JAVA_DIR)/Common
COMMON_JAR := $(COMMON_DIR)/target/common.jar
COMMON_SOURCES := $(shell find $(COMMON_DIR)/src -type f -name *.java)

SERVER_DIR := $(JAVA_DIR)/Server
SERVER_JAR := $(SERVER_DIR)/target/server.jar
SERVER_SOURCES := $(shell find $(SERVER_DIR)/src -type f -name *.java)
SERVER_MAIN := it.polimi.project14.CivilProtectionServer

USER_DIR := $(JAVA_DIR)/User
USER_JAR := $(USER_DIR)/target/user.jar
USER_SOURCES := $(shell find $(USER_DIR)/src -type f -name *.java)
USER_MAIN := it.polimi.project14.CivilProtectionUser

SOURCE_DIR := $(JAVA_DIR)/Source
SOURCE_JAR := $(SOURCE_DIR)/target/source.jar
SOURCE_SOURCES := $(shell find $(SOURCE_DIR)/src -type f -name *.java)
SOURCE_MAIN := it.polimi.project14.CivilProtectionSource

TESTS_DIR := $(JAVA_DIR)/Tests
TESTS_JAR := $(TESTS_DIR)/target/tests.jar
TESTS_SOURCES := $(shell find $(TESTS_DIR)/src -type f -name *.java)
TESTS_MAIN := it.polimi.project14.CivilProtectionTests

# DEPS ########################################################################

PLANTUML_JAR := $(UML_DIR)/lib/plantuml.jar
PLANTUML_URL := https://sourceforge.net/projects/plantuml/files/plantuml.jar/download

SQLITE_JAR := $(SERVER_DIR)/lib/sqlite-jdbc-3.30.1.jar
SQLITE_URL := https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.30.1.jar

DATETIMEPICKER_JAR := $(USER_DIR)/lib/LGoodDatePicker-10.4.1.jar
DATETIMEPICKER_URL := https://github.com/LGoodDatePicker/LGoodDatePicker/releases/download/v10.4.1-Standard/LGoodDatePicker-10.4.1.jar

JUNIT_JAR := $(TESTS_DIR)/lib/junit-4.13.jar
JUNIT_URL := https://search.maven.org/remotecontent?filepath=junit/junit/4.13/junit-4.13.jar

HAMCREST_JAR := $(TESTS_DIR)/lib/hamcrest-core-1.3.jar
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

.SECONDEXPANSION:

all: uml common server user source

# UML #########################################################################

uml: $(UML_TARGETS)

$(UML_TARGET)/%.$(UML_TARGET_EXT): $(UML_SRC)/%.$(UML_SRC_EXT) | $(PLANTUML_JAR) $$(@D)/
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

$(SERVER_UBERJAR): $(SERVER_JAR) $(COMMON_JAR) | $(SQLITE_JAR)
$(USER_UBERJAR): $(USER_JAR) $(COMMON_JAR) | $(DATETIMEPICKER_JAR) $(SQLITE_JAR)
$(SOURCE_UBERJAR): $(SOURCE_JAR) $(COMMON_JAR) | $(SQLITE_JAR)

$(SERVER_UBERJAR) $(USER_UBERJAR) $(SOURCE_UBERJAR): | $$(@D)/
	-rm -r $(JAVA_BIN)
	mkdir -p $(JAVA_BIN)
	cp $^ $(filter %.jar,$|) $(JAVA_BIN)
	cd $(JAVA_BIN) && (for jar in $(^F) $(notdir $(filter %.jar,$|)) ; do jar xf "$${jar}" ; rm "$${jar}" ; done)
	jar cfe $@ $(MAIN) -C $(JAVA_BIN) .

run-server: $(SERVER_JAR) $(COMMON_JAR) | $(SQLITE_JAR)
run-user: $(USER_JAR) $(COMMON_JAR) | $(DATETIMEPICKER_JAR) $(SQLITE_JAR)
run-source: $(SOURCE_JAR) $(COMMON_JAR) | $(SQLITE_JAR)
run-tests: $(TESTS_JAR) $(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(USER_JAR) | $(JUNIT_JAR) $(SQLITE_JAR) $(HAMCREST_JAR)

run-server run-user run-source run-tests:
	$(JAVA) -cp "$(call join-cp,$^ $|)" $(MAIN)

common: $(COMMON_JAR)
server: $(SERVER_JAR)
user: $(USER_JAR)
source: $(SOURCE_JAR)
tests: $(TESTS_JAR)

$(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(TESTS_JAR): DATA_ARG =
$(USER_JAR): DATA_ARG = -C $(USER_DIR)/data .

$(COMMON_JAR): SOURCES = $(COMMON_SOURCES)
$(SERVER_JAR): SOURCES = $(SERVER_SOURCES)
$(SERVER_JAR): $(COMMON_JAR)
$(USER_JAR): SOURCES = $(USER_SOURCES)
$(USER_JAR): $(COMMON_JAR) $(DATETIMEPICKER_JAR)
$(SOURCE_JAR): SOURCES = $(SOURCE_SOURCES)
$(SOURCE_JAR): $(COMMON_JAR)
$(TESTS_JAR): SOURCES = $(TESTS_SOURCES)
$(TESTS_JAR): $(COMMON_JAR) $(SERVER_JAR) $(SOURCE_JAR) $(USER_JAR) | $(JUNIT_JAR)

$(COMMON_JAR) $(SERVER_JAR) $(USER_JAR) $(SOURCE_JAR) $(TESTS_JAR): | $$(addprefix $$(dir $$(@D)),bin/ target/)
	$(JAVAC) -d $(firstword $|) -cp "$(call join-cp,$^ $(filter %.jar,$|))" $(SOURCES)
	jar cf $@ -C $(firstword $|) . $(DATA_ARG)

# DEPS ########################################################################

$(PLANTUML_JAR): URL := $(PLANTUML_URL)
$(SQLITE_JAR): URL := $(SQLITE_URL)
$(JUNIT_JAR): URL := $(JUNIT_URL)
$(HAMCREST_JAR): URL := $(HAMCREST_URL)
$(DATETIMEPICKER_JAR): URL := $(DATETIMEPICKER_URL)

$(PLANTUML_JAR) $(SQLITE_JAR) $(JUNIT_JAR) $(HAMCREST_JAR) $(DATETIMEPICKER_JAR): | $$(@D)/
	wget -O $@ "$(URL)"

# CLEAN #######################################################################

clean-uml:
	-rm -r $(UML_TARGET)

clean-java:
	-rm -r $(foreach pref,$(COMMON_DIR) $(SERVER_DIR) $(USER_DIR) $(SOURCE_DIR) $(TESTS_DIR),$(addprefix $(pref)/,bin/ target/)) $(JAVA_BIN) $(JAVA_TARGET)

# DIRECTORIES #################################################################
.PRECIOUS: %/
%/:
	mkdir -p $@
