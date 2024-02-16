# Java-Selenium-Cucumber-Test-Framework

Framework para pruebas con Selenium/Cucumber Codigo Java y Maven (Udemy 2024)


#Para correr pruebas Web usando la suite de TestNG

**`mvn clean test -DsuiteFile='WebTestKlimberRunnerNoParams.xml'`**

**`mvn clean test -DsuiteFile='WebTestKlimberRunner.xml'`**

#Para correr pruebas Mobile  usando la suite de TestNG
**`mvn clean test -DsuiteFile='MobileTestYapeRunner.xml'`**

Aclaración: deben tener instalado un dispositivo movil (virtual o fisico), esto puede hacerse usando Android Virtual Devices de android Studio (https://developer.android.com/studio?_gl=1*1id4xbg*_up*MQ..&gclid=CjwKCAiAibeuBhAAEiwAiXBoJIZkSEqnJmJO-T3L5EUMKzQzyKxbvBXuZ1ACi1YI9if4h83yl5a9ABoCg_IQAvD_BwE&gclsrc=aw.ds)
y tambien debe instalarse Appium desktop o Appium server (https://appium.io/docs/en/2.0/quickstart/install/)


#Para correr pruebas Api
**`mvn clean test -DsuiteFile='ApiTestPetStoreRunnerNoParams.xml'`**
