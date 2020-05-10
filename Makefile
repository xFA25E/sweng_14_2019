###############################################################################
#                            TOP-LEVEL DIRECTORIES                             #
###############################################################################

BUILD_DIR := build
DOCS_DIR := docs
JAVA_DIR := src

###############################################################################
#                                   DIAGRAMS                                  #
###############################################################################

DIAGRAMS_SRC_DIR := $(DOCS_DIR)/diagrams
DIAGRAMS_BUILD_DIR := $(BUILD_DIR)/diagrams

DIAGRAMS_EXT := png
ifeq ($(DIAGRAMS_EXT), tex)
	DIAGRAMS_ARG := -tlatex
else
	DIAGRAMS_ARG := -t$(DIAGRAMS_EXT)
endif

DIAGRAMS_SOURCES := $(shell find $(DIAGRAMS_SRC_DIR) -type f -name *.puml)
PLANTUML := plantuml.jar
PLANTUML_JAR := build/$(PLANTUML)

###############################################################################
#                                     JAVA                                    #
###############################################################################

JAVA_TARGET_DIR := $(BUILD_DIR)/java
JAVA_SOURCES := $(shell find $(JAVA_SRC_DIR) -type f -name *.java)

SQLITE := sqlite-jdbc-3.30.1.jar
SQLITE_JAR := $(JAVA_TARGET_DIR)/$(SQLITE)

###############################################################################
#                                   RECEPIES                                  #
###############################################################################

# JAVA ########################################################################

run: java $(SQLITE_JAR)
	java -classpath '$(JAVA_TARGET_DIR):$(SQLITE_JAR)' it.polimi.project14.CivilProtection server

java: $(JAVA_SOURCES) $(JAVA_TARGET_DIR)
	javac -d $(JAVA_TARGET_DIR) $(JAVA_SOURCES)

# DIAGRAMS ####################################################################

diagrams: $(DIAGRAMS_SOURCES) $(PLANTUML_JAR)
	for puml in $(DIAGRAMS_SOURCES); do \
		target_dir=$$(dirname .$${puml#$(DIAGRAMS_SRC_DIR)}); \
		target_dir=$(DIAGRAMS_TARGET_DIR)/$${target_dir}; \
		target_file=$${target_dir}/$$(basename $$puml .puml).$(DIAGRAMS_EXT); \
		\
		mkdir -p "$${target_dir}"; \
		java -jar $(PLANTUML_JAR) $(DIAGRAMS_ARG) -p <"$${puml}" >"$${target_file}"; \
	done

# DEPENDENCIES ################################################################

sqlite: $(JAVA_TARGET_DIR)
	wget -O $(SQLITE_JAR) "https://bitbucket.org/xerial/sqlite-jdbc/downloads/$(SQLITE)"

plantuml: $(BUILD_DIR)
	wget -O $(PLANTUML_JAR) "https://sourceforge.net/projects/plantuml/files/$(PLANTUML)/download"

# DIRECTORIES #################################################################

$(JAVA_TARGET_DIR):
	make -p $(JAVA_TARGET_DIR)

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

# CLEAN #######################################################################

clean:
	rm -r $(BUILD_DIR)
