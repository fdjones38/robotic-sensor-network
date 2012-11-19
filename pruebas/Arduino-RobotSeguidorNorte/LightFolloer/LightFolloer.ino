//esta aplicación usará una fotocelda para realizar
//un seguimiento a un gradiente de luz


// pwm de los motores
int pwmMotorD = 160;
int pwmMotorI = 160;

// Pines en uso
int pintMotorD=6;		//motor Derecho
int pintMotorI=5;		//motor Izquierdo

int M1 = 7;
int M2 = 4;

// Gradiente de la intensidad de luz que se desea seguir
int luzRef = 150;

//Intensidad de luz medida
int luz = 0;

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
  Serial.begin(9600); // Inicializo la comunicación serial
  pinMode(pintMotorI, OUTPUT);
  pinMode(pintMotorD, OUTPUT);
  analogWrite(pintMotorD, pwmMotorD);
  analogWrite(pintMotorI, pwmMotorI);
}

void loop(){
  //leemos el valor de la fotocelda
  luz = analogRead(A0);
  
  //asignamos signos (en caso de ser necesario)
  if(luz<luzRef){
	/////operaciones
  }else{
	/////operaciones
  }
  
  //calculamos el calculo de errores con el valor de referencia para el control PID
  P = luzRef - luz;
  I += P;
  D =   P - lastVal;
  float motorD = pwmMotorD - (1 * P) - (6 * D) - (0.2 * I);
    
  //almacenamos el valor P actual
  lastVal = P;
  
  //control PID
  analogWrite(pintMotorD, motorD);
  
}
