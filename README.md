# http-sample-app #

Welcome to http-sample-app!

# App

1. Unzip it somewhere and `cd` in.
2. `sbt assembly` will create jar in target/scala-2.11
3. 

To run it with default http interface and port (0.0.0.0:9000):

`java -jar target/scala-2.11/http-sample-app-assembly-98f01a856857603bd2678024973f828e19353f06-SNAPSHOT.jar`

To override it:

java -Dhttp.port=9001 -Dhttp.interface=localhost -jar target/scala-2.11/http-sample-app-assembly-98f01a856857603bd2678024973f828e19353f06-SNAPSHOT.jar

4. Check it works:

`curl http://127.0.0.1:9000/random1`

it should give back: {"n":0.2762890765327731} - every time a new random value

# Docker

## Build the image
`docker build --build-arg app_version=98f01a856857603bd2678024973f828e19353f06-SNAPSHOT -t http-image .`

## Start container
`docker run -e http_interface=0.0.0.0 -e http_port=9000 -p 9000:9000 --name http-container http-image`

## Kill container
`rm -f -v http-container`


## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original
author. Along with any pull requests, please state that the contribution is your
original work and that you license the work to the project under the project's
open source license. Whether or not you state this explicitly, by submitting any
copyrighted material via pull request, email, or other means you agree to
license the material under the project's open source license and warrant that
you have the legal authority to do so.

## License ##

This code is open source software licensed under the
[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) license.
