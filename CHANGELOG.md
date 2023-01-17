
# Change Log

## Version 2.1.0

* The Builder plugin integrates with the ALM Client RestAPI Plugin which allows the IKAN ALM connection configuration to be defined in Configure System screen with Environment variables parameters.
    - almBaseUrl      (mandatory)
    - almSystem_location  (mandatory)
    - almRestUserid       (optional. Preferred in the Project)
    - almRestPassword (optional. Preferred in the Project)
* The Global Tools Configuration would contain an ANT and JDK 11 definitions which will be set in the Pipeline’s groovy scripts for running plugin step(s).
Pipeline samples are available in the [GitHub project](https://github.com/jenkinsci/ikan-almclient-plugin) Pipelines folder.
* Support for the Builder Pipeline Syntax and pipeline models are available for each task.
* Jenkins console logs produced by the plugin have been streamlined to improve readability.
