
/* Include the HCPCA9685 library */
#include "HCPCA9685.h"


/* I2C slave address for the device/module. For the HCMODU0097 the default I2C address
   is 0x40 */
#define  I2CAdd 0x40


/* Create an instance of the library */
HCPCA9685 HCPCA9685(I2CAdd);


//Upper and lower Rod Declaration
int enA = 5;
int in1 = 8;
int in2 = 9;
// motor two
int enB = 15;
int in3 = 11;
int in4 = 12;

//4wd Bottom Declartion

int ena1 = 5;
int input1 = 2;
int input2 = 3;
int input3 = 4;
int input4 = 5;
int ena2 = 10;
int vena = 120;
int panPos=0;
int tiltPos=180;
void setup() 
{
  /* Initialise the library and set it to 'servo mode' */ 
  HCPCA9685.Init(SERVO_MODE);
 Serial.begin(9600); //Sets the baud for serial data transmission                               
  //  pinMode(13, OUTPUT); //Sets digital pin 13 as output pin
  /* Wake the device up */
  HCPCA9685.Sleep(false);
  pinMode(enA, OUTPUT);
  pinMode(enB, OUTPUT);
  pinMode(in1, OUTPUT);
  pinMode(in2, OUTPUT);
  pinMode(in3, OUTPUT);
  pinMode(in4, OUTPUT);

 pinMode(input1,OUTPUT);
pinMode(input2,OUTPUT);
pinMode(input3,OUTPUT);
pinMode(input4,OUTPUT);
pinMode(ena1, OUTPUT);
pinMode(ena2, OUTPUT);
}

void UpperMotorForward(int delaySeconds)
{
 digitalWrite(enA, LOW);
  delay(delaySeconds);
  digitalWrite(in2, LOW);
  digitalWrite(in1, HIGH);
   delay(delaySeconds);
  digitalWrite(enA, LOW);
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);
  delay(delaySeconds);
 
}
void UpperMotorBackward(int delaySeconds)
{
  digitalWrite(enA, LOW);
  delay(delaySeconds);
  digitalWrite(in2, HIGH);
  digitalWrite(in1, LOW);
   delay(delaySeconds);
  digitalWrite(enA, LOW);
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);
  delay(delaySeconds);
}

void UpperLowerMotorForward(int delaySeconds)
{

 // digitalWrite(enA, LOW);
 // digitalWrite(enB, LOW);
  delay(delaySeconds);
 // digitalWrite(enA, HIGH);
  digitalWrite(in1, HIGH);
  digitalWrite(in2, LOW);
  digitalWrite(in3, LOW);
  digitalWrite(in4, HIGH);
  delay(delaySeconds);
 // digitalWrite(enA, LOW);
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);
  digitalWrite(enB, LOW);
  digitalWrite(in3, LOW);
  digitalWrite(in4, LOW);
  delay(delaySeconds);
  
}
void LowerMotorForward(int delaySeconds)
{
  digitalWrite(enB, LOW);
  digitalWrite(in3, HIGH);
  digitalWrite(in4, LOW);
delay(delaySeconds);
   LowerMotorStop();

}
void LowerMotorBackward(int delaySeconds)
{

  digitalWrite(in3, LOW);
  digitalWrite(in4, HIGH);
  delay(delaySeconds);
  LowerMotorStop();
}
void LowerMotorStop()
{
  digitalWrite(enB, LOW);
  digitalWrite(in3, LOW);
  digitalWrite(in4, LOW);
 }
 void UpperMotorStop()
{
  digitalWrite(enA, LOW);
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);
 }


void TiltRotateFordward(int maxAngle,int delaySeconds)
{
    //maxAngle =360
    for(int i=tiltPos;   i<maxAngle;  i++) 
    { HCPCA9685.Servo(0,i); delay(delaySeconds);
    tiltPos=i;
    }
  
}
void TiltRotateBackward(int minAngle,int delaySeconds)
{
       //minAngle=180;
       for(int i=tiltPos;   i>minAngle;  i--) { 
        HCPCA9685.Servo(0,i); delay(delaySeconds); 
         tiltPos=i;}
}
void PanRotateForward(int minAngle,int delaySeconds)
{
  //min angle =0
   for(int i=panPos; i>minAngle;   i--) 
   { HCPCA9685.Servo(2, i); delay(delaySeconds); 
   panPos=i;   
   }
}

void PanRotateBackward(int maxAngle,int delaySeconds)
{
  //max angle =210
 for(int i=panPos;   i<maxAngle;  i++) { HCPCA9685.Servo(2, i);; delay(delaySeconds);
 panPos=i;
}
}

