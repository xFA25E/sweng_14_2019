BUILD_DIR := build
DOCS_DIR := docs

DIAGRAMS_SRC_DIR := $(DOCS_DIR)/diagrams
DIAGRAMS_TARGET_DIR := $(BUILD_DIR)/diagrams
DIAGRAMS_EXT := png
ifeq ($(DIAGRAMS_EXT), tex)
	DIAGRAMS_ARG := -tlatex
else
	DIAGRAMS_ARG := -t$(DIAGRAMS_EXT)
endif
DIAGRAMS_SOURCES := $(shell find $(DIAGRAMS_SRC_DIR) -type f -name *.puml)
PLANTUML_JAR := build/plantuml.jar

diagrams: $(DIAGRAMS_SOURCES)
	for puml in $(DIAGRAMS_SOURCES); do \
		target_dir=$$(dirname .$${puml#$(DIAGRAMS_SRC_DIR)}); \
		target_dir=$(DIAGRAMS_TARGET_DIR)/$${target_dir}; \
		target_file=$${target_dir}/$$(basename $$puml .puml).$(DIAGRAMS_EXT); \
		\
		mkdir -p "$${target_dir}"; \
		java -jar $(PLANTUML_JAR) $(DIAGRAMS_ARG) -p <"$${puml}" >"$${target_file}"; \
	done

plantuml: $(BUILD_DIR)
	wget -O $(PLANTUML_JAR) "https://sourceforge.net/projects/plantuml/files/plantuml.jar/download"

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

clean:
	rm -r $(BUILD_DIR)
