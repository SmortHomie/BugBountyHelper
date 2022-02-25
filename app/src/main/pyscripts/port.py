#Lec 4
import socket
from IPy import IP

def check_ip(ip):
    try:
        IP(ip)
        return ip
    except ValueError:
        return socket.gethostbyname(ip)

def get_banner (s):
    return s.recv(1024)

def scan_port(ipaddr,port):
    try:
        sock=socket.socket()
        sock.settimeout(2)
        sock.connect((ipaddr,port))
        try:
            banner=get_banner(sock)
            res="Banner : "+str(banner)
        except:
            res="Banner : None"
    except:
        res="close"
        pass
    return res

def scan(ip,port):
    # ip="google.com"
    conv_ip=check_ip(ip)
    r=[]
    r.append(str(port))
    a=scan_port(conv_ip,port)
    if(a=="close"):
        r.append("CLOSED")
        r.append("Port : "+str(port)+"\nIP Addr : \n"+conv_ip)
        r.append("Banner : None")
    else :
        r.append("OPEN")
        r.append("Port : "+str(port)+"\nIP Addr : \n"+conv_ip)
        r.append(a)
    return r


