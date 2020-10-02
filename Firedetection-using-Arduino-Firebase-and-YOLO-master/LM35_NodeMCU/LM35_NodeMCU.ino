#include <FirebaseArduino.h>
#include <ESP8266WiFi.h>

// Set these to run example.
#define FIREBASE_HOST "iot-control-3.firebaseio.com"
#define FIREBASE_AUTH "m4EZJy79J8HfCntIe9oWHUv7Tt2yTWUArC73IIZI"
#define WIFI_SSID "Kick_deon"
#define WIFI_PASSWORD "wifipassword@123"
float fire;
int val;
void setup() {
Serial.begin(9600);
pinMode(D7, OUTPUT);
pinMode(D6, OUTPUT);
pinMode(D8, OUTPUT);
pinMode(A0, INPUT);
// connect to wifi.
WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
Serial.print("connecting");
while (WiFi.status() != WL_CONNECTED) {
Serial.print(".");
delay(100);
}
Serial.println();
Serial.print("connected: ");
Serial.println(WiFi.localIP());
Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
Firebase.set("LED_STATUS", 0);
}
int n = 0;


void loop() {

int analogValue = analogRead(A0);
float millivolts = (analogValue/1024.0) * 3300; //3300 is the voltage provided by NodeMCU
float celsius = millivolts/10;

Serial.print("in DegreeC=   ");
Serial.println(celsius);
Firebase.setFloat("fire_sensor_data",celsius);

if(celsius>37){
digitalWrite(D6,HIGH);
Firebase.setInt("fire_sensor_status",1);

}

///////DigitalRead for vibration Sensor/////////

val = digitalRead(D0);
  Serial.print("vib digitalRead=   ");
  Serial.println(val);
  if(0>=val){
  digitalWrite(D8,HIGH);
  Firebase.setInt("vibration_sensor_status",1);

  }
  
//////////////////////////////////////////////// 
n = Firebase.getInt("LED_STATUS");  
// handle error
if (n==1) {
Serial.println("LED ON");
digitalWrite(D7,HIGH);  
return;

}
else {
Serial.println("LED OFF");
digitalWrite(D7,LOW);  
digitalWrite(D6,LOW);  
digitalWrite(D8,LOW);  
Firebase.setInt("fire_sensor_status",0);
Firebase.setInt("vibration_sensor_status",0);
return;


}
}
