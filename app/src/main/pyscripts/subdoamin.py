import requests
import os

# the domain to scan for subdomains
domain = input("Enter Domains :")
# read all subdomains
f_name=os.path.join(os.path.dirname(__file__),"Subs/subs.txt")
with open(f_name,'r') as file:
    
#file = open("subdomains.txt")
# read all content
    content = file.read()
# split by new lines
    subdomains = content.splitlines()
    discovered_subdomains = []
    for subdomain in subdomains:
    # construct the url
        url = f"http://{subdomain}.{domain}"
        try:
            # if this raises an ERROR, that means the subdomain does not exist
            requests.get(url)
        except requests.ConnectionError:
        # if the subdomain does not exist, just pass, print nothing
            pass
        else:
            print("[+] Discovered subdomain:", url)
        # append the discovered subdomain to our list
            discovered_subdomains.append(url)
        
#with open("discovered_subdomains.txt", "w") as f:
#    for subdomain in discovered_subdomains:
#        print(subdomain, file=f)
