# sdirobot
# Project sdirobot:	"E-home indor Robotic Assistant"
## Goal:	Study RPI and HW involved programming
## Base:	Raspberry Pi and sensor modules
## OS:	Linux(Debian Wheezy) sep-2014

###### Content
**Additional Software and libs:**	
>0. **pi4j** install using sudo dpkg -i pi4j-1.1.deb or see http://pi4j.com/install.html
>1.	**wiringpi** Gordons library to talk with pins and devices on RaspberryPi
>2.	**Festival** is an text to speech engine
>3.	**Pocketsphinx** is an Speech Recognition engine
>4. **Postgresql** ver 9.3 on pi install
>5. **sqllite** used by default database engine as **inmemory** see https://bitbucket.org/xerial/sqlite-jdbc

**Hardware:**	
>1.	RPI 3 Master
>1.1.	Camera Module Board REV 1.3 5MP Webcam Video 1080p OV5647 For Raspberry Pi
>1.2.	USB sound card
>1.3.	USB wifi as AP
>1.4.	gprs module
>1.5.	DS1302
>1.6.	solar panel with LiO charger load circuit
>1.7.	LM2596 dc-dc step down module
>2.	RPI Model B 2 rev 2 as slave system
>2.1.	USB wifi
>2.2.	LM2596 dc-dc step down module
>2.3.	mcp 23017 E/SP dip 28
>2.4.	some uln2003 based drivers
>2.5.	Gear Stepper Motors DC 5V 4 Phase
>2.6.	Ultrasonic Module HC-SR04 Distance Measuring Transducer Sensor
>2.7.	gyro module
>3.	a couple of jumpers,cables,screws,nuts and so on :)...
>4.		... _more will be described here later_ ...

>**Description:**
<pre>
Actually model as i view it in my mind consist of two or more rpi's 
Master and slaves (like brain and ganglies or aux brains) - in the future i will be able to grow it and made grid by adding one more rpi
Booth runs same tomcat application but in different mode and interacts via http or via rs232 protocol with MAX232 seria chip
Master will drive entire "organism" and since it stronger - will do some opencv and text to speach stuff
Slaves will operate sensors ,motors ,buttons and leds as pin or i2c connected devices.
Actually it is more likely relation CNS ->(sensor ,motor->(somatic , autonomous)) divisions
</pre>
![NS Schema](https://github.com/dimasalomatine/sdirobot/blob/master/doc/NSdiagram.svg.png)

>**Requested Abilities:**
>1.	Indor movement with less or more accepted obstacle avoidance
>2.	Recognize faces ,objects etc at least known or prelearned
>3.	Metric distance by Ultrasonic module
>4.	Speak and listen for known commands
>5.	Simple AI script based with decision tree aproach
>6. Master can initiate,execute,control and inspect commandset and MPI functionality on slaves 
>7. Manage and control via mobile/web application

>Come here more often to keep up to date :)
>Also visit  [my site](hellcreator.esy.es)
>_Yours_
_Dmitry Salomatine aKa Hellcreator_

