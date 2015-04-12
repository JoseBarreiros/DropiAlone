function [p_bajo, p_alto, p_total] = prosimage(filepath)

pkg load image
foto=imread(filepath);   %Activar para correr en servidor
% foto=imread('imagenp.JPG');  %Desactivar para correr en servidor
RG=foto(:,:,1)-foto(:,:,2);   %Canal R-G, contrasta las reflectancias para aproximar la deteccion de danos en las hojas
% imshow(RG)
RGu = im2bw(RG, 0.10);   %binariza RG con tolerancia 0.12
% imshow(RGu)
afectado_bajo=sum(sum(RGu));  %calcula el area con afectacion baja. suma los blancos(1)  

BW = im2bw(foto(:,:,3), 0.5);   %binariza Blue con tolerancia 0.5
%imshow(BW)
BWh=1-BW;     %negativo de BW
%imshow(BWh)
h=sum(sum(BWh));    %suma las areas de todas las hojas sin contar los huecos

 BW5 = bwfill(BWh,'holes');   %rellena los huecos de todas las hojas para obtener el area total
% imshow(BW5)
 total=sum(sum(BW5));   %calcula el area total de la hoja
% temp=BW5-BWh;     %muestra la imagen de afectacion alta
%  imshow(temp)
 afectado_alto=total-h;  %calcula el area de afectacion alta (huecos)
 p_bajo=(afectado_bajo/total)*100    
 p_alto=(afectado_alto/total)*100
 p_total=(p_bajo+p_alto)
 % end
 
 



