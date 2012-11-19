/**
 * Esta aplicación usa una brujula para contrlar por 
 * medio de un puente H un robot diferencial.
 *
 */

// Reading from HMC6352 and send it to serial
// need to the I2C Protocol used by the HMC6352.
#include <Wire.h>  

// PWM de los motores
int pwmMotorD = 160;
int pwmMotorI = 160;

// Pines en uso
int pinMotorD=6;
int pinMotorI=5;

int M1 = 7;
int M2 = 4;

// Angulo en el que se quiere dirigir.
int anguloRef = 120;

//Valores del control PID
float P;
float D;
float I;

// Valor almacenado de la iteración anterior
float lastVal=0;

// Contador para contar las iteraciones
int cont=0;
 
 
void setup(){
  // Iniciar la comunicación serial.
  Serial.begin(9600); 
  // Initiate I2C, No param means "join as master"
  Wire.begin();       
  
  // Declara los pines de salida para el puente H.
  pinMode(pinMotorI, OUTPUT);
  pinMode(pinMotorD, OUTPUT);

  // Declara las salidas de PWM para los motores.
  analogWrite(pinMotorD, pwmMotorD); 
  analogWrite(pinMotorI, pwmMotorI);    
}
 
void loop(){
  Wire.beginTransmission(0x21);
  Wire.write("A");     // Send "Get Data" command, info from Datasheet
  delay(100);         // interface command delay, info from Datasheet
  Wire.requestFrom(0x21, 2); //get the two data bytes, MSB and LSB
  byte MSB = Wire.read(); // Result will be in tenths of degrees (0 to 3599)
  byte LSB = Wire.read(); // Provided in binary format over two bytes."
  Wire.endTransmission();
  // Compute result from the two bytes results
  float grados = ((MSB << 8) + LSB) / 10;
  // Display result
  //Serial.print(myres);
  //Serial.println(" degrees");
  
  // Grados en sentido sur
  if(grados > 180){
    grados = grados - 360;
  }
  
  
  // El control proporcional es el numero de grados
  P = anguloRef - grados;
  I += P;  
  D =   P - lastVal;
  

  lastVal = P;
  
 
   float motorD = pwmMotorD - 1 * P - 6 * D - 0.2 * I;   
   //float motorD = pwmMotorD - 1 * P;   
   
     
   motorD = motorD > 255? 255 : motorD;
    motorD = motorD < 0? 0 : motorD;
    
   analogWrite(pinMotorD, motorD);       

  // Imprimir los parámetros del control PID:
  Serial.print(cont++);
  Serial.print(",");
  Serial.print(grados);
  Serial.print(",");
  Serial.print(D);
  Serial.print(",");
  Serial.print(I);  
  Serial.print(",");
  Serial.print(motorD);
  
  Serial.println();
  
  
  delay(100);
}
