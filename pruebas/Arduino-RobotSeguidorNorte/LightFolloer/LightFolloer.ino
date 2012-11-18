//esta aplicación usará una fotocelda para realizar
//un seguimiento a un gradiente de luz


// pwm de los motores
int pwmMotorD = 160;
int pwmMotorI = 160;

// Pines en uso
int pintMotorD=6;
int pintMotorI=5;

int M1 = 7;
int M2 = 4;

// Gradiente de la intensidad de luz que se desea seguir
int LuzRef = 150;

//Valores del control PID
float P;
float D;
float I;

//valor almacenado del loop anterior
float lastVal=0;

//contador para enumerar los datos de los ciclos loop
int cont=0;

float errorAcumulado = 0;

void setup(){
  Serial.begin(9600); // Initiate Serial
  pinMode(pintMotorI, OUTPUT);
  pinMode(pintMotorD, OUTPUT);
  analogWrite(pintMotorD, pwmMotorD);
  analogWrite(pintMotorI, pwmMotorI);
}

void loop(){
  
}