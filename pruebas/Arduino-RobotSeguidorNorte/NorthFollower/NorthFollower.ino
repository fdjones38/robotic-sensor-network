/**
 * Esta aplicacin usa una brujula para contrlar por 
 * medio de un puente H un robot diferencial.
 *
 */


// pwm de los motores
int pwmMotorD = 160;
int pwmMotorI = 160;

// Pines en uso
int pintMotorD=6;
int pintMotorI=5;

int M1 = 7;
int M2 = 4;

boolean dir;

// Angulo en el que se quiere dirigir.
int anguloRef = 120;


float P;
float D;
float I;

float lastVal=0;

int cont=0;

double errorAcumulado;


// Reading from HMC6352 and send it to serial
#include <Wire.h>     // need to the I2C Protocol used by the HMC6352
 
void setup(){
  Serial.begin(9600); // Initiate Serial
  Wire.begin();       // Initiate I2C, No param means "join as master"
  
  // declare pin 9 to be an output:
  pinMode(pintMotorI, OUTPUT);
  pinMode(pintMotorD, OUTPUT);
  analogWrite(pintMotorD, pwmMotorD); 
  analogWrite(pintMotorI, pwmMotorI);    
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
    
   analogWrite(pintMotorD, motorD);       

   Serial.print(cont++);
   Serial.print(",");
   Serial.print(grados);
  // Serial.print(" P=");  
  // Serial.print(P);
   Serial.print(",");
   Serial.print(D);
   Serial.print(",");
   Serial.print(I);
   
   Serial.print(",");
   Serial.print(motorD);
   // Serial.print(" ");
  
  Serial.println();
  
  
  delay(100);
}
