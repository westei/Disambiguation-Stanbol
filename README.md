README
=======
To install this Engine. 
Maven should be installed.

 dir/: the directory where it was code is present

1. Have a Stanbol running. 
2. Open terminal and go to /dir/.
3. run:  mvn clean compile install
4. Open a browser and open link  http://localhost:8080/system/console/bundles . 
5. then click install/Update button
6. on the pop up, check start bundle, click on browse and go to directory, select /dir/target/org.apache.stanbol.enhancer.engine.disambiguation.mlt-0.0.1-SNAPSHOT.jar  

you can see that entity disambiguation engine is installed.
