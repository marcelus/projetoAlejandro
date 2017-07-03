String recv, temp;
String param = ":;420000;1;1;0000000;0;1;000;0000000;000;                                        ;0000000;000;                                        ;0000000;000;                                        ;0000000;000;                                        ;!01898915581@";

void setup() {
  Serial.begin(9600);
}

void loop() {
  
  //Faz Leitura se disponível
  if (Serial.available())
    recv = Serial.readString();

  //Se receber interrogação, envia a config atual
  if (recv == "?"){
    Serial.print(param);
    recv = "";
  }

  //Se iniciar com #, realiza o checksum e grava a informação lida sem o #
  if (recv.charAt(0) == '#'){
    temp = param;
    param = recv.substring(1);
    recv = "";

    //Separa o checksum recebido pelo Serial e calcula o checksum das configurações localmente
    String chkSum1 = "", chkSum2 = "";
    chkSum1 = adlerChecksum(param.substring(0, param.lastIndexOf('!')));
    chkSum2 = param.substring(param.lastIndexOf('!')+1,param.lastIndexOf('@'));

//    Serial.print("\nchksum1: ");
//    Serial.print(chkSum1);
//    Serial.print("\nchksum2: ");
//    Serial.print(chkSum2);

    //Verifica se os checksums são diferentes
    for (int i = 0; i < chkSum1.length(); i++){
      if (chkSum1.charAt(i) != chkSum2.charAt(i))
        param = temp;
    }
      
  }
  //Delay para não causar conflito no I/O Serial
  delay(2);
}

//Calcula checksum
String simpleChecksum (String param){
  unsigned long chkSum = 0;
  for (int i = 0; i < param.length(); i++)
    chkSum += ((int) param.charAt(i)) * i;
  return normalizeChkSum(chkSum);
}

String adlerChecksum (String param){
  unsigned long a = 1, b = 0;
  for (int i = 0; i < param.length() ; i++ ){
          a = (a + (int)param.charAt(i)) % 65521;
          b = (b + a) % 65521;
  }
  return normalizeChkSum( b * 65536 + a );
}

//Normaliza o checksum como string de 11 dígitos
String normalizeChkSum (unsigned long chkSum){
  char r[11];
  sprintf(r, "%011lu", chkSum); 
  return r;
}
