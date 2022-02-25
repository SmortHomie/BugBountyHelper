import requests
from pprint import pprint
from bs4 import BeautifulSoup as bs
from urllib.parse import urljoin
import os,random

def get_all_forms(url):
    soup = bs(requests.get(url).content, "html.parser")
    return soup.find_all("form")

def random_line(afile):
    line = next(afile)
    for num, aline in enumerate(afile, 2):
        if random.randrange(num):
            continue
        line = aline
    return line

def get_form_details(form):
    details = {}
    # get the form action (target url)
    action = form.attrs.get("action").lower()
    # get the form method (POST, GET, etc.)
    method = form.attrs.get("method", "get").lower()
    # get all the input details such as type and name
    inputs = []
    for input_tag in form.find_all("input"):
        input_type = input_tag.attrs.get("type", "text")
        input_name = input_tag.attrs.get("name")
        inputs.append({"type": input_type, "name": input_name})
    # put everything to the resulting dictionary
    details["action"] = action
    details["method"] = method
    details["inputs"] = inputs
    return details

def submit_form(form_details, url, value):
    # construct the full URL (if the url provided in action is relative)
    target_url = urljoin(url, form_details["action"])
    # get the inputs
    inputs = form_details["inputs"]
    data = {}
    for input in inputs:
        # replace all text and search values with `value`
        if input["type"] == "text" or input["type"] == "search":
            input["value"] = value
        input_name = input.get("name")
        input_value = input.get("value")
        if input_name and input_value:
            # if input name and value are not None, 
            # then add them to the data of form submission
            data[input_name] = input_value

    if form_details["method"] == "post":
        return requests.post(target_url, data=data)
    else:
        # GET request
        return requests.get(target_url, params=data)

def scan_xss(url,xss_payload):
    # get all the forms from the URL
    forms = get_all_forms(url)
    print(f"[+] Detected {len(forms)} forms on {url}.")
    js_script = xss_payload
    # returning value
    is_vulnerable = False
    # iterate over all forms
    for form in forms:
        form_details = get_form_details(form)
        content = submit_form(form_details, url, js_script).content.decode()
        if js_script in content:
            print(f"[+] XSS Detected on {url}")
            print(f"[*] Form details:")
            pprint(form_details)
            is_vulnerable = True
            # won't break because we want to print available vulnerable forms
    return is_vulnerable

def randomPayload(Ftype):
    f_name=fileinput(Ftype)
    with open(f_name,'r') as file:
        dork=random_line(file)

    return str(dork)

def fileinput(files):
	if files.lower()=="e":
		f_name=os.path.join(os.path.dirname(__file__),"XSS Payload/L.txt")
	elif files.lower()=="m":
		f_name=os.path.join(os.path.dirname(__file__),"XSS Payload/M.txt")    
	elif files.lower()=="h":
		f_name=os.path.join(os.path.dirname(__file__),"XSS Payload/H.txt")
	else:
		return "File Types Invalid"
	return f_name

url = "https://xss-game.appspot.com/level1/frame"
xss=randomPayload("e")
print(xss)
print(scan_xss(url,xss))
