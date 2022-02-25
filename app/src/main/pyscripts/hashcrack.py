import hashlib,os

#HashVal=str(input("Hash Value :"))
def file(files):
	if files.lower()=="e":
		f_name=os.path.join(os.path.dirname(__file__),"Pass/L.txt")
	elif files.lower()=="m":
		f_name=os.path.join(os.path.dirname(__file__),"Pass/M.txt")    
	elif files.lower()=="h":
		f_name=os.path.join(os.path.dirname(__file__),"Pass/H.txt")
	else:
		return "File Types Invalid"
	return f_name

def deHash(ftype,HashVal):
	f_name=file(ftype)
	Res=[]
	with open(f_name,'r') as F:
		for line in F:
			hashobject=hashlib.md5(line.strip().encode())
			h_word=hashobject.hexdigest()
			if h_word==HashVal:
				Res.append("MD5")
				Res.append(line.strip())
			hashobject=hashlib.sha1(line.strip().encode())
			h_word=hashobject.hexdigest()
			elif h_word==HashVal:
				Res.append("SHA1")
				Res.append(line.strip())
			hashobject=hashlib.sha224(line.strip().encode())
			h_word=hashobject.hexdigest()
			elif h_word==HashVal:
				Res.append("SHA224")
				Res.append(line.strip())
			hashobject=hashlib.sha256(line.strip().encode())
			h_word=hashobject.hexdigest()
			elif h_word==HashVal:
				Res.append("SHA256")
				Res.append(line.strip())
			hashobject=hashlib.sha384(line.strip().encode())
			h_word=hashobject.hexdigest()
			elif h_word==HashVal:
				Res.append("SHA384")
				Res.append(line.strip())

