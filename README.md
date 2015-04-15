Dropi Alone is a web and mobile-based App for ground ozone leaf injury analysis. The app allows you to take a photo of a leaf and share your data in order to be analysed and published in a crowsourced map.

#Mobile App
The Mobile App allows you to take a leaf's photo, then, this app gives it an ID and ask the user to fill a form wich includes:
 - Crop name
 - Dropped Leaf? 
- Zone: Urban or Rural? 
Morover the app automaticaly add the date, time and GPS location. Finally the App sends the data to the Web Server wich analyse and respond with:
 - % Ozone injury 
- % Holes in the leaf ( only if the user want to know) 
- % Total Injury 
- Classification (based on ....)

Notes: Android compatible

#Web App
The Web app displays the data of every user with pins in a simple map.  You can click the pin and access the photo and the data of every survey.  Also it shows a historic survey's data and let you filter by: date, zone, crop name, etc

#Server
In addition of supporting the web and mobile app, the server interact with an image processing algorith written in Octave.  The algorith separate the channels and operate them based on the difference of light reflectance on the leaf. First identify the leaf, calculate the area and then identify the %ozone injury, the presence of black dots, brown dots and holes on the leaf.
 
##Notes:  
The server needs:
- Octave 3.8 and libraries (general-1.3.4.tar.gz / control-2.8.0.tar.gz / signal-1.3.1.tar.gz / image-2.2.2.tar.gz)

##Contributors
[Fernando Ledesma](https://github.com/f3r10)
[José Albarado](https://github.com/josepepe91)
[Jonathan Morocho](https://github.com/jmorocho)
[Nicolás Magne Tang](https://github.com/nicomagnet)
[José Barreiros](https://github.com/JoseBarreiros)
