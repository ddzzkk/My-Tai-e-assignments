#!/bin/bash

# Loop through folders A1 to A8
for i in {1..8}; do
    folder="A$i"
    # Create the file if it doesn't exist and append the content
    echo 'rootProject.name = "tai-e-A'"$i"'"' > "$folder/tai-e/settings.gradle.kts"
done