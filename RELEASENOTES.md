## Version 1.4.2
* Add support of incremental build in annotation processor
* Raise `minSdkVersion` to `14`

## Version 1.4.1
* Fixed bug in `ValueMapperProcessor` when it can not run normally

## Version 1.4.0
* Fully migrated to Kotlin
* Added test support of Kotlin sources
* Fixed: `LogProcessor` logic now called even if logs are disabled
* Fixed: `LogProcessor` incorrect `message` parameter if log is too long

## Version 1.3.0
* New function: print lines where log called (`setPrintReferences` function)
* New function: `LogProcessor` class allows to add additional logic to every log call

## Version 1.2.6
* Print new line after function name if trying to print `Group` class
* Updated Bintray plugin
* Added Checkstyle rules
* Added `Group` test with function names

## Version 1.2.5
* Changed print function name with log. Now function name and log
  split by " -> " not by ":\n"

## Version 1.2.4
* Added MapFieldInner annotation to add custom fields into value mapper

## Version 1.1.6
* Fixed print of very big messages
* Trim TAGS to 23 symbols

## Version 1.1.5
* Fixed broken proguard rules
