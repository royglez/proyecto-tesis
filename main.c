#include <stdio.h>
#include <stdlib.h>
int main(){
    float calificacion1,calificacion2,calificacion3,res,calificacion;
    printf("calificacion11.- ");
    scanf ("%d", &calificacion1);
    printf("calificacion22.- ");
    scanf ("%d", &calificacion2);
    printf("calificacion33.- ");
    scanf ("%d", &calificacion3);
    res=(calificacion1+calificacion2+calificacion3);
    printf("calificacion=%f",res);
    if(calificacion>=90 && calificacion<=100){
        printf("EXCELENTE!!!");
    }else if(calificacion<=90 && calificacion>=80){
        printf("BIEN!!!");
    }else if(calificacion<=80 && calificacion>=70){
        printf("INTERMEDIO!!!");
    }else if(calificacion<=70 && calificacion>=60){
        printf("MAL!!!");
    }else if(calificacion<=59 && calificacion>=0){
        printf("REPROBADO!!!");
    }
    system("pause");
    return 0;                       }
