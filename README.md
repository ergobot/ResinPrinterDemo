ResinPrinterDemo
================

This is software for a raspberry pi to drive a resin printer.

Background - Why
Creation Workshop is an awesome tool, but is unable to run on a raspberry pi.  That's okay though!  The standalone host is a slimmed down version of the Creation Workshop software.  This software accepts the files exports from CreationWorkshop and uses those to drive the resin printing process.

But the raspberry pi has only one video output!!
You will connect to your pi via a webpage.  (This is similiar to how you configure your router.)   The raspberry pi will display the image to the projector.

Directions:

Download latest release here:
<release>

On your raspberry pi, run:
sudo apt-get install librxtx-java
java -Djava.library.path=/usr/lib/jni -cp /usr/share/java/RXTXcomm.jar -jar CwHost.jar


Find the ip of your raspberry pi

Open a browser go to the following webpage:
<needs webclient> static content

Or 
Open a browser, postman, etc...
GET
http://raspberrypi:8080/cwhost/machine/motorsoff
http://raspberrypi:8080/cwhost/machine/morotson
http://raspberrypi:8080/cwhost/machine/status
http://raspberrypi:8080/cwhost/machine/movez/-10
http://raspberrypi:8080/cwhost/machine/movez/-1
http://raspberrypi:8080/cwhost/machine/movez/-.025
http://raspberrypi:8080/cwhost/files/list
http://raspberrypi:8080/application.wadl
POST -form-data - file
http://raspberrypi:8080/cwhost/files/upload





