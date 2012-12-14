#include <XBee.h>

/*
Programa de prueba para enviar un paquete de datos al Modulo Xbee configurado
como coordinador.
**/

//objeto XBee para realizar la comunicacion
XBee xbee = XBee();
//Datos que serán enviados.
uint8_t payload[] = { 'H', 'i' };
//direccion de 16 bits
uint16_t addr16 = 0xFFFE;
//Direccion de 64 bits (Direccion del Coordinador)
XBeeAddress64 addr64 = XBeeAddress64(0x00000000, 0x00000000);
//Se crea el paquete para ser enviado
//ZBTxRequest(XBeeAddress64 &addr64, uint16_t addr16, uint8_t broadcastRadius, uint8_t option, uint8_t *payload, uint8_t payloadLength, uint8_t frameId)
ZBTxRequest zbTx = ZBTxRequest(addr64, addr16, 0x00, 0x01, payload, sizeof(payload), 0x01);

void setup(){
  xbee.begin(9600);  
  pinMode(13, OUTPUT);
}

void loop(){
  //Led que indica envío de paquete
  digitalWrite(13, HIGH);
  //Se envía el paquete
  xbee.send(zbTx);
  delay(100);
  digitalWrite(13, LOW);
  delay(2000);
}
