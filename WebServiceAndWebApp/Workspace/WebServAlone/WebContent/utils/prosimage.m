function [p_total, p_bajo, p_alto, out] = prosimage(filepath,t)
%t=0;
pkg load image
 foto=imread(filepath);   %Activar para correr en servidor

%foto=imread('cla10.JPG');  %Desactivar para correr en servidor
RG=foto(:,:,1)-foto(:,:,2);   %Canal R-G, contrasta las reflectancias para aproximar la deteccion de danos en las hojas
 %imshow(RG)
RGu = im2bw(RG, 0.10);   %binariza RG con tolerancia 0.12
% imshow(RGu)
 
 

 RGc = im2bw(RG, 0.2);   %binariza RG con tolerancia 0.5 para detectar circulos cafes
% imshow(RGc)
 cl9=sum(sum(RGc));

 RGb = im2bw(foto(:,:,3), 0.02);   %binariza RG con tolerancia 0.5 para detectar circulos negros
%imshow(RGb)
RGbb=ones(size(foto(:,:,3)))-RGb; 
%imshow(RGbb)
 cl10=sum(sum(RGbb));
 
 
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

 cl9_p=(cl9/total)*100;
  cl10_p=(cl10/total)*100;
 
  
 afectado_alto=total-h;  %calcula el area de afectacion alta (huecos)
 p_bajo=(afectado_bajo/total)*100    
 p_alto=(afectado_alto/total)*100
 p_total=(p_bajo+p_alto)
 % end
if t==1

%if cl9_p>1
%out='clase9';
%else
%  if cl10>0.2 || p_bajo>80
%  out='clase10';
%  else 
%    if p_bajo>4
%     out='clase8' ;
%     end
%  end
%  out='clase7';
%end
if cl10_p>0.6 || p_bajo>91
out=im2double(10);
elseif p_bajo>15
   out=8 ;
elseif cl9_p>1
   out=9 ;
else
   out=7 ;

end
   
else

if p_bajo>=0 & p_bajo<=0.9
out=1;
end 
if p_bajo>0.9 & p_bajo<=6
out=2;
end 
if p_bajo>6 & p_bajo<=25
out=3;
end 
if p_bajo>25 & p_bajo<=50
out=4;
end 
if p_bajo>50 & p_bajo<=75
out=5;
end 

if p_bajo>75 & p_bajo<=100
out=6;
end 

end

out


