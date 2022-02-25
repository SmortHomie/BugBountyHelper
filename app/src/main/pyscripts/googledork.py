#Google Dorker
import requests
import os
from googlesearch import search
import random

def random_line(afile):
    line = next(afile)
    for num, aline in enumerate(afile, 2):
        if random.randrange(num):
            continue
        line = aline
    return line

def strongDorking(Query,Count=20):
    number=0
    DResults=[]
    counter=0
    for results in search(Query, num=int(Count),start=0,stop=None,lang="en",tld="com", pause=2.5):
        number+=1
        counter+=1
        DResults.append(results)
        if number >= int(Count):
            break
    return DResults

def minimalDorking(Query):
    google_url = f"https://www.google.com/search?q={Query}&hl=en"
    api = f"https://api.hackertarget.com/pagelinks?q={google_url}"
    response = requests.get(api).text
    return response.split("\n")

def randomDork(F):
    f_name=os.path.join(os.path.dirname(__file__),F)
    with open(f_name,'r') as file:
        dork=random_line(file)

    return str(dork)

