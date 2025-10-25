# Gradle Plugin for Blue Cave

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Publish to Gradle Plugins](https://github.com/bluecave-toolbox/bluecave-gradle-plugin/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/bluecave-toolbox/bluecave-gradle-plugin/actions/workflows/gradle-publish.yml)

A Gradle plugin that provides a wrapper for Blue Cave's CLI, making it easier to integrate Blue Cave
into your Gradle projects.

## Usage
Add the plugin to your `build.gradle` file:
```groovy
plugins {
    ...
    id "io.bluecave.plugin" version "0.1.1"
}
```
If using Kotlin:
```kotlin
plugins {
    ...
    id("io.bluecave.plugin") version "0.1.1"
}
```

You can now use the task `bluecaveReport` to analyse and report coverage to your Blue Cave project:
```shell
export BLUECAVE_TOKEN="<your project token>" # Please keep this a secret!
# The following is only required if running outside of GitHub Actions:
# export BLUECAVE_EXTRA_OPTS="-b <branch name, such as main> -c <commit hash to attribute this analysis to>"
# See https://docs.bluecave.io/languages/java/ for more information. 
./gradlew bluecaveReport
```

If you run into any trouble, please open an issue :)

## Contributing
Pull requests are welcome! For major changes, please open an issue to discuss your change first.

## License
[MIT](https://github.com/bluecave-toolbox/bluecave-gradle-plugin/blob/main/LICENSE).
