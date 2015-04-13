Dropi Alone es una aplicación web y móvil (Android) cuyo objetivo es analizar la imagen de una hoja de una planta para obtener ciertos porcentajes de daños de la misma. La aplicación móvil permite la captura de la imagen de una hoja para su análisis, mientras que la aplicación web permite socializar de manera rápida los resultados de los análisis de las hojas capturadas por múltiples usuarios.

#Aplicación Móvil
La aplicación móvil se encarga de tomar la foto de la hoja de una planta e incluir información relacionada a la foto:
- Nombre de la planta
- Localización (urbana o rural)
- Estado de la hoja (desprendida o no desprendida de la planta)
- Latitud y longitud del sitio  en donde fue tomada la foto
- Fecha en la que fue tomada la foto

Todos estos datos son enviados a traves de un servicio RESTFULL cuya respuesta va a ser:
- Porcentaje de falta de clorofila
- Porcentaje de área de la hoja con orificios
- Porcentaje total de daño de la hoja
- Clasificación de daño por ozono

#Aplicación Web

Consiste en una página web en la cual se muestra un mapa en donde se establecen  marcadores en base a las fotos tomadas por los usuarios segun su localización. Adicionalmente, cada uno de estos marcadores despliega un popup donde se muestra la foto, el nombre de la planta, y los porcentajes de daño de la hoja. También muestra una tabla con toda la información de los marcadores y dispone una opción de filtros según el nombre de la planta, la región y la fecha en la que fue tomada la foto.

Adicionalmente, en el servidor donde se aloja la web app, se realiza el procesamiento de la imagen de la hoja mediante un algoritmo que delimita el área de la hoja, convirtiéndola en binario para determinar el área de la misma. Además se realiza una operación entre dos canales de color en la imagen para determinar la falta de clorofila. En el caso de que la hoja se haya desprendido de la planta, se realiza una identificación de puntos negros y puntos cafés para clasificar la hoja y obtener los porcentajes de daño respecto al área total de la hoja.

###Nota
En el servidor se necesita tener instalado Octave con las siguientes librerias:
- general-1.3.4.tar.gz
- control-2.8.0.tar.gz
- signal-1.3.1.tar.gz
- image-2.2.2.tar.gz
