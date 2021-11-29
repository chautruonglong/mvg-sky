#!/bin/bash

find . -type d ! -path "*.git*" -empty -exec touch '{}'/.gitkeep \;
