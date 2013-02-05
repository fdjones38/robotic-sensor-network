/**
 * Esta aplicación usa una fotocelda para realizar
 * un seguimiento de una curva de nivel de un 
 * gradiente de luz generado por una bombilla incandecente.
 */
#include <XBee.h>

//Comunicacion
//ZIG-BEE: Direccion de 64 bits (Direccion del Coordinador)
XBeeAddress64 addr64 = XBeeAddress64(0x00000000, 0x00000000);
//ZIG-BEE: direccion de 16 bits
uint16_t addr16 = 0xFFFE;

//Paquete de datos a enviar
uint8_t payload[10];

XBee xbee = XBee();

//ZIG-BEE: Envio de datos
ZBTxRequest zbTx = ZBTxRequest(addr64, addr16, 0x00, 0x01, payload, sizeof(payload), 0x01);
//ZIG-BEE: Recepcion de datos
XBeeResponse response = XBeeResponse();
ZBRxResponse rx = ZBRxResponse();



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
int luzRef = 680;

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

  //parametros temporales para ser enviados
  uint32_t thP = (int) (P*100)+30000;
  uint32_t thErrorAc = (uint16_t) (errorAcumulado * 100) + 10000000;
  uint32_t thMotorD = (uint16_t) (motorD*100)+10000000;
  // ZIGBEE: Envia los parámetros deseados para ser graficado
  payload[0] = 0xFD;
  //byte que indica la cantidad de datos que se envairan fragmentados
  payload[1] = 0x04;
  payload[2] = 0;
    payload[3] = 0;
    payload[4] = luz >> 8 & 0xff;
  payload[5] = luz & 0xff;
  payload[6] = thP >> 24 & 0xff;
  payload[7] = thP >> 16 & 0xff;
  payload[8] = thP >> 8 & 0xff;
  payload[9] = thP & 0xff;
  payload[10] = thErrorAc >> 24 & 0xff;
  payload[11] = thErrorAc >> 16 & 0xff;
  payload[12] = thErrorAc >> 8 & 0xff;
  payload[13] = thErrorAc & 0xff;
  payload[14] = thMotorD >> 24 & 0xff;
  payload[15] = thMotorD >> 16 & 0xff;
  payload[16] = thMotorD >> 8 & 0xff;
  payload[17] = thMotorD & 0xff;

  xbee.send(zbTx);
  delay(100);

}