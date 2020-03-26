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

diagrams: $(shell find $(DIAGRAMS_SRC_DIR) -type f -name *.puml)
	for puml in $?; do \
		target_dir=$$(dirname .$${puml#$(DIAGRAMS_SRC_DIR)}); \
		target_dir=$(DIAGRAMS_TARGET_DIR)/$${target_dir}; \
		target_file=$${target_dir}/$$(basename $$puml .puml).$(DIAGRAMS_EXT); \
		\
		mkdir -p "$${target_dir}"; \
		plantuml $(DIAGRAMS_ARG) -p <"$${puml}" >"$${target_file}"; \
	done

clean:
	rm -r $(BUILD_DIR)