void MotorBackward(int delaySeconds)
{
 // go  forward
  analogWrite(ena1, vena);
  analogWrite(ena2, vena);
  digitalWrite(input4,LOW);
  digitalWrite(input3,HIGH);  
  digitalWrite(input2,LOW);
  digitalWrite(input1,HIGH);  
  delay(delaySeconds);   //1 second wait
  MotorStop1(delaySeconds);
}
void DMotorStop(int delaySeconds)
{
  analogWrite(ena1, vena);
 analogWrite(ena2, vena);
 digitalWrite(input1,LOW);
 digitalWrite(input2,LOW);  
 digitalWrite(input3,LOW);
 digitalWrite(input4,LOW);  
 delay(delaySeconds); 
 MotorStop1(delaySeconds);
}
void MotorLeft(int delaySeconds)
{
 digitalWrite(input1,HIGH);
 digitalWrite(input2,LOW);  
 digitalWrite(input3,LOW);
 digitalWrite(input4,HIGH);  
  delay(delaySeconds);
  MotorStop1(delaySeconds);
  
}
void MotorRight(int delaySeconds)
{
 //analogWrite(ena1, vena);
 //analogWrite(ena2, vena);
 digitalWrite(input1,LOW);
 digitalWrite(input2,HIGH);  
 digitalWrite(input3,HIGH);
 digitalWrite(input4,LOW);  
 delay(delaySeconds); 
 MotorStop1(delaySeconds);
}
void MotorForward(int delaySeconds)
{
// analogWrite(ena1, vena);
// analogWrite(ena2, vena);
  digitalWrite(input1,LOW);
  digitalWrite(input2,HIGH);  
  digitalWrite(input3,LOW);
  digitalWrite(input4,HIGH);  
    delay(delaySeconds);  
 MotorStop1(delaySeconds);
} 
void MotorStop1(int delaySeconds)
{
 //analogWrite(ena1, vena);
 //analogWrite(ena2, vena);
 digitalWrite(input1,LOW);
 digitalWrite(input2,LOW);  
 digitalWrite(input3,LOW);
 digitalWrite(input4,LOW);  
 delay(delaySeconds); 
} 
void MotorStop2(int delaySeconds)
{

 //analogWrite(ena1, vena);
 digitalWrite(input1,LOW);
 digitalWrite(input2,LOW);  
 digitalWrite(input3,LOW);
 digitalWrite(input4,LOW);  
 delay(delaySeconds);   //1 second wait
}
  void stop3(int delaySeconds)
  {

 digitalWrite(input1,LOW);
 digitalWrite(input2,LOW);  
 digitalWrite(input3,LOW);
 digitalWrite(input4,LOW);  
 delay(delaySeconds); 
  }


String recvString="0";
void loop() 
{

//Upper part 

//  TiltRotateFordward();
//  TiltRotateBackward();
//PanRotateForward();
//PanRotateBackward();
  
 // UpperMotorForward();
 // LowerMotorForward(); 
 // UpperMotorBackward(); 
 // LowerMotorBackward();


//Lower 4wd parts
//MotorForward();
//MotorBackward();
//MotorLeft(); 
//MotorRight();

//A - UpperMotorForward
//B = UpperMotorBackward
//C = LowerMotorForward
//D = LowerMotorBackward

//E = PanRotateForward
//F = PanRotateBackward
//G = TiltRotateFordward
//H = TiltRotateBackward

//I = MotorForward
//J = MotorBackward
//K = MotorLeft
//L = MotorRight

if(Serial.available() > 0)  // Send data only when you receive data:
   {
      recvString = Serial.readString();      
     // data = A,10,25, - A= Funcation Command , 0 = Angle ,25 = delay seconds 

int i1 = recvString.indexOf(',');
  int i2 = recvString.indexOf(',', i1+1);
  Serial.println(i1);
  Serial.println(i2);

  String firstValue = recvString.substring(0, i1);
  int secondValue = recvString.substring(i1 + 1, i2).toInt();
  int thirdValue = recvString.substring(i2 + 1).toInt();
  Serial.print(recvString); 
  Serial.println(firstValue);
  Serial.println(secondValue);
  Serial.println(thirdValue);
         
      Serial.print("\n");      
      Serial.print("Current Pan Pos is :"); 
            Serial.print(panPos);    
            Serial.print("\n");   
            Serial.print("Current Tilt Pos is :"); 
            Serial.print(tiltPos); 
               Serial.print("\n");  
      if(recvString == "0")  
      {
      }           
      else if(firstValue == "A")    
      {     
           UpperMotorForward(thirdValue);
          //UpperLowerMotorForward();
      }
           else if(firstValue == "B")         
           UpperMotorBackward(thirdValue);
           else if(firstValue == "C")         
           LowerMotorForward(thirdValue);
                else if(firstValue == "D")         
           LowerMotorBackward(thirdValue);
                else if(firstValue == "E")
                {
               Serial.print("Rotating Pan");             
           PanRotateForward(secondValue,thirdValue);
            Serial.print("Updated Pan Pos is :"); 
            Serial.print("\n");  
            Serial.print(panPos); 
                }
                else if(firstValue == "F") 
                {        
                    Serial.print("Rotating Pan");
           PanRotateBackward(secondValue,thirdValue);
              Serial.print("Updated Pan Pos is :"); 
              Serial.print("\n");  
            Serial.print(panPos); 
                }
                else if(firstValue == "G")    
                {
                              Serial.print("Rotating Tilt");     
           TiltRotateFordward(secondValue,thirdValue);
             Serial.print("Updated Tilt Pos is :"); 
            Serial.print(tiltPos); 
               Serial.print("\n");  
                }
                else if(firstValue == "H")  
                {  
                     Serial.print("Rotating Tilt");          
           TiltRotateBackward(secondValue,thirdValue);
           Serial.print("Updated Tilt Pos is :"); 
            Serial.print(tiltPos); 
               Serial.print("\n");  
                }
                else if(firstValue == "I")         
           MotorForward(thirdValue);
                else if(firstValue == "J")         
           MotorBackward(thirdValue);
                else if(firstValue == "K")         
           MotorLeft(thirdValue);
                else if(firstValue == "L")         
           MotorRight(thirdValue);
           
   }

}
