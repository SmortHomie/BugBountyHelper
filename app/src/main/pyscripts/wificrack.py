from wireless import Wireless
wire= Wireless(['wlan'])
#for i in ['L','M','H']:
  #  print("Finding Pass in WifiPass/"+i+'.txt')
    #with open('WifiPass/'+i+'.txt','r') as f:
      #for line in f.readlines():
        #if wire.connect(ssid='wifiname',password=line.strip()) ==True:
          #print("connect : "+line.strip()+"succes")
        #else:
          #print("connect : "+line.strip()+"fail")
print(wire.connect(ssid="Bhushan'",password="1234568"))
