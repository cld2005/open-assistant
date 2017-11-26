PROTOC = protoc
PROTOC_DIR = ../message
PROTOC_FILES = message.proto
PROTO_TARGET_DIR = ./src/main/java
PROTO_TARGET_SUBDIR = $(PROTO_TARGET_DIR)/protobuf

.PHONY: clean

all: clean
	mkdir $(PROTO_TARGET_SUBDIR)
	$(PROTOC) -I$(PROTOC_DIR) $(PROTOC_FILES) --java_out=$(PROTO_TARGET_DIR)
	mvn package

clean:
	rm -fr $(PROTO_TARGET_SUBDIR)
	mvn clean
