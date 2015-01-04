ResinPrinterDemo
================

Initial exploration/write.  
Don't use, for historical purposes.  
Find the rewrite here:  
https://github.com/area515/Creation-Workshop-Host

(Very alpha) - This is an initial write of a host software for creation workshop.  This project is not in active development here.  

This is software for a raspberry pi to drive a resin printer.

Background - Why
Creation Workshop is an awesome tool, but is unable to run on a raspberry pi.  That's okay though!  The standalone host is a slimmed down version of the Creation Workshop software.  This software accepts the files exports from CreationWorkshop and uses those to drive the resin printing process.

But the raspberry pi has only one video output!
You will connect to your pi via a webpage (and rest api).  This is similiar to how you configure your router.   The raspberry pi will use the projector only to display the image slices for your printer.

Directions:
Release is tested on raspberry pi b with fresh raspbian wheezy install.  In testing, I assume the pi is only be used for the resin printer and the Raspbian OS is a new install.  

Your configuration steps:

Set your Raspian to startup the gui (startx) on boot without logging in.
Follow the "Download" step
Follow the "Run the install" step
Done!

Download latest release here:
<release>
Put the download at /home/pi/ or 

On your raspberry pi, run:
(script work in progress)
After install the cwhost on raspian startup
resources/rpiInstallInstructions

Manual startup:


Use it:
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

Developers - see resources/develop.README
