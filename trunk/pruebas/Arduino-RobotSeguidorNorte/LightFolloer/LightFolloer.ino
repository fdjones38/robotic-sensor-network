/**
 * Esta aplicación usa una fotocelda para realizar
 * un seguimiento de una curva de nivel de un 
 * gradiente de luz generado por una bombilla incandecente.
 */

// PWM de los motores
int pwmMotorD = 160;
int pwmMotorI = 160;

// Pines para puente H
int pinMotorD = 5;		//motor Derecho
int pinMotorI = 6;		//motor Izquierdo

// Pines para salida PWM de motores.
int M1 = 7;
int M2 = 4;

// Pin para lectura de valor análogo de luz.
int pinAnalogo = A0;

// Gradiente de la intensidad de luz que se desea seguir
int luzRef = 830;

// Intensidad de luz medida
int luz = 0;

// Valores del control PID
float P;
float D;
float I;

// Valor almacenado de la iteración anterior
float lastVal = 0;

// Contador para contar las iteraciones
int cont = 0;

// Valor del motor que se va modificar dinámicamente.
float motorD;

// Error acumulado.
double errorAcumulado = 0;

// Numero de iteraciones para E.
int iteracionesE = 100;


void setup(){
  // Iniciar la comunicación serial.
  Serial.begin(9600); 

  // Declara los pines de salida para el puente H.
  pinMode(pinMotorI, OUTPUT);
  pinMode(pinMotorD, OUTPUT);

  // Declara las salidas de PWM para los motores.
  analogWrite(pinMotorD, pwmMotorD);
  analogWrite(pinMotorI, pwmMotorI);
}

void loop(){
  // Lee el valor de la fotocelda
  luz = analogRead(pinAnalogo);
  
   
  // Control PID.
  P = luz - luzRef;
  I += P;
  D =   P - lastVal;
  
  // Error acumulado
  if(cont < iteracionesE){
     errorAcumulado += abs(P);
  }
 
  //corrección de signo por análisis de recorridos
  motorD = pwmMotorD - 1 * P - 6 * D - 0 * I;  
  
  // Límites para el PWM.
  motorD = motorD > 255? 255 : motorD;
  motorD = motorD < 100? 100 : motorD;

  // Almacena el valor P actual
  lastVal = P;
  
  // Control PID
  analogWrite(pinMotorD, motorD);

  // Imprimir los parámetros del control PID:
  Serial.print(cont++);
  Serial.print(",");
  Serial.print(luz);
  Serial.print(",");
  Serial.print(P);
//  Serial.print(",");
//  Serial.print(D);
//  Serial.print(",");
//  Serial.print(I);  
  Serial.print(",");
  Serial.print(errorAcumulado);  
  Serial.print(",");  
  Serial.print(motorD);

  
  Serial.println();
  delay(100);
  
}
