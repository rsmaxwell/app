# app

The app is a command line front end to the `model` project which is a simple SIR compartmental model which 
predicts the progress of an infection in a set of populations. 


## Run

Drop this Maven project into Eclipse and build. Then run as:

java -cp ${CLASSPATH} com.rsmaxwell.infection.app.App -c {filename}

Where {filename} is a configuration file in json format. 

A call is made to the `model` project which runs the SIR model and returns a results stream 
in the requested format.

The `model` needs a configuration to define the SIR model and the format of the results stream which is returned:

Example simple configuration:

``` json
{
   "maxTime":20,
   "groups":{
      "1":{
         "name":"everyone",
         "recovery":0.23,
         "population":10,
         "iStart":0.01
      }
   },
   "connectors":{
      "1.1":{
         "transmission":3.2
      }
   },
   "output": {
      "format": "jpeg",
      "filter": [ "All" ],
      "width": 800,
      "height": 400
   } 
}
```




## Results 

When the model is run, a population is generated based on each group. The SIR model is run from time = 0 to the maxTime and the values of 
the Susceptible, Infected and Recovered are stored as Results for each Population
   

![example output](doc/images/SIR-output.png)


